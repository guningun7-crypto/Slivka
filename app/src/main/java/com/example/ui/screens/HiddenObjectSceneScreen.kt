package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.ZoomInMap
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.DialogueLine
import com.example.ui.components.HiddenItemCanvas
import com.example.ui.viewmodel.ActiveSceneState
import com.example.ui.viewmodel.ScenePhase
import kotlin.math.roundToInt

@Composable
fun HiddenObjectSceneScreen(
    scene: ActiveSceneState,
    hintsBalance: Long,
    onItemTapped: (String) -> Unit,
    onEmptyTapped: () -> Unit,
    onUseHint: () -> Unit,
    onAdvanceIntro: () -> Unit,
    onSkipIntro: () -> Unit,
    onAdvanceOutro: () -> Unit,
    onReplay: () -> Unit,
    onExit: () -> Unit
) {
    BackHandler { onExit() }

    var zoomScale by remember(scene.location.id, scene.difficulty.id) { mutableFloatStateOf(1f) }
    var panOffset by remember(scene.location.id, scene.difficulty.id) { mutableStateOf(Offset.Zero) }
    var wrongTapMarker by remember { mutableStateOf<Offset?>(null) }

    val infiniteTransition = rememberInfiniteTransition(label = "hintPulse")
    val hintPulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(tween(650), RepeatMode.Reverse),
        label = "hintPulseScale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF171123))
            .safeDrawingPadding()
    ) {
        // Top HUD Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onExit,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("scene_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Вернуться на карту",
                    tint = Color.White
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text(
                    text = scene.location.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = "${scene.difficulty.title} • Найдено ${scene.foundItemIds.size}/${scene.targetItems.size}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFFF6C85F)
                )
            }

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (scene.secondsRemaining <= 25) Color(0xFFB72B47) else Color(0xFF2B2040)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Timer,
                        contentDescription = "Таймер",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = formatSeconds(scene.secondsRemaining),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            }
        }

        // Sliva's Live Commentary Banner
        AnimatedVisibility(
            visible = scene.slivaBannerText != null && scene.phase == ScenePhase.SEARCHING,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_director_sliva),
                        contentDescription = "Слива Владиславовна",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = scene.slivaBannerText.orEmpty(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Interactive Hidden Object Canvas Viewport
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .clip(RoundedCornerShape(20.dp))
                    .clipToBounds()
                    .border(2.dp, Color(0xFFF6C85F).copy(alpha = 0.5f), RoundedCornerShape(20.dp))
            ) {
                val widthPx = constraints.maxWidth.toFloat()
                val heightPx = constraints.maxHeight.toFloat()
                val baseItemDp = (46f * scene.difficulty.itemScale).dp
                val itemSizePx = with(LocalDensity.current) { baseItemDp.toPx() }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(scene.phase) {
                            if (scene.phase == ScenePhase.SEARCHING) {
                                detectTransformGestures { _, pan, zoom, _ ->
                                    val newScale = (zoomScale * zoom).coerceIn(1f, 2.6f)
                                    val maxOffsetX = (widthPx * (newScale - 1f)) / 2f
                                    val maxOffsetY = (heightPx * (newScale - 1f)) / 2f
                                    zoomScale = newScale
                                    panOffset = if (newScale <= 1.02f) {
                                        Offset.Zero
                                    } else {
                                        Offset(
                                            x = (panOffset.x + pan.x).coerceIn(-maxOffsetX, maxOffsetX),
                                            y = (panOffset.y + pan.y).coerceIn(-maxOffsetY, maxOffsetY)
                                        )
                                    }
                                }
                            }
                        }
                        .pointerInput(scene.phase) {
                            if (scene.phase == ScenePhase.SEARCHING) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        if (zoomScale > 1.1f) {
                                            zoomScale = 1f
                                            panOffset = Offset.Zero
                                        } else {
                                            zoomScale = 1.9f
                                        }
                                    },
                                    onTap = { tapOffset ->
                                        wrongTapMarker = tapOffset
                                        onEmptyTapped()
                                    }
                                )
                            }
                        }
                        .graphicsLayer {
                            scaleX = zoomScale
                            scaleY = zoomScale
                            translationX = panOffset.x
                            translationY = panOffset.y
                        }
                ) {
                    Image(
                        painter = painterResource(id = scene.location.sceneDrawableRes),
                        contentDescription = scene.location.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Render Hidden Objects on Scene
                    scene.targetItems.forEach { item ->
                        val isFound = item.id in scene.foundItemIds
                        if (!isFound) {
                            val isHinted = scene.hintedItemId == item.id
                            val xPx = (widthPx * item.xFraction - itemSizePx / 2f).roundToInt()
                            val yPx = (heightPx * item.yFraction - itemSizePx / 2f).roundToInt()

                            Box(
                                modifier = Modifier
                                    .offset { IntOffset(xPx, yPx) }
                                    .size(baseItemDp)
                                    .clip(CircleShape)
                                    .clickable(enabled = scene.phase == ScenePhase.SEARCHING) {
                                        wrongTapMarker = null
                                        onItemTapped(item.id)
                                    }
                                    .testTag("hidden_item_${item.id}"),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isHinted) {
                                    Canvas(modifier = Modifier.fillMaxSize()) {
                                        drawCircle(
                                            color = Color(0xFFF6C85F),
                                            radius = (size.minDimension / 2f) * hintPulse,
                                            style = Stroke(width = 6f)
                                        )
                                    }
                                }
                                HiddenItemCanvas(
                                    kind = item.kind,
                                    rotationDeg = item.rotationDeg,
                                    ambientAlpha = if (scene.difficulty.id == "master") 0.88f else 0.96f,
                                    showSoftShadow = true,
                                    modifier = Modifier.size(baseItemDp * 0.85f)
                                )
                            }
                        }
                    }

                    // Wrong tap visual indicator
                    wrongTapMarker?.let { pos ->
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawCircle(
                                color = Color(0xFFE53935).copy(alpha = 0.7f),
                                radius = 26f,
                                center = pos,
                                style = Stroke(width = 5f)
                            )
                        }
                    }
                }

                // Zoom Reset Button when zoomed in
                if (zoomScale > 1.05f && scene.phase == ScenePhase.SEARCHING) {
                    FilledTonalButton(
                        onClick = {
                            zoomScale = 1f
                            panOffset = Offset.Zero
                        },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .minimumInteractiveComponentSize()
                    ) {
                        Icon(Icons.Filled.ZoomInMap, contentDescription = "Сбросить масштаб", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("1x", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }

        // Bottom Controls: Target Checklist + Hint Button
        Surface(
            color = Color(0xFF231A35),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Искать предметы (щипок — масштаб):",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFFE8DEF8)
                    )
                    Button(
                        onClick = onUseHint,
                        enabled = scene.phase == ScenePhase.SEARCHING,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD99B26),
                            contentColor = Color(0xFF221300)
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("use_hint_button")
                    ) {
                        Icon(Icons.Filled.Lightbulb, contentDescription = "Подсказка", modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Подсказка ($hintsBalance)", fontWeight = FontWeight.Bold)
                    }
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(scene.targetItems, key = { it.id }) { item ->
                        val found = item.id in scene.foundItemIds
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (found) Color(0xFF1F4E3D) else Color(0xFF33274D),
                            modifier = Modifier.border(
                                width = 1.dp,
                                color = if (item.clueInfo != null && !found) Color(0xFFF6C85F) else Color.Transparent,
                                shape = RoundedCornerShape(14.dp)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (found) {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = "Найдено",
                                        tint = Color(0xFF4ADE80),
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else {
                                    HiddenItemCanvas(
                                        kind = item.kind,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = item.name,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = if (found) Color(0xFFA7F3D0) else Color.White,
                                        textDecoration = if (found) TextDecoration.LineThrough else TextDecoration.None
                                    )
                                    if (item.clueInfo != null) {
                                        Text(
                                            text = if (found) "Улика в деле" else "Особая улика",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color(0xFFF6C85F)
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

    // Story Dialogue Overlay (Intro & Outro)
    if (scene.phase == ScenePhase.INTRO_DIALOGUE || scene.phase == ScenePhase.OUTRO_DIALOGUE) {
        val lines = if (scene.phase == ScenePhase.INTRO_DIALOGUE) scene.location.introDialogue else scene.location.outroDialogue
        val currentLine = lines.getOrNull(scene.dialogueIndex)
        if (currentLine != null) {
            DialogueOverlay(
                line = currentLine,
                stepText = "${scene.dialogueIndex + 1} / ${lines.size}",
                isIntro = scene.phase == ScenePhase.INTRO_DIALOGUE,
                onNext = {
                    if (scene.phase == ScenePhase.INTRO_DIALOGUE) onAdvanceIntro() else onAdvanceOutro()
                },
                onSkip = onSkipIntro
            )
        }
    }

    // Results Overlay
    if (scene.phase == ScenePhase.RESULTS) {
        ResultsOverlay(
            scene = scene,
            onReplay = onReplay,
            onExit = onExit
        )
    }
}

@Composable
private fun DialogueOverlay(
    line: DialogueLine,
    stepText: String,
    isIntro: Boolean,
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.68f))
            .padding(20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (line.isSliva) {
                        Image(
                            painter = painterResource(id = R.drawable.img_director_sliva),
                            contentDescription = line.speaker,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.tertiaryContainer)
                                .border(2.dp, MaterialTheme.colorScheme.tertiary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = line.speaker.take(1),
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = line.speaker,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = line.role,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Text(
                        text = stepText,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = line.text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isIntro) {
                        OutlinedButton(
                            onClick = onSkip,
                            modifier = Modifier.minimumInteractiveComponentSize()
                        ) {
                            Text("К поиску")
                        }
                    } else {
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Button(
                        onClick = onNext,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("dialogue_next_button")
                    ) {
                        Text("Далее")
                    }
                }
            }
        }
    }
}

@Composable
private fun ResultsOverlay(
    scene: ActiveSceneState,
    onReplay: () -> Unit,
    onExit: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.72f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = if (scene.isVictory) "Дело раскрыто!" else "Время истекло",
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (scene.isVictory) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )

                if (scene.isVictory) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        repeat(3) { idx ->
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (idx < scene.earnedStars) Color(0xFFF6B73C) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                    Text(
                        text = "+${scene.earnedScore} очков сыщика",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Ошибок: ${scene.mistakesCount} • Осталось времени: ${formatSeconds(scene.secondsRemaining)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Text(
                        text = "Слива Владиславовна: «Не расстраивайся! Попробуй ещё раз или используй подсказку.»",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onReplay,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text("Ещё раз")
                    }
                    Button(
                        onClick = onExit,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("results_map_button")
                    ) {
                        Text("На карту")
                    }
                }
            }
        }
    }
}

private fun formatSeconds(totalSeconds: Int): String {
    val m = totalSeconds / 60
    val s = totalSeconds % 60
    return "%d:%02d".format(m, s)
}
