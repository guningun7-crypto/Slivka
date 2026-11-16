package com.example.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.data.model.DetectiveProfile
import com.example.data.model.DifficultyLevel
import com.example.data.model.FairyLocationDef
import com.example.data.model.GameCatalog
import com.example.data.model.LocationProgress
import com.example.ui.components.DetectiveAvatarCanvas
import kotlin.math.roundToInt

@Composable
fun FairyMapScreen(
    profile: DetectiveProfile,
    locationsProgress: Map<String, LocationProgress>,
    onSelectDifficulty: (DifficultyLevel) -> Unit,
    onStartLocation: (FairyLocationDef, DifficultyLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeDifficulty = DifficultyLevel.fromId(profile.selectedDifficulty)
    var selectedLocation by remember { mutableStateOf(GameCatalog.locations.first()) }
    val selectedProgress = locationsProgress[selectedLocation.id]
    val isSelectedUnlocked = selectedProgress?.unlocked == true || selectedLocation.id == "cottage"
    val maxStars = GameCatalog.locations.size * 3

    val infiniteTransition = rememberInfiniteTransition(label = "mapPulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.45f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(1100), RepeatMode.Reverse),
        label = "pulseAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 640.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Top Detective Header Strip
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DetectiveAvatarCanvas(
                        gender = profile.gender,
                        skinToneIndex = profile.skinToneIndex.toInt(),
                        hairStyleIndex = profile.hairStyleIndex.toInt(),
                        hairColorIndex = profile.hairColorIndex.toInt(),
                        hatId = profile.hatId,
                        coatId = profile.coatId,
                        accessoryId = profile.accessoryId,
                        modifier = Modifier.size(56.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = profile.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (profile.gender == "girl") "Юная сыщица • ${profile.totalScore} очков" else "Юный сыщик • ${profile.totalScore} очков",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = "Звёзды",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "${profile.totalStars}/$maxStars",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                Icons.Filled.Lightbulb,
                                contentDescription = "Подсказки",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "${profile.hintsBalance}",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            Text(
                text = "Карта Сказочной страны (8 сказок)",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            // Tall Portrait Interactive Map for 8 Fairy-Tale Locations
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.68f)
                ) {
                    val mapWidthPx = constraints.maxWidth.toFloat()
                    val mapHeightPx = constraints.maxHeight.toFloat()
                    val density = LocalDensity.current

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Northern ice -> golden sands -> emerald forest -> warm parchment
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFCDE4F5),
                                    Color(0xFFF0DFB6),
                                    Color(0xFFCDE0BE),
                                    Color(0xFFE7D4AE)
                                )
                            )
                        )

                        // Winding enchanted river across the continent
                        val river = Path().apply {
                            moveTo(0f, size.height * 0.22f)
                            cubicTo(
                                size.width * 0.50f, size.height * 0.30f,
                                size.width * 0.30f, size.height * 0.62f,
                                size.width, size.height * 0.70f
                            )
                        }
                        drawPath(
                            path = river,
                            color = Color(0xFF72BBD4).copy(alpha = 0.58f),
                            style = Stroke(width = size.width * 0.055f, cap = StrokeCap.Round)
                        )

                        // Decorative forest and snowy mountain accents
                        val treeCenters = listOf(
                            0.10f to 0.72f, 0.88f to 0.66f, 0.12f to 0.53f,
                            0.88f to 0.44f, 0.14f to 0.34f, 0.50f to 0.90f
                        )
                        treeCenters.forEach { (tx, ty) ->
                            drawCircle(
                                color = Color(0xFF2E7D5B).copy(alpha = 0.38f),
                                radius = size.width * 0.048f,
                                center = Offset(size.width * tx, size.height * ty)
                            )
                        }

                        // Dashed cobblestone trail linking all 8 fairy tales
                        val pts = GameCatalog.locations.map {
                            Offset(size.width * it.mapXFraction, size.height * it.mapYFraction)
                        }
                        for (i in 0 until pts.lastIndex) {
                            drawLine(
                                color = Color(0xFF6B3E26).copy(alpha = 0.72f),
                                start = pts[i],
                                end = pts[i + 1],
                                strokeWidth = size.width * 0.013f,
                                cap = StrokeCap.Round,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 12f), 0f)
                            )
                        }
                    }

                    // 8 Interactive Location Nodes
                    val pinSizePx = with(density) { 54.dp.toPx() }
                    GameCatalog.locations.forEachIndexed { index, loc ->
                        val prog = locationsProgress[loc.id]
                        val unlocked = prog?.unlocked == true || index == 0
                        val completed = prog?.completed == true
                        val stars = (prog?.stars ?: 0L).toInt()
                        val isSelected = selectedLocation.id == loc.id

                        val xPx = (mapWidthPx * loc.mapXFraction - pinSizePx / 2f).roundToInt()
                        val yPx = (mapHeightPx * loc.mapYFraction - pinSizePx / 2f).roundToInt()

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .offset { IntOffset(xPx, yPx) }
                                .clickable { selectedLocation = loc }
                                .minimumInteractiveComponentSize()
                                .testTag("map_node_${loc.id}")
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            !unlocked -> MaterialTheme.colorScheme.surfaceVariant
                                            completed -> MaterialTheme.colorScheme.tertiaryContainer
                                            else -> MaterialTheme.colorScheme.primaryContainer
                                        }
                                    )
                                    .border(
                                        width = if (isSelected) 3.dp else 2.dp,
                                        color = when {
                                            isSelected -> MaterialTheme.colorScheme.secondary.copy(alpha = pulseAlpha)
                                            unlocked -> MaterialTheme.colorScheme.primary
                                            else -> MaterialTheme.colorScheme.outline
                                        },
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = loc.sceneDrawableRes),
                                    contentDescription = loc.title,
                                    contentScale = ContentScale.Crop,
                                    alpha = if (unlocked) 1f else 0.35f,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                )
                                if (!unlocked) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Black.copy(alpha = 0.45f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Filled.Lock,
                                            contentDescription = "Локация закрыта",
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                } else if (completed) {
                                    Icon(
                                        Icons.Filled.CheckCircle,
                                        contentDescription = "Дело раскрыто",
                                        tint = Color(0xFF4ADE80),
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .size(18.dp)
                                            .background(Color.Black.copy(alpha = 0.65f), CircleShape)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
                                shadowElevation = 2.dp
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "${index + 1}. ${loc.fairyTaleName}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                    )
                                    if (unlocked) {
                                        Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                                            repeat(3) { starIdx ->
                                                Icon(
                                                    imageVector = Icons.Filled.Star,
                                                    contentDescription = null,
                                                    tint = if (starIdx < stars) Color(0xFFF6B73C) else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                                    modifier = Modifier.size(10.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Difficulty Selector
            Text(
                text = "Уровень сложности",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DifficultyLevel.entries.forEach { diff ->
                    FilterChip(
                        selected = activeDifficulty == diff,
                        onClick = { onSelectDifficulty(diff) },
                        label = { Text(diff.title, style = MaterialTheme.typography.labelLarge) },
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("difficulty_${diff.id}")
                    )
                }
            }
            Text(
                text = activeDifficulty.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Selected Case Details Card
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = selectedLocation.title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Сказка: ${selectedLocation.fairyTaleName} • Герой: ${selectedLocation.heroName}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Text(
                        text = selectedLocation.shortSummary,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (selectedProgress != null && selectedProgress.bestScore > 0L) {
                        Text(
                            text = "Рекорд: ${selectedProgress.bestScore} очков • Лучшее время: ${selectedProgress.bestTimeSeconds} сек",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    Button(
                        onClick = { onStartLocation(selectedLocation, activeDifficulty) },
                        enabled = isSelectedUnlocked,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("start_investigation_button")
                    ) {
                        Icon(
                            imageVector = if (isSelectedUnlocked) Icons.Filled.PlayArrow else Icons.Filled.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isSelectedUnlocked) "Начать расследование" else "Раскройте предыдущую сказку",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
