package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

object AvatarPalette {
    val skinTones = listOf(
        Color(0xFFFDE0C8),
        Color(0xFFF3C39D),
        Color(0xFFD99B72),
        Color(0xFF9C6644)
    )
    val hairColors = listOf(
        Color(0xFF2D1E1B), // Темный каштан
        Color(0xFF6B3E26), // Ореховый
        Color(0xFFD48836), // Рыжий
        Color(0xFFE9C46A), // Пшеничный
        Color(0xFF5A2D82)  // Волшебный сливовый
    )
    fun coatColor(coatId: String): Pair<Color, Color> = when (coatId) {
        "emerald" -> Color(0xFF1F6E50) to Color(0xFF134633)
        "royal" -> Color(0xFF264D8E) to Color(0xFF17315E)
        "gold" -> Color(0xFFC4861C) to Color(0xFF8C5A08)
        else -> Color(0xFF5A2D82) to Color(0xFF3A1859)
    }
}

@Composable
fun DetectiveAvatarCanvas(
    gender: String,
    skinToneIndex: Int,
    hairStyleIndex: Int,
    hairColorIndex: Int,
    hatId: String,
    coatId: String,
    accessoryId: String,
    modifier: Modifier = Modifier
) {
    val skin = AvatarPalette.skinTones[skinToneIndex.coerceIn(0, AvatarPalette.skinTones.lastIndex)]
    val hair = AvatarPalette.hairColors[hairColorIndex.coerceIn(0, AvatarPalette.hairColors.lastIndex)]
    val (coatMain, coatShadow) = AvatarPalette.coatColor(coatId)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f

        // Background medallion glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(coatMain.copy(alpha = 0.25f), Color(0xFF2B1B4D).copy(alpha = 0.85f)),
                center = Offset(cx, h * 0.45f),
                radius = w * 0.52f
            ),
            radius = w * 0.49f,
            center = Offset(cx, h * 0.5f)
        )
        drawCircle(
            color = Color(0xFFF6C85F),
            radius = w * 0.485f,
            center = Offset(cx, h * 0.5f),
            style = Stroke(width = w * 0.025f)
        )

        // Long hair back layer for girl or curly style
        if (gender == "girl" || hairStyleIndex == 2) {
            drawOval(
                color = hair,
                topLeft = Offset(cx - w * 0.24f, h * 0.23f),
                size = Size(w * 0.48f, h * 0.44f)
            )
        }

        // Detective coat & shoulders
        val coatPath = Path().apply {
            moveTo(cx - w * 0.34f, h * 0.92f)
            quadraticTo(cx - w * 0.31f, h * 0.62f, cx - w * 0.14f, h * 0.59f)
            lineTo(cx + w * 0.14f, h * 0.59f)
            quadraticTo(cx + w * 0.31f, h * 0.62f, cx + w * 0.34f, h * 0.92f)
            close()
        }
        drawPath(coatPath, color = coatMain)

        // Coat lapels
        val leftLapel = Path().apply {
            moveTo(cx - w * 0.13f, h * 0.59f)
            lineTo(cx, h * 0.78f)
            lineTo(cx - w * 0.18f, h * 0.72f)
            close()
        }
        val rightLapel = Path().apply {
            moveTo(cx + w * 0.13f, h * 0.59f)
            lineTo(cx, h * 0.78f)
            lineTo(cx + w * 0.18f, h * 0.72f)
            close()
        }
        drawPath(leftLapel, color = coatShadow)
        drawPath(rightLapel, color = coatShadow)

        // Scarf / Collar tie
        drawCircle(color = Color(0xFFF6C85F), radius = w * 0.03f, center = Offset(cx, h * 0.66f))

        // Neck & Head
        drawRoundRect(
            color = skin,
            topLeft = Offset(cx - w * 0.07f, h * 0.50f),
            size = Size(w * 0.14f, h * 0.13f),
            cornerRadius = CornerRadius(w * 0.04f, w * 0.04f)
        )
        drawOval(
            color = skin,
            topLeft = Offset(cx - w * 0.18f, h * 0.24f),
            size = Size(w * 0.36f, h * 0.34f)
        )

        // Rosy cheeks
        drawCircle(
            color = Color(0xFFE57373).copy(alpha = 0.28f),
            radius = w * 0.038f,
            center = Offset(cx - w * 0.10f, h * 0.45f)
        )
        drawCircle(
            color = Color(0xFFE57373).copy(alpha = 0.28f),
            radius = w * 0.038f,
            center = Offset(cx + w * 0.10f, h * 0.45f)
        )

        // Eyes
        drawCircle(color = Color(0xFF261C2C), radius = w * 0.023f, center = Offset(cx - w * 0.07f, h * 0.41f))
        drawCircle(color = Color(0xFF261C2C), radius = w * 0.023f, center = Offset(cx + w * 0.07f, h * 0.41f))
        drawCircle(color = Color.White, radius = w * 0.008f, center = Offset(cx - w * 0.076f, h * 0.402f))
        drawCircle(color = Color.White, radius = w * 0.008f, center = Offset(cx + w * 0.064f, h * 0.402f))

        // Smile
        drawArc(
            color = Color(0xFF6D3D3D),
            startAngle = 20f,
            sweepAngle = 140f,
            useCenter = false,
            topLeft = Offset(cx - w * 0.06f, h * 0.45f),
            size = Size(w * 0.12f, h * 0.07f),
            style = Stroke(width = w * 0.014f, cap = StrokeCap.Round)
        )

        // Front Hair Styles
        when (hairStyleIndex % 4) {
            0 -> { // Classic side sweep
                drawArc(
                    color = hair,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(cx - w * 0.20f, h * 0.21f),
                    size = Size(w * 0.40f, h * 0.22f)
                )
            }
            1 -> { // Parted bangs
                drawArc(
                    color = hair,
                    startAngle = 190f,
                    sweepAngle = 80f,
                    useCenter = true,
                    topLeft = Offset(cx - w * 0.20f, h * 0.21f),
                    size = Size(w * 0.26f, h * 0.22f)
                )
                drawArc(
                    color = hair,
                    startAngle = 270f,
                    sweepAngle = 80f,
                    useCenter = true,
                    topLeft = Offset(cx - w * 0.06f, h * 0.21f),
                    size = Size(w * 0.26f, h * 0.22f)
                )
            }
            2 -> { // Fluffy curls
                for (dx in listOf(-0.14f, -0.05f, 0.05f, 0.14f)) {
                    drawCircle(color = hair, radius = w * 0.075f, center = Offset(cx + w * dx, h * 0.27f))
                }
            }
            else -> { // Sleek fringe
                drawRoundRect(
                    color = hair,
                    topLeft = Offset(cx - w * 0.19f, h * 0.22f),
                    size = Size(w * 0.38f, h * 0.12f),
                    cornerRadius = CornerRadius(w * 0.08f, w * 0.08f)
                )
            }
        }

        // Goggles accessory (over forehead / eyes)
        if (accessoryId == "goggles") {
            drawCircle(
                color = Color(0xFFD99B26),
                radius = w * 0.048f,
                center = Offset(cx - w * 0.07f, h * 0.41f),
                style = Stroke(width = w * 0.015f)
            )
            drawCircle(
                color = Color(0xFFD99B26),
                radius = w * 0.048f,
                center = Offset(cx + w * 0.07f, h * 0.41f),
                style = Stroke(width = w * 0.015f)
            )
            drawLine(
                color = Color(0xFFD99B26),
                start = Offset(cx - w * 0.02f, h * 0.41f),
                end = Offset(cx + w * 0.02f, h * 0.41f),
                strokeWidth = w * 0.015f
            )
        }

        // Hats
        when (hatId) {
            "cap" -> {
                drawArc(
                    color = coatMain,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(cx - w * 0.20f, h * 0.15f),
                    size = Size(w * 0.40f, h * 0.22f)
                )
                drawRoundRect(
                    color = coatShadow,
                    topLeft = Offset(cx - w * 0.23f, h * 0.25f),
                    size = Size(w * 0.46f, h * 0.04f),
                    cornerRadius = CornerRadius(w * 0.02f, w * 0.02f)
                )
            }
            "trilby" -> {
                drawRoundRect(
                    color = Color(0xFF3E2C4D),
                    topLeft = Offset(cx - w * 0.15f, h * 0.12f),
                    size = Size(w * 0.30f, h * 0.14f),
                    cornerRadius = CornerRadius(w * 0.04f, w * 0.04f)
                )
                drawRect(
                    color = Color(0xFFF6C85F),
                    topLeft = Offset(cx - w * 0.15f, h * 0.22f),
                    size = Size(w * 0.30f, h * 0.025f)
                )
                drawRoundRect(
                    color = Color(0xFF2D1E3B),
                    topLeft = Offset(cx - w * 0.25f, h * 0.24f),
                    size = Size(w * 0.50f, h * 0.035f),
                    cornerRadius = CornerRadius(w * 0.02f, w * 0.02f)
                )
            }
            "beret" -> {
                drawOval(
                    color = Color(0xFFB72B47),
                    topLeft = Offset(cx - w * 0.22f, h * 0.14f),
                    size = Size(w * 0.44f, h * 0.16f)
                )
                // Feather
                drawLine(
                    color = Color(0xFFF6C85F),
                    start = Offset(cx + w * 0.12f, h * 0.19f),
                    end = Offset(cx + w * 0.24f, h * 0.09f),
                    strokeWidth = w * 0.022f,
                    cap = StrokeCap.Round
                )
            }
            "hood" -> {
                val hoodPath = Path().apply {
                    moveTo(cx, h * 0.08f)
                    lineTo(cx + w * 0.25f, h * 0.28f)
                    lineTo(cx - w * 0.25f, h * 0.28f)
                    close()
                }
                drawPath(hoodPath, color = Color(0xFF1F6E50))
                drawCircle(color = Color(0xFFF6C85F), radius = w * 0.025f, center = Offset(cx, h * 0.09f))
            }
        }

        // Hand-held / corner Accessories
        when (accessoryId) {
            "loupe" -> {
                drawCircle(
                    color = Color(0xFFAADDF5).copy(alpha = 0.7f),
                    radius = w * 0.075f,
                    center = Offset(cx + w * 0.24f, h * 0.73f)
                )
                drawCircle(
                    color = Color(0xFFF6C85F),
                    radius = w * 0.075f,
                    center = Offset(cx + w * 0.24f, h * 0.73f),
                    style = Stroke(width = w * 0.022f)
                )
                drawLine(
                    color = Color(0xFF6B3E26),
                    start = Offset(cx + w * 0.29f, h * 0.78f),
                    end = Offset(cx + w * 0.36f, h * 0.86f),
                    strokeWidth = w * 0.028f,
                    cap = StrokeCap.Round
                )
            }
            "watch" -> {
                drawCircle(
                    color = Color(0xFFF6C85F),
                    radius = w * 0.065f,
                    center = Offset(cx + w * 0.24f, h * 0.76f)
                )
                drawCircle(
                    color = Color.White,
                    radius = w * 0.048f,
                    center = Offset(cx + w * 0.24f, h * 0.76f)
                )
                drawLine(
                    color = Color(0xFF2D1E1B),
                    start = Offset(cx + w * 0.24f, h * 0.76f),
                    end = Offset(cx + w * 0.24f, h * 0.725f),
                    strokeWidth = w * 0.012f
                )
                drawLine(
                    color = Color(0xFF2D1E1B),
                    start = Offset(cx + w * 0.24f, h * 0.76f),
                    end = Offset(cx + w * 0.27f, h * 0.76f),
                    strokeWidth = w * 0.012f
                )
            }
            "notebook" -> {
                drawRoundRect(
                    color = Color(0xFF8C3B2B),
                    topLeft = Offset(cx + w * 0.17f, h * 0.69f),
                    size = Size(w * 0.14f, h * 0.17f),
                    cornerRadius = CornerRadius(w * 0.02f, w * 0.02f)
                )
                drawLine(
                    color = Color(0xFFF6C85F),
                    start = Offset(cx + w * 0.20f, h * 0.74f),
                    end = Offset(cx + w * 0.28f, h * 0.74f),
                    strokeWidth = w * 0.015f
                )
                drawLine(
                    color = Color(0xFFF6C85F),
                    start = Offset(cx + w * 0.20f, h * 0.79f),
                    end = Offset(cx + w * 0.26f, h * 0.79f),
                    strokeWidth = w * 0.015f
                )
            }
        }
    }
}
