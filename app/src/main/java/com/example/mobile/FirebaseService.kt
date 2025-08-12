package com.example.mobile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.example.mobile.R
import com.example.mobile.data.Transazione
import com.example.mobile.data.Articolo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.firebase.firestore.AggregateSource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseService(private val activity: Activity) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    private val _saldo = MutableStateFlow(0.0) // Privata
    val saldo: StateFlow<Double> = _saldo.asStateFlow() // Pubblica

    private val _nTransazioni = MutableStateFlow(0.0) // Privata
    val nTransazioni: StateFlow<Double> = _nTransazioni.asStateFlow() // Pubblica

    private val _bitcoin = MutableStateFlow("") // Privata
    val bitcoin: StateFlow<String> = _bitcoin.asStateFlow() // Pubblica

    private val _lightning = MutableStateFlow("") // Privata
    val lightning: StateFlow<String> = _lightning.asStateFlow() // Pubblica

    private val _bitcoinBitmap = MutableStateFlow<Bitmap?>(null)
    val bitcoinBitmap: StateFlow<Bitmap?> = _bitcoinBitmap

    private val _lightningBitmap = MutableStateFlow<Bitmap?>(null)
    val lightningBitmap: StateFlow<Bitmap?> = _lightningBitmap



    /** Carica il saldo iniziale dal database all'avvio **/
    fun caricaSaldoIniziale(callback: ((Boolean) -> Unit)? = null) {
        val uid = auth.currentUser?.uid
        if (uid.isNullOrEmpty()) {
            Log.w("Firebase", "Nessun utente loggato")
            callback?.invoke(false)
            return
        }

        db.collection("utenti").document(uid)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    _saldo.value = doc.getDouble("saldo") ?: 0.0
                    Log.d("Firebase", "Saldo iniziale caricato: ${_saldo.value}")
                } else {
                    Log.w("Firebase", "Documento utente non trovato, creazione nuovo utente con saldo 0")
                    db.collection("utenti").document(uid)
                        .set(mapOf("saldo" to 0.0))
                    _saldo.value = 0.0
                }
                callback?.invoke(true)
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Errore caricando saldo: ${e.message}")
                callback?.invoke(false)
            }
    }

    /** Aggiorna il saldo locale leggendo da Firestore **/
    fun aggiornaSaldo(callback: ((Boolean) -> Unit)? = null) {
        val uid = auth.currentUser?.uid
        if (uid.isNullOrEmpty()) {
            Log.w("Firebase", "Nessun utente loggato")
            callback?.invoke(false)
            return
        }

        db.collection("utenti").document(uid)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val nuovoSaldo = doc.getDouble("saldo") ?: 0.0
                    if (_saldo.value != nuovoSaldo) {
                        _saldo.value = nuovoSaldo
                        Log.d("Firebase", "Saldo aggiornato: ${_saldo.value}")
                    }
                    callback?.invoke(true)
                } else {
                    Log.w("Firebase", "Documento utente non trovato durante aggiornamento")
                    callback?.invoke(false)
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Errore aggiornando saldo: ${e.message}")
                callback?.invoke(false)
            }
    }

    /** 🔹 Configura Google SignIn */
    fun getGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        Log.d("Firebase", "GoogleSignInClient configurato")
        return GoogleSignIn.getClient(activity, gso)
    }

    /** 🔹 Gestisce il login con Google */
    fun handleGoogleSignInResult(
        data: Intent?,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                Log.d("Firebase", "Google SignIn riuscito, ID token ricevuto")
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            Log.d("Firebase", "Autenticazione Firebase con Google riuscita")
                            val uid = auth.currentUser?.uid
                            if (uid != null) {
                                Log.d("Firebase", "UID utente: $uid")
                                checkAndCreateUser(uid) { success ->
                                    if (success) {
                                        Log.d("Firebase", "Utente verificato o creato correttamente")
                                        onSuccess()
                                    } else {
                                        Log.d("Firebase", "Errore durante la creazione o verifica utente")
                                        onFailure(Exception("Errore creazione utente"))
                                    }
                                }
                            } else {
                                Log.d("Firebase", "UID utente non trovato dopo login")
                                onFailure(Exception("UID non trovato"))
                            }
                        } else {
                            Log.d("Firebase", "Autenticazione Firebase fallita: ${authTask.exception}")
                            onFailure(authTask.exception ?: Exception("Errore sconosciuto"))
                        }
                    }
            }
        } catch (e: ApiException) {
            Log.d("Firebase", "Errore ApiException nel login Google: ${e.message}")
            onFailure(e)
        }
    }

    /** 🔹 Controlla e crea la collezione `utenti` e il documento dell'utente */
    private fun checkAndCreateUser(uid: String, callback: (Boolean) -> Unit) {
        val utentiRef = db.collection("utenti")

        utentiRef.document(uid).get().addOnSuccessListener { doc ->
            if (!doc.exists()) {
                Log.d("Firebase", "Utente non trovato nel db, procedo a crearlo")
                createUserDocument(uid) { created ->
                    if (created) {
                        Log.d("Firebase", "Documento utente creato con successo")
                    } else {
                        Log.d("Firebase", "Fallita la creazione del documento utente")
                    }
                    callback(created)
                }
            } else {
                Log.d("Firebase", "Utente già esistente nel db")
                callback(true) // Utente già esistente
            }
        }.addOnFailureListener {
            Log.d("Firebase", "Errore nel recuperare il documento utente: ${it.message}")
            callback(false)
        }
    }

    /** 🔹 Crea il documento utente con i campi iniziali */
    private fun createUserDocument(uid: String, callback: (Boolean) -> Unit) {
        val userData = mapOf(
            "bitcoin" to "wallet bitcoin",
            "lightning" to "wallet lightning",
            "saldo" to 0.0
        )

        db.collection("utenti").document(uid)
            .set(userData, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Firebase", "Documento utente iniziale creato")
                // Non creo la collezione transazioni fittizia
                callback(true)
            }
            .addOnFailureListener {
                Log.d("Firebase", "Errore nella creazione del documento utente: ${it.message}")
                callback(false)
            }
    }

    /** 🔹 Logout */
    fun signOut(onComplete: () -> Unit) {
        auth.signOut()
        Log.d("Firebase", "Utente disconnesso (logout)")
        onComplete()
    }

    /**
     * Aggiunge una transazione in modo atomico e aggiorna il saldo usando runTransaction.
     * Alla fine aggiorna _saldo.value localmente (solo se la transaction ha successo).
     */
    fun addTransaction(
        transazione: Transazione,
        callback: ((Boolean) -> Unit)? = null
    ) {
        val uid = auth.currentUser?.uid ?: run {
            Log.w("Firebase", "Utente non autenticato")
            callback?.invoke(false)
            return
        }

        val utenteRef = db.collection("utenti").document(uid)
        val transazioneRef = utenteRef.collection("transazioni").document(transazione.id)

        // Mappo articoli in mappe (compatibile con Firestore)
        val articoliMap = transazione.articoli.map { articolo ->
            mapOf(
                "id" to articolo.id,
                "nome" to articolo.nome,
                "prezzo" to articolo.prezzo,
                "valuta" to articolo.valuta
            )
        }

        val datiTransazione = mapOf(
            "quantita" to transazione.quantita,
            "dataTimestamp" to transazione.dataTimestamp,
            "isEntrata" to transazione.isEntrata,
            "from" to transazione.from,
            "to" to transazione.to,
            "articoli" to articoliMap
        )

        // runTransaction restituisce il valore che il lambda ritorna (qui Double = nuovo saldo)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(utenteRef)
            val saldoAttuale = snapshot.getDouble("saldo") ?: 0.0

            val nuovoSaldo = if (transazione.isEntrata) {
                saldoAttuale + transazione.quantita
            } else {
                saldoAttuale - transazione.quantita
            }

            // salvo la transazione
            transaction.set(transazioneRef, datiTransazione)

            // se il documento utente esiste faccio update, altrimenti set (per essere robusto)
            if (snapshot.exists()) {
                transaction.update(utenteRef, "saldo", nuovoSaldo)
            } else {
                transaction.set(utenteRef, mapOf("saldo" to nuovoSaldo))
            }

            nuovoSaldo // valore di ritorno della transaction
        }.addOnSuccessListener { result ->
            // result dovrebbe essere Double (nuovo saldo)
            val nuovoSaldo = (result as? Double) ?: run {
                Log.e("Firebase", "runTransaction successo ma risultato non Double")
                callback?.invoke(false)
                return@addOnSuccessListener
            }
            _saldo.value = nuovoSaldo
            Log.d("Firebase", "Transazione salvata e saldo aggiornato atomico: $nuovoSaldo")
            callback?.invoke(true)
        }.addOnFailureListener { e ->
            Log.e("Firebase", "Errore runTransaction: ${e.message}", e)
            callback?.invoke(false)
        }
    }

    fun getNumeroTotaleTransazioni() {
        val uid = auth.currentUser?.uid
        if (uid.isNullOrEmpty()) {
            Log.w("Firebase", "Utente non autenticato")
            _nTransazioni.value = 0.0
            return
        }

        val transazioniRef = db.collection("utenti").document(uid).collection("transazioni")

        transazioniRef.get()
            .addOnSuccessListener { querySnapshot ->
                val count = querySnapshot.size().toDouble()
                Log.d("Firebase", "Numero totale transazioni: $count")
                _nTransazioni.value = count
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Errore contando transazioni: ${e.message}")
                _nTransazioni.value = 0.0
            }
    }

    fun getAllTransactions(callback: (List<Transazione>?) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid.isNullOrEmpty()) {
            Log.w("Firebase", "Utente non autenticato")
            callback(null)
            return
        }

        val transazioniRef = db.collection("utenti").document(uid).collection("transazioni")

        transazioniRef.get()
            .addOnSuccessListener { querySnapshot ->
                val listaTransazioni = querySnapshot.documents.mapNotNull { doc ->
                    try {
                        val id = doc.id
                        val quantita = doc.getDouble("quantita") ?: return@mapNotNull null
                        val dataTimestamp = doc.getLong("dataTimestamp") ?: return@mapNotNull null
                        val isEntrata = doc.getBoolean("isEntrata") ?: return@mapNotNull null
                        val from = doc.getString("from") ?: ""
                        val to = doc.getString("to") ?: ""
                        val articoliList = doc.get("articoli") as? List<Map<String, Any>> ?: emptyList()

                        val articoli = articoliList.mapNotNull { articoloMap ->
                            val artIdStr = articoloMap["id"] as? String ?: return@mapNotNull null
                            val artId = artIdStr.toIntOrNull() ?: return@mapNotNull null
                            val nome = articoloMap["nome"] as? String ?: return@mapNotNull null
                            val prezzo = (articoloMap["prezzo"] as? Number)?.toDouble() ?: return@mapNotNull null
                            val valuta = articoloMap["valuta"] as? String ?: return@mapNotNull null
                            Articolo(artId, nome, prezzo, valuta)
                        }

                        Transazione(
                            id = id,
                            quantita = quantita,
                            dataTimestamp = dataTimestamp,
                            isEntrata = isEntrata,
                            from = from,
                            to = to,
                            articoli = articoli
                        )
                    } catch (e: Exception) {
                        Log.e("Firebase", "Errore parsing transazione: ${e.message}")
                        null
                    }
                }
                Log.d("Firebase", "Transazioni caricate: ${listaTransazioni.size}")
                callback(listaTransazioni)
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Errore caricando transazioni: ${e.message}")
                callback(null)
            }
    }

    fun caricaIndirizziDaServer() {
        val uid = auth.currentUser?.uid
        if (uid.isNullOrEmpty()) {
            Log.w("Firebase", "Utente non autenticato")
            return
        }

        val userDocRef = db.collection("utenti").document(uid)

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val indirizzoBitcoin = document.getString("bitcoin") ?: ""
                    val indirizzoLightning = document.getString("lightning") ?: ""

                    _bitcoin.value = indirizzoBitcoin
                    _lightning.value = indirizzoLightning

                    // Genera le bitmap QR code con la funzione esterna
                    _bitcoinBitmap.value = if (indirizzoBitcoin.isNotEmpty()) {
                        generaQrCode(indirizzoBitcoin)
                    } else null

                    _lightningBitmap.value = if (indirizzoLightning.isNotEmpty()) {
                        generaQrCode(indirizzoLightning)
                    } else null

                    Log.d("Firebase", "Indirizzi e bitmap caricati con successo")
                    Log.d("Firebase", "Bitcoin: $indirizzoBitcoin")
                    Log.d("Firebase", "Lightning: $indirizzoLightning")
                } else {
                    Log.w("Firebase", "Documento utente non trovato")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Errore caricando gli indirizzi: ${e.message}")
            }
    }






}
