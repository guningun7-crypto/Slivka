package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.model.CollectedClue
import com.example.data.model.GameCatalog
import com.example.data.model.UnlockedAchievement
import com.example.ui.components.HiddenItemCanvas

@Composable
fun ArchiveScreen(
    clues: List<CollectedClue>,
    achievements: Map<String, UnlockedAchievement>,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val allClueItems = remember {
        GameCatalog.locations.flatMap { loc ->
            loc.items.mapNotNull { obj ->
                obj.clueInfo?.let { Triple(loc.fairyTaleName, obj.kind, it) }
            }
        }
    }
    val collectedById = remember(clues) { clues.associateBy { it.clueId } }

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
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Чемоданчик и достижения",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                    icon = { Icon(Icons.Filled.WorkOutline, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("archive_tab_clues")
                ) {
                    Text("Улики (${clues.size}/${allClueItems.size})")
                }
                SegmentedButton(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                    icon = { Icon(Icons.Filled.EmojiEvents, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("archive_tab_achievements")
                ) {
                    Text("Награды (${achievements.size}/${GameCatalog.achievements.size})")
                }
            }

            if (selectedTab == 0) {
                if (clues.isEmpty()) {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Чемоданчик улик пока пуст! Особые предметы с золотой рамкой на локациях откроют тайные заметки Сливы Владиславовны.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(allClueItems, key = { it.third.clueId }) { (taleName, itemKind, clueInfo) ->
                        val unlocked = collectedById.containsKey(clueInfo.clueId)
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (unlocked) {
                                    MaterialTheme.colorScheme.surface
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
                                }
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = if (unlocked) 2.dp else 0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(58.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (unlocked) MaterialTheme.colorScheme.secondaryContainer
                                            else MaterialTheme.colorScheme.surfaceVariant
                                        )
                                        .border(
                                            width = 1.5.dp,
                                            color = if (unlocked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (unlocked) {
                                        HiddenItemCanvas(kind = itemKind, modifier = Modifier.size(40.dp))
                                    } else {
                                        Icon(
                                            Icons.Filled.Lock,
                                            contentDescription = "Не найдена",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (unlocked) clueInfo.title else "Неизвестная улика",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = MaterialTheme.colorScheme.primaryContainer
                                        ) {
                                            Text(
                                                text = taleName,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    if (unlocked) {
                                        Text(
                                            text = clueInfo.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "Заметка директора: ${clueInfo.slivaNote}",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    } else {
                                        Text(
                                            text = "Обыщите сказку «$taleName», чтобы найти эту улику.",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(12.dp)) }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(GameCatalog.achievements, key = { it.id }) { ach ->
                        val unlocked = achievements.containsKey(ach.id)
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (unlocked) {
                                    MaterialTheme.colorScheme.surface
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                }
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = if (unlocked) 2.dp else 0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(52.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (unlocked) MaterialTheme.colorScheme.secondaryContainer
                                            else MaterialTheme.colorScheme.surfaceVariant
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (unlocked) Icons.Filled.EmojiEvents else Icons.Filled.Lock,
                                        contentDescription = ach.title,
                                        tint = if (unlocked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = ach.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        if (unlocked) {
                                            Surface(
                                                shape = RoundedCornerShape(8.dp),
                                                color = MaterialTheme.colorScheme.tertiaryContainer
                                            ) {
                                                Text(
                                                    text = ach.badgeTitle,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        text = ach.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(12.dp)) }
                }
            }
        }
    }
}
