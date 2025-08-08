package com.example.mobile.data

sealed class Screen(val route: String) {
    object Tastiera : Screen("tastiera")
    object Carrello : Screen("carrello")
    object Pagamento : Screen("pagamento")
    object Impostazioni : Screen("impostazioni")
    object CambioPin : Screen("cambiopin")
    object Transazioni : Screen("transazioni")
    object Trasferimento : Screen("trasferimento")

}