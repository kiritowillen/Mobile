package com.example.mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mobile.R
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun PulsanteConIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    highlighted: Boolean = false // nuovo parametro
) {
    val shape = RoundedCornerShape(16.dp)

    // Modificatore condizionale
    val backgroundModifier = if (highlighted) {
        modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.primary)
            .innerShadow(
                shape = shape,
                shadowColorDark = Color.Black.copy(alpha = 0.5f),
                shadowColorLight = Color.White.copy(alpha = 0.7f),
                strokeWidth = 12f
            )
    } else {
        modifier
    }

    Box(
        modifier = backgroundModifier.clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f)
        )
    }
}
fun Modifier.innerShadow(
    shape: RoundedCornerShape,
    shadowColorDark: Color = Color.Black.copy(alpha = 0.5f),
    shadowColorLight: Color = Color.White.copy(alpha = 0.7f),
    strokeWidth: Float = 12f
): Modifier = this.then(
    Modifier.drawWithContent {
        drawContent()

        val outline = shape.createOutline(size, layoutDirection, this)
        val path = Path().apply { addOutline(outline) }

        clipPath(path) {
            // Ombra nera interna sopra e sinistra
            drawPath(
                path = path,
                brush = Brush.linearGradient(
                    colors = listOf(shadowColorDark, Color.Transparent),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, size.height)
                ),
                style = Stroke(width = strokeWidth)
            )

            // Ombra bianca interna sotto e destra
            drawPath(
                path = path,
                brush = Brush.linearGradient(
                    colors = listOf(Color.Transparent, shadowColorLight),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, size.height)
                ),
                style = Stroke(width = strokeWidth)
            )
        }
    }
)
