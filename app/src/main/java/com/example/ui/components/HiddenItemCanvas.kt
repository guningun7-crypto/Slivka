package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import com.example.data.model.HiddenItemKind

@Composable
fun HiddenItemCanvas(
    kind: HiddenItemKind,
    modifier: Modifier = Modifier,
    rotationDeg: Float = 0f,
    ambientAlpha: Float = 1f,
    showSoftShadow: Boolean = false
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        if (showSoftShadow) {
            drawOval(
                color = Color.Black.copy(alpha = 0.26f * ambientAlpha),
                topLeft = Offset(w * 0.14f, h * 0.72f),
                size = Size(w * 0.72f, h * 0.22f)
            )
        }
        rotate(degrees = rotationDeg, pivot = Offset(w / 2f, h / 2f)) {
            drawHiddenItemShape(kind, w, h, ambientAlpha)
        }
    }
}

private fun DrawScope.drawHiddenItemShape(
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

    when (kind) {
        HiddenItemKind.BASKET -> {
            drawArc(
                color = wood,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(w * 0.24f, h * 0.14f),
                size = Size(w * 0.52f, h * 0.52f),
                style = Stroke(width = w * 0.07f)
            )
            val body = Path().apply {
                moveTo(w * 0.15f, h * 0.45f)
                lineTo(w * 0.85f, h * 0.45f)
                lineTo(w * 0.74f, h * 0.84f)
                lineTo(w * 0.26f, h * 0.84f)
                close()
            }
            drawPath(body, color = Color(0xFFC28342).copy(alpha = alpha))
            drawPath(body, color = wood, style = Stroke(width = w * 0.04f))
            drawOval(color = crimson, topLeft = Offset(w * 0.25f, h * 0.39f), size = Size(w * 0.5f, h * 0.14f))
        }
        HiddenItemKind.BROOCH, HiddenItemKind.FAIRY_BROOCH -> {
            drawOval(color = gold, topLeft = Offset(w * 0.22f, h * 0.18f), size = Size(w * 0.56f, h * 0.64f))
            drawOval(
                color = if (kind == HiddenItemKind.FAIRY_BROOCH) sky else crimson,
                topLeft = Offset(w * 0.32f, h * 0.28f),
                size = Size(w * 0.36f, h * 0.44f)
            )
            drawCircle(color = Color.White.copy(alpha = 0.7f * alpha), radius = w * 0.06f, center = Offset(w * 0.44f, h * 0.42f))
        }
        HiddenItemKind.RED_HOOD -> {
            val path = Path().apply {
                moveTo(w * 0.50f, h * 0.14f)
                quadraticTo(w * 0.84f, h * 0.35f, w * 0.82f, h * 0.82f)
                lineTo(w * 0.18f, h * 0.82f)
                quadraticTo(w * 0.18f, h * 0.38f, w * 0.50f, h * 0.14f)
                close()
            }
            drawPath(path, color = crimson)
            drawPath(path, color = Color(0xFF7A1424).copy(alpha = alpha), style = Stroke(width = w * 0.04f))
        }
        HiddenItemKind.COMPASS, HiddenItemKind.POCKET_WATCH -> {
            drawCircle(color = gold, radius = w * 0.08f, center = Offset(w * 0.5f, h * 0.16f), style = Stroke(width = w * 0.04f))
            drawCircle(color = gold, radius = w * 0.32f, center = Offset(w * 0.5f, h * 0.56f))
            drawCircle(color = parchment, radius = w * 0.24f, center = Offset(w * 0.5f, h * 0.56f))
            drawLine(color = crimson, start = Offset(w * 0.5f, h * 0.56f), end = Offset(w * 0.5f, h * 0.36f), strokeWidth = w * 0.045f, cap = StrokeCap.Round)
            drawLine(color = darkGold, start = Offset(w * 0.5f, h * 0.56f), end = Offset(w * 0.65f, h * 0.62f), strokeWidth = w * 0.04f, cap = StrokeCap.Round)
        }
        HiddenItemKind.KEYS, HiddenItemKind.CARRIAGE_KEY -> {
            drawCircle(color = gold, radius = w * 0.16f, center = Offset(w * 0.32f, h * 0.36f), style = Stroke(width = w * 0.07f))
            drawLine(color = gold, start = Offset(w * 0.44f, h * 0.46f), end = Offset(w * 0.80f, h * 0.78f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
            drawLine(color = gold, start = Offset(w * 0.67f, h * 0.67f), end = Offset(w * 0.58f, h * 0.78f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
            drawLine(color = gold, start = Offset(w * 0.76f, h * 0.74f), end = Offset(w * 0.68f, h * 0.85f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
        }
        HiddenItemKind.SCROLL -> {
            drawRoundRect(color = parchment, topLeft = Offset(w * 0.18f, h * 0.26f), size = Size(w * 0.64f, h * 0.48f), cornerRadius = CornerRadius(w * 0.08f, w * 0.08f))
            drawRoundRect(color = darkGold, topLeft = Offset(w * 0.14f, h * 0.22f), size = Size(w * 0.14f, h * 0.56f), cornerRadius = CornerRadius(w * 0.06f, w * 0.06f))
            drawRoundRect(color = darkGold, topLeft = Offset(w * 0.72f, h * 0.22f), size = Size(w * 0.14f, h * 0.56f), cornerRadius = CornerRadius(w * 0.06f, w * 0.06f))
            drawLine(color = crimson, start = Offset(w * 0.48f, h * 0.26f), end = Offset(w * 0.48f, h * 0.74f), strokeWidth = w * 0.06f)
        }
        HiddenItemKind.RIBBON -> {
            val bow = Path().apply {
                moveTo(w * 0.5f, h * 0.48f)
                lineTo(w * 0.18f, h * 0.24f)
                lineTo(w * 0.18f, h * 0.62f)
                close()
                moveTo(w * 0.5f, h * 0.48f)
                lineTo(w * 0.82f, h * 0.24f)
                lineTo(w * 0.82f, h * 0.62f)
                close()
            }
            drawPath(bow, color = crimson)
            drawLine(color = crimson, start = Offset(w * 0.46f, h * 0.50f), end = Offset(w * 0.30f, h * 0.84f), strokeWidth = w * 0.08f, cap = StrokeCap.Round)
            drawLine(color = crimson, start = Offset(w * 0.54f, h * 0.50f), end = Offset(w * 0.70f, h * 0.84f), strokeWidth = w * 0.08f, cap = StrokeCap.Round)
            drawCircle(color = gold, radius = w * 0.08f, center = Offset(w * 0.5f, h * 0.48f))
        }
        HiddenItemKind.MEDALLION, HiddenItemKind.OWL_AMULET -> {
            drawArc(color = gold, startAngle = 180f, sweepAngle = 180f, useCenter = false, topLeft = Offset(w * 0.32f, h * 0.12f), size = Size(w * 0.36f, h * 0.36f), style = Stroke(width = w * 0.04f))
            drawCircle(color = if (kind == HiddenItemKind.OWL_AMULET) gold else silver, radius = w * 0.26f, center = Offset(w * 0.5f, h * 0.58f))
            drawCircle(color = Color(0xFF4B236E).copy(alpha = alpha), radius = w * 0.16f, center = Offset(w * 0.5f, h * 0.58f))
            drawCircle(color = gold, radius = w * 0.06f, center = Offset(w * 0.5f, h * 0.58f))
        }
        HiddenItemKind.THIMBLE -> {
            val thimble = Path().apply {
                moveTo(w * 0.32f, h * 0.32f)
                quadraticTo(w * 0.5f, h * 0.14f, w * 0.68f, h * 0.32f)
                lineTo(w * 0.74f, h * 0.80f)
                lineTo(w * 0.26f, h * 0.80f)
                close()
            }
            drawPath(thimble, color = gold)
            drawPath(thimble, color = darkGold, style = Stroke(width = w * 0.04f))
            for (dotX in listOf(0.42f, 0.58f)) {
                for (dotY in listOf(0.44f, 0.58f)) {
                    drawCircle(color = darkGold, radius = w * 0.03f, center = Offset(w * dotX, h * dotY))
                }
            }
        }
        HiddenItemKind.TEAPOT -> {
            drawOval(color = Color(0xFF3E7CB1).copy(alpha = alpha), topLeft = Offset(w * 0.24f, h * 0.36f), size = Size(w * 0.52f, h * 0.44f))
            drawArc(color = Color(0xFF3E7CB1).copy(alpha = alpha), startAngle = -90f, sweepAngle = 180f, useCenter = false, topLeft = Offset(w * 0.66f, h * 0.42f), size = Size(w * 0.18f, h * 0.26f), style = Stroke(width = w * 0.06f))
            drawLine(color = Color(0xFF3E7CB1).copy(alpha = alpha), start = Offset(w * 0.26f, h * 0.52f), end = Offset(w * 0.12f, h * 0.38f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
            drawCircle(color = gold, radius = w * 0.06f, center = Offset(w * 0.5f, h * 0.31f))
        }
        HiddenItemKind.SLIPPER -> {
            val slipper = Path().apply {
                moveTo(w * 0.18f, h * 0.52f)
                quadraticTo(w * 0.34f, h * 0.66f, w * 0.52f, h * 0.50f)
                lineTo(w * 0.64f, h * 0.30f)
                lineTo(w * 0.78f, h * 0.72f)
                lineTo(w * 0.68f, h * 0.72f)
                lineTo(w * 0.62f, h * 0.56f)
                quadraticTo(w * 0.42f, h * 0.76f, w * 0.16f, h * 0.68f)
                close()
            }
            drawPath(slipper, color = sky)
            drawPath(slipper, color = Color.White.copy(alpha = 0.9f * alpha), style = Stroke(width = w * 0.04f))
        }
        HiddenItemKind.MASK -> {
            drawOval(color = Color(0xFF5A2D82).copy(alpha = alpha), topLeft = Offset(w * 0.12f, h * 0.34f), size = Size(w * 0.76f, h * 0.36f))
            drawOval(color = Color.White.copy(alpha = 0.85f * alpha), topLeft = Offset(w * 0.24f, h * 0.44f), size = Size(w * 0.20f, h * 0.14f))
            drawOval(color = Color.White.copy(alpha = 0.85f * alpha), topLeft = Offset(w * 0.56f, h * 0.44f), size = Size(w * 0.20f, h * 0.14f))
            drawOval(color = gold, topLeft = Offset(w * 0.12f, h * 0.34f), size = Size(w * 0.76f, h * 0.36f), style = Stroke(width = w * 0.04f))
        }
        HiddenItemKind.GOBLET -> {
            val cup = Path().apply {
                moveTo(w * 0.26f, h * 0.20f)
                lineTo(w * 0.74f, h * 0.20f)
                quadraticTo(w * 0.70f, h * 0.56f, w * 0.50f, h * 0.56f)
                quadraticTo(w * 0.30f, h * 0.56f, w * 0.26f, h * 0.20f)
                close()
            }
            drawPath(cup, color = silver)
            drawLine(color = silver, start = Offset(w * 0.5f, h * 0.56f), end = Offset(w * 0.5f, h * 0.80f), strokeWidth = w * 0.08f)
            drawRoundRect(color = silver, topLeft = Offset(w * 0.32f, h * 0.78f), size = Size(w * 0.36f, h * 0.08f), cornerRadius = CornerRadius(w * 0.04f, w * 0.04f))
        }
        HiddenItemKind.ROSE -> {
            drawLine(color = emerald, start = Offset(w * 0.5f, h * 0.48f), end = Offset(w * 0.5f, h * 0.86f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
            drawCircle(color = crimson, radius = w * 0.18f, center = Offset(w * 0.5f, h * 0.32f))
            drawCircle(color = Color(0xFFE84A63).copy(alpha = alpha), radius = w * 0.10f, center = Offset(w * 0.47f, h * 0.29f))
        }
        HiddenItemKind.WAX_SEAL -> {
            drawCircle(color = crimson, radius = w * 0.32f, center = Offset(w * 0.5f, h * 0.5f))
            drawCircle(color = gold, radius = w * 0.19f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(width = w * 0.04f))
            drawCircle(color = gold, radius = w * 0.07f, center = Offset(w * 0.5f, h * 0.5f))
        }
        HiddenItemKind.CRYSTAL_SHARD -> {
            val shard = Path().apply {
                moveTo(w * 0.50f, h * 0.12f)
                lineTo(w * 0.74f, h * 0.48f)
                lineTo(w * 0.50f, h * 0.88f)
                lineTo(w * 0.26f, h * 0.48f)
                close()
            }
            drawPath(shard, color = sky)
            drawPath(shard, color = Color.White.copy(alpha = 0.85f * alpha), style = Stroke(width = w * 0.04f))
        }
        HiddenItemKind.MIRROR_HAND -> {
            drawOval(color = gold, topLeft = Offset(w * 0.24f, h * 0.14f), size = Size(w * 0.52f, h * 0.48f))
            drawOval(color = sky, topLeft = Offset(w * 0.31f, h * 0.20f), size = Size(w * 0.38f, h * 0.36f))
            drawLine(color = gold, start = Offset(w * 0.5f, h * 0.62f), end = Offset(w * 0.5f, h * 0.88f), strokeWidth = w * 0.08f, cap = StrokeCap.Round)
        }
        HiddenItemKind.YARN_BALL -> {
            drawCircle(color = gold, radius = w * 0.30f, center = Offset(w * 0.5f, h * 0.5f))
            drawArc(color = darkGold, startAngle = 15f, sweepAngle = 150f, useCenter = false, topLeft = Offset(w * 0.26f, h * 0.30f), size = Size(w * 0.48f, h * 0.40f), style = Stroke(width = w * 0.04f))
            drawArc(color = darkGold, startAngle = 200f, sweepAngle = 140f, useCenter = false, topLeft = Offset(w * 0.24f, h * 0.26f), size = Size(w * 0.52f, h * 0.42f), style = Stroke(width = w * 0.04f))
        }
        HiddenItemKind.FIRE_FEATHER -> {
            val feather = Path().apply {
                moveTo(w * 0.22f, h * 0.80f)
                quadraticTo(w * 0.34f, h * 0.24f, w * 0.82f, h * 0.18f)
                quadraticTo(w * 0.68f, h * 0.62f, w * 0.22f, h * 0.80f)
                close()
            }
            drawPath(feather, color = Color(0xFFFF6D2E).copy(alpha = alpha))
            drawCircle(color = gold, radius = w * 0.09f, center = Offset(w * 0.62f, h * 0.36f))
        }
        HiddenItemKind.CLAY_POT -> {
            drawOval(color = Color(0xFFB5653B).copy(alpha = alpha), topLeft = Offset(w * 0.22f, h * 0.34f), size = Size(w * 0.56f, h * 0.50f))
            drawRoundRect(color = wood, topLeft = Offset(w * 0.28f, h * 0.22f), size = Size(w * 0.44f, h * 0.14f), cornerRadius = CornerRadius(w * 0.05f, w * 0.05f))
        }
        HiddenItemKind.WOODEN_SPOON -> {
            drawOval(color = Color(0xFFD4924B).copy(alpha = alpha), topLeft = Offset(w * 0.44f, h * 0.16f), size = Size(w * 0.36f, h * 0.36f))
            drawLine(color = wood, start = Offset(w * 0.48f, h * 0.46f), end = Offset(w * 0.20f, h * 0.82f), strokeWidth = w * 0.09f, cap = StrokeCap.Round)
        }
        HiddenItemKind.GLOW_MUSHROOM -> {
            drawRoundRect(color = parchment, topLeft = Offset(w * 0.40f, h * 0.50f), size = Size(w * 0.20f, h * 0.32f), cornerRadius = CornerRadius(w * 0.06f, w * 0.06f))
            drawArc(color = emerald, startAngle = 180f, sweepAngle = 180f, useCenter = true, topLeft = Offset(w * 0.18f, h * 0.20f), size = Size(w * 0.64f, h * 0.58f))
            drawCircle(color = Color(0xFFA7F3D0).copy(alpha = alpha), radius = w * 0.055f, center = Offset(w * 0.40f, h * 0.35f))
            drawCircle(color = Color(0xFFA7F3D0).copy(alpha = alpha), radius = w * 0.045f, center = Offset(w * 0.60f, h * 0.38f))
        }
        HiddenItemKind.RUNE_STONE -> {
            drawRoundRect(color = Color(0xFF6B7280).copy(alpha = alpha), topLeft = Offset(w * 0.24f, h * 0.18f), size = Size(w * 0.52f, h * 0.64f), cornerRadius = CornerRadius(w * 0.14f, w * 0.14f))
            drawLine(color = sky, start = Offset(w * 0.50f, h * 0.30f), end = Offset(w * 0.50f, h * 0.70f), strokeWidth = w * 0.05f, cap = StrokeCap.Round)
            drawLine(color = sky, start = Offset(w * 0.36f, h * 0.44f), end = Offset(w * 0.64f, h * 0.56f), strokeWidth = w * 0.045f, cap = StrokeCap.Round)
        }
        HiddenItemKind.DEW_VIAL -> {
            drawCircle(color = emerald, radius = w * 0.25f, center = Offset(w * 0.5f, h * 0.60f))
            drawRoundRect(color = silver, topLeft = Offset(w * 0.41f, h * 0.24f), size = Size(w * 0.18f, h * 0.18f), cornerRadius = CornerRadius(w * 0.04f, w * 0.04f))
            drawRoundRect(color = wood, topLeft = Offset(w * 0.43f, h * 0.16f), size = Size(w * 0.14f, h * 0.10f), cornerRadius = CornerRadius(w * 0.03f, w * 0.03f))
        }
        HiddenItemKind.TOWEL -> {
            drawRoundRect(color = parchment, topLeft = Offset(w * 0.24f, h * 0.18f), size = Size(w * 0.52f, h * 0.64f), cornerRadius = CornerRadius(w * 0.05f, w * 0.05f))
            drawRect(color = crimson, topLeft = Offset(w * 0.24f, h * 0.28f), size = Size(w * 0.52f, h * 0.08f))
            drawRect(color = crimson, topLeft = Offset(w * 0.24f, h * 0.64f), size = Size(w * 0.52f, h * 0.08f))
        }
        HiddenItemKind.LANTERN -> {
            drawArc(color = darkGold, startAngle = 180f, sweepAngle = 180f, useCenter = false, topLeft = Offset(w * 0.36f, h * 0.12f), size = Size(w * 0.28f, h * 0.22f), style = Stroke(width = w * 0.04f))
            drawRoundRect(color = darkGold, topLeft = Offset(w * 0.28f, h * 0.28f), size = Size(w * 0.44f, h * 0.52f), cornerRadius = CornerRadius(w * 0.08f, w * 0.08f))
            drawRoundRect(color = Color(0xFFFFE066).copy(alpha = alpha), topLeft = Offset(w * 0.36f, h * 0.36f), size = Size(w * 0.28f, h * 0.36f), cornerRadius = CornerRadius(w * 0.05f, w * 0.05f))
        }
        else -> drawExtraHiddenItemShape(kind, w, h, alpha)
    }
}
