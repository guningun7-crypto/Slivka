package com.example.ui.components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.data.model.HiddenItemKind

internal fun DrawScope.drawExtraHiddenItemShape(
    kind: HiddenItemKind,
    w: Float,
    h: Float,
    alpha: Float
) {
    val gold = Color(0xFFE6B33D).copy(alpha = alpha)
    val darkGold = Color(0xFF9B6A12).copy(alpha = alpha)
    val crimson = Color(0xFFC82D44).copy(alpha = alpha)
    val silver = Color(0xFFD8E4F0).copy(alpha = alpha)
    val wood = Color(0xFF8C5327).copy(alpha = alpha)
    val parchment = Color(0xFFF3E2BA).copy(alpha = alpha)
    val emerald = Color(0xFF28966C).copy(alpha = alpha)
    val sky = Color(0xFF87CEEB).copy(alpha = alpha)
    val frost = Color(0xFFB3E5FC).copy(alpha = alpha)
    val deepBlue = Color(0xFF1E56A0).copy(alpha = alpha)

    when (kind) {
        HiddenItemKind.ICE_CROWN -> {
            val crown = Path().apply {
                moveTo(w * 0.14f, h * 0.74f)
                lineTo(w * 0.18f, h * 0.34f)
                lineTo(w * 0.36f, h * 0.52f)
                lineTo(w * 0.50f, h * 0.18f)
                lineTo(w * 0.64f, h * 0.52f)
                lineTo(w * 0.82f, h * 0.34f)
                lineTo(w * 0.86f, h * 0.74f)
                close()
            }
            drawPath(crown, color = frost)
            drawPath(crown, color = Color.White.copy(alpha = 0.9f * alpha), style = Stroke(width = w * 0.04f))
            drawCircle(color = sky, radius = w * 0.055f, center = Offset(w * 0.50f, h * 0.58f))
        }
        HiddenItemKind.MUFF -> {
            drawRoundRect(
                color = Color(0xFF6D4C41).copy(alpha = alpha),
                topLeft = Offset(w * 0.16f, h * 0.28f),
                size = Size(w * 0.68f, h * 0.46f),
                cornerRadius = CornerRadius(w * 0.18f, w * 0.18f)
            )
            drawRoundRect(
                color = Color(0xFFF5EFE6).copy(alpha = alpha),
                topLeft = Offset(w * 0.20f, h * 0.34f),
                size = Size(w * 0.60f, h * 0.12f),
                cornerRadius = CornerRadius(w * 0.08f, w * 0.08f)
            )
            drawCircle(color = crimson, radius = w * 0.06f, center = Offset(w * 0.5f, h * 0.55f))
        }
        HiddenItemKind.REINDEER_BELL -> {
            drawCircle(color = gold, radius = w * 0.28f, center = Offset(w * 0.5f, h * 0.54f))
            drawCircle(color = darkGold, radius = w * 0.08f, center = Offset(w * 0.5f, h * 0.20f), style = Stroke(width = w * 0.04f))
            drawLine(color = darkGold, start = Offset(w * 0.32f, h * 0.58f), end = Offset(w * 0.68f, h * 0.58f), strokeWidth = w * 0.05f, cap = StrokeCap.Round)
            drawCircle(color = darkGold, radius = w * 0.06f, center = Offset(w * 0.5f, h * 0.60f))
        }
        HiddenItemKind.SNOWFLAKE -> {
            val c = Offset(w * 0.5f, h * 0.5f)
            drawLine(color = frost, start = Offset(w * 0.5f, h * 0.14f), end = Offset(w * 0.5f, h * 0.86f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
            drawLine(color = frost, start = Offset(w * 0.18f, h * 0.32f), end = Offset(w * 0.82f, h * 0.68f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
            drawLine(color = frost, start = Offset(w * 0.82f, h * 0.32f), end = Offset(w * 0.18f, h * 0.68f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
            drawCircle(color = Color.White.copy(alpha = 0.9f * alpha), radius = w * 0.08f, center = c)
        }
        HiddenItemKind.GOLDEN_CHAIN -> {
            drawOval(color = gold, topLeft = Offset(w * 0.16f, h * 0.30f), size = Size(w * 0.36f, h * 0.24f), style = Stroke(width = w * 0.07f))
            drawOval(color = darkGold, topLeft = Offset(w * 0.34f, h * 0.40f), size = Size(w * 0.36f, h * 0.24f), style = Stroke(width = w * 0.07f))
            drawOval(color = gold, topLeft = Offset(w * 0.50f, h * 0.50f), size = Size(w * 0.36f, h * 0.24f), style = Stroke(width = w * 0.07f))
        }
        HiddenItemKind.SPECTACLES -> {
            drawCircle(color = sky.copy(alpha = 0.45f * alpha), radius = w * 0.17f, center = Offset(w * 0.30f, h * 0.52f))
            drawCircle(color = sky.copy(alpha = 0.45f * alpha), radius = w * 0.17f, center = Offset(w * 0.70f, h * 0.52f))
            drawCircle(color = gold, radius = w * 0.17f, center = Offset(w * 0.30f, h * 0.52f), style = Stroke(width = w * 0.055f))
            drawCircle(color = gold, radius = w * 0.17f, center = Offset(w * 0.70f, h * 0.52f), style = Stroke(width = w * 0.055f))
            drawLine(color = gold, start = Offset(w * 0.47f, h * 0.50f), end = Offset(w * 0.53f, h * 0.50f), strokeWidth = w * 0.05f)
        }
        HiddenItemKind.BOOK_SPELLS -> {
            drawRoundRect(color = Color(0xFF6A1B4D).copy(alpha = alpha), topLeft = Offset(w * 0.22f, h * 0.18f), size = Size(w * 0.56f, h * 0.66f), cornerRadius = CornerRadius(w * 0.06f, w * 0.06f))
            drawRect(color = parchment, topLeft = Offset(w * 0.28f, h * 0.22f), size = Size(w * 0.46f, h * 0.56f))
            drawRoundRect(color = Color(0xFF4A1035).copy(alpha = alpha), topLeft = Offset(w * 0.20f, h * 0.18f), size = Size(w * 0.12f, h * 0.66f), cornerRadius = CornerRadius(w * 0.04f, w * 0.04f))
            drawCircle(color = gold, radius = w * 0.09f, center = Offset(w * 0.54f, h * 0.50f))
        }
        HiddenItemKind.INKWELL -> {
            drawRoundRect(color = deepBlue, topLeft = Offset(w * 0.26f, h * 0.46f), size = Size(w * 0.44f, h * 0.36f), cornerRadius = CornerRadius(w * 0.08f, w * 0.08f))
            drawRoundRect(color = gold, topLeft = Offset(w * 0.36f, h * 0.38f), size = Size(w * 0.24f, h * 0.10f), cornerRadius = CornerRadius(w * 0.04f, w * 0.04f))
            drawLine(color = parchment, start = Offset(w * 0.48f, h * 0.44f), end = Offset(w * 0.78f, h * 0.16f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
        }
        HiddenItemKind.MAGIC_LAMP -> {
            drawOval(color = gold, topLeft = Offset(w * 0.24f, h * 0.44f), size = Size(w * 0.50f, h * 0.28f))
            drawLine(color = gold, start = Offset(w * 0.70f, h * 0.56f), end = Offset(w * 0.88f, h * 0.38f), strokeWidth = w * 0.08f, cap = StrokeCap.Round)
            drawArc(color = darkGold, startAngle = 90f, sweepAngle = 220f, useCenter = false, topLeft = Offset(w * 0.12f, h * 0.42f), size = Size(w * 0.18f, h * 0.22f), style = Stroke(width = w * 0.05f))
            drawRoundRect(color = darkGold, topLeft = Offset(w * 0.34f, h * 0.72f), size = Size(w * 0.30f, h * 0.08f), cornerRadius = CornerRadius(w * 0.04f, w * 0.04f))
            drawCircle(color = crimson, radius = w * 0.05f, center = Offset(w * 0.49f, h * 0.39f))
        }
        HiddenItemKind.CARPET_ROLL -> {
            drawRoundRect(color = crimson, topLeft = Offset(w * 0.16f, h * 0.38f), size = Size(w * 0.68f, h * 0.28f), cornerRadius = CornerRadius(w * 0.10f, w * 0.10f))
            drawRect(color = gold, topLeft = Offset(w * 0.30f, h * 0.38f), size = Size(w * 0.10f, h * 0.28f))
            drawRect(color = gold, topLeft = Offset(w * 0.60f, h * 0.38f), size = Size(w * 0.10f, h * 0.28f))
            drawCircle(color = parchment, radius = w * 0.10f, center = Offset(w * 0.80f, h * 0.52f))
        }
        HiddenItemKind.SAPPHIRE_GEM -> {
            val gem = Path().apply {
                moveTo(w * 0.34f, h * 0.22f)
                lineTo(w * 0.66f, h * 0.22f)
                lineTo(w * 0.82f, h * 0.46f)
                lineTo(w * 0.50f, h * 0.82f)
                lineTo(w * 0.18f, h * 0.46f)
                close()
            }
            drawPath(gem, color = deepBlue)
            drawPath(gem, color = sky, style = Stroke(width = w * 0.045f))
            drawCircle(color = Color.White.copy(alpha = 0.75f * alpha), radius = w * 0.06f, center = Offset(w * 0.40f, h * 0.38f))
        }
        HiddenItemKind.AMPHORA -> {
            drawOval(color = Color(0xFFD27D46).copy(alpha = alpha), topLeft = Offset(w * 0.28f, h * 0.34f), size = Size(w * 0.44f, h * 0.46f))
            drawRoundRect(color = darkGold, topLeft = Offset(w * 0.38f, h * 0.20f), size = Size(w * 0.24f, h * 0.16f), cornerRadius = CornerRadius(w * 0.04f, w * 0.04f))
            drawArc(color = darkGold, startAngle = 90f, sweepAngle = 180f, useCenter = false, topLeft = Offset(w * 0.18f, h * 0.34f), size = Size(w * 0.18f, h * 0.22f), style = Stroke(width = w * 0.045f))
            drawArc(color = darkGold, startAngle = -90f, sweepAngle = 180f, useCenter = false, topLeft = Offset(w * 0.64f, h * 0.34f), size = Size(w * 0.18f, h * 0.22f), style = Stroke(width = w * 0.045f))
        }
        HiddenItemKind.HOURGLASS -> {
            drawLine(color = wood, start = Offset(w * 0.26f, h * 0.20f), end = Offset(w * 0.74f, h * 0.20f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
            drawLine(color = wood, start = Offset(w * 0.26f, h * 0.80f), end = Offset(w * 0.74f, h * 0.80f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
            val glass = Path().apply {
                moveTo(w * 0.32f, h * 0.22f)
                lineTo(w * 0.68f, h * 0.22f)
                lineTo(w * 0.50f, h * 0.50f)
                lineTo(w * 0.68f, h * 0.78f)
                lineTo(w * 0.32f, h * 0.78f)
                lineTo(w * 0.50f, h * 0.50f)
                close()
            }
            drawPath(glass, color = sky.copy(alpha = 0.65f * alpha))
            drawPath(glass, color = gold, style = Stroke(width = w * 0.04f))
            drawCircle(color = gold, radius = w * 0.07f, center = Offset(w * 0.50f, h * 0.70f))
        }
        HiddenItemKind.SILVER_CASKET -> {
            drawRoundRect(color = silver, topLeft = Offset(w * 0.18f, h * 0.36f), size = Size(w * 0.64f, h * 0.42f), cornerRadius = CornerRadius(w * 0.06f, w * 0.06f))
            drawRoundRect(color = Color(0xFF607D8B).copy(alpha = alpha), topLeft = Offset(w * 0.16f, h * 0.28f), size = Size(w * 0.68f, h * 0.14f), cornerRadius = CornerRadius(w * 0.05f, w * 0.05f))
            drawCircle(color = gold, radius = w * 0.06f, center = Offset(w * 0.50f, h * 0.48f))
        }
        HiddenItemKind.MITTENS -> {
            drawRoundRect(color = crimson, topLeft = Offset(w * 0.22f, h * 0.24f), size = Size(w * 0.34f, h * 0.54f), cornerRadius = CornerRadius(w * 0.14f, w * 0.14f))
            drawRoundRect(color = crimson, topLeft = Offset(w * 0.46f, h * 0.30f), size = Size(w * 0.32f, h * 0.50f), cornerRadius = CornerRadius(w * 0.14f, w * 0.14f))
            drawRect(color = parchment, topLeft = Offset(w * 0.22f, h * 0.66f), size = Size(w * 0.34f, h * 0.10f))
            drawRect(color = parchment, topLeft = Offset(w * 0.46f, h * 0.68f), size = Size(w * 0.32f, h * 0.10f))
        }
        HiddenItemKind.FROST_STAFF -> {
            drawLine(color = wood, start = Offset(w * 0.26f, h * 0.84f), end = Offset(w * 0.68f, h * 0.28f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
            drawCircle(color = frost, radius = w * 0.14f, center = Offset(w * 0.70f, h * 0.26f))
            drawCircle(color = Color.White.copy(alpha = 0.85f * alpha), radius = w * 0.07f, center = Offset(w * 0.67f, h * 0.23f))
        }
        HiddenItemKind.SAMOVAR -> {
            drawOval(color = gold, topLeft = Offset(w * 0.26f, h * 0.30f), size = Size(w * 0.48f, h * 0.42f))
            drawRoundRect(color = darkGold, topLeft = Offset(w * 0.38f, h * 0.18f), size = Size(w * 0.24f, h * 0.14f), cornerRadius = CornerRadius(w * 0.04f, w * 0.04f))
            drawRoundRect(color = darkGold, topLeft = Offset(w * 0.32f, h * 0.72f), size = Size(w * 0.36f, h * 0.10f), cornerRadius = CornerRadius(w * 0.04f, w * 0.04f))
            drawLine(color = darkGold, start = Offset(w * 0.50f, h * 0.56f), end = Offset(w * 0.74f, h * 0.58f), strokeWidth = w * 0.05f, cap = StrokeCap.Round)
        }
        HiddenItemKind.GOLDEN_NUT -> {
            drawOval(color = gold, topLeft = Offset(w * 0.24f, h * 0.24f), size = Size(w * 0.52f, h * 0.54f))
            drawOval(color = darkGold, topLeft = Offset(w * 0.24f, h * 0.24f), size = Size(w * 0.52f, h * 0.54f), style = Stroke(width = w * 0.04f))
            drawCircle(color = emerald, radius = w * 0.10f, center = Offset(w * 0.50f, h * 0.50f))
        }
        HiddenItemKind.SWAN_CROWN -> {
            drawArc(color = gold, startAngle = 180f, sweepAngle = 180f, useCenter = false, topLeft = Offset(w * 0.18f, h * 0.38f), size = Size(w * 0.64f, h * 0.44f), style = Stroke(width = w * 0.08f, cap = StrokeCap.Round))
            drawCircle(color = Color.White.copy(alpha = 0.95f * alpha), radius = w * 0.09f, center = Offset(w * 0.50f, h * 0.28f))
            drawCircle(color = sky, radius = w * 0.05f, center = Offset(w * 0.30f, h * 0.42f))
            drawCircle(color = sky, radius = w * 0.05f, center = Offset(w * 0.70f, h * 0.42f))
        }
        HiddenItemKind.ANCHOR -> {
            drawCircle(color = darkGold, radius = w * 0.09f, center = Offset(w * 0.50f, h * 0.20f), style = Stroke(width = w * 0.05f))
            drawLine(color = darkGold, start = Offset(w * 0.50f, h * 0.28f), end = Offset(w * 0.50f, h * 0.80f), strokeWidth = w * 0.08f, cap = StrokeCap.Round)
            drawLine(color = darkGold, start = Offset(w * 0.32f, h * 0.40f), end = Offset(w * 0.68f, h * 0.40f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
            drawArc(color = darkGold, startAngle = 15f, sweepAngle = 150f, useCenter = false, topLeft = Offset(w * 0.22f, h * 0.46f), size = Size(w * 0.56f, h * 0.34f), style = Stroke(width = w * 0.07f, cap = StrokeCap.Round))
        }
        HiddenItemKind.TELESCOPE -> {
            drawLine(color = wood, start = Offset(w * 0.16f, h * 0.72f), end = Offset(w * 0.76f, h * 0.28f), strokeWidth = w * 0.14f, cap = StrokeCap.Round)
            drawLine(color = gold, start = Offset(w * 0.56f, h * 0.42f), end = Offset(w * 0.82f, h * 0.24f), strokeWidth = w * 0.17f, cap = StrokeCap.Round)
            drawCircle(color = sky, radius = w * 0.06f, center = Offset(w * 0.82f, h * 0.24f))
        }
        HiddenItemKind.CORAL_BRANCH -> {
            drawLine(color = crimson, start = Offset(w * 0.50f, h * 0.84f), end = Offset(w * 0.50f, h * 0.26f), strokeWidth = w * 0.09f, cap = StrokeCap.Round)
            drawLine(color = crimson, start = Offset(w * 0.50f, h * 0.60f), end = Offset(w * 0.24f, h * 0.34f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
            drawLine(color = crimson, start = Offset(w * 0.50f, h * 0.50f), end = Offset(w * 0.76f, h * 0.28f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
            drawLine(color = Color(0xFFFF7043).copy(alpha = alpha), start = Offset(w * 0.36f, h * 0.46f), end = Offset(w * 0.32f, h * 0.24f), strokeWidth = w * 0.05f, cap = StrokeCap.Round)
        }
        HiddenItemKind.COIN_POUCH -> {
            drawOval(color = wood, topLeft = Offset(w * 0.22f, h * 0.36f), size = Size(w * 0.56f, h * 0.46f))
            drawLine(color = gold, start = Offset(w * 0.34f, h * 0.36f), end = Offset(w * 0.66f, h * 0.36f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
            drawOval(color = wood, topLeft = Offset(w * 0.36f, h * 0.18f), size = Size(w * 0.28f, h * 0.18f))
            drawCircle(color = gold, radius = w * 0.08f, center = Offset(w * 0.50f, h * 0.58f))
        }
        else -> Unit
    }
}
