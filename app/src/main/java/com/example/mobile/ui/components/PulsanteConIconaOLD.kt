package com.example.mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mobile.R
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.drawscope.clipPath

@Composable
fun PulsanteConIconOLD(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .clip(shape) // <--- clip applicato qui
            .background(MaterialTheme.colorScheme.primary)
            .innerStrokeShadowOLD(shape) // <--- ombra interna
            .clickable { onClick() }, // click
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.tastiera),
            contentDescription = "Tastiera",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f)
        )
    }
}


fun Modifier.innerStrokeShadowOLD(
    shape: RoundedCornerShape,
    darkColor: Color = Color.Black.copy(alpha = 0.4f),
    lightColor: Color = Color.White.copy(alpha = 0.6f),
    strokeWidth: Float = 28f
): Modifier = this.then(
    Modifier.drawWithContent {
        // disegna prima il contenuto normale
        drawContent()

        // crea il percorso della forma
        val outline = shape.createOutline(size, layoutDirection, this)
        val path = Path().apply { addOutline(outline) }

        // Clippeamo tutto ciò che disegneremo dentro il path
        clipPath(path) {
            // Stroke nero interno (alto/sinistra)
            drawPath(
                path = path,
                brush = Brush.linearGradient(
                    colors = listOf(darkColor, Color.Transparent),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, size.height)
                ),
                style = Stroke(width = strokeWidth)
            )

            // Stroke bianco interno (basso/destra)
            drawPath(
                path = path,
                brush = Brush.linearGradient(
                    colors = listOf(Color.Transparent, lightColor),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, size.height)
                ),
                style = Stroke(width = strokeWidth)
            )
        }
    }
)
