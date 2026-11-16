package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.DetectiveProfile
import com.example.data.model.GameCatalog
import com.example.ui.components.AvatarPalette
import com.example.ui.components.DetectiveAvatarCanvas

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharacterStudioScreen(
    isOnboarding: Boolean,
    isGuestMode: Boolean = false,
    initialProfile: DetectiveProfile?,
    onSave: (
        name: String,
        gender: String,
        skinToneIndex: Int,
        hairStyleIndex: Int,
        hairColorIndex: Int,
        hatId: String,
        coatId: String,
        accessoryId: String
    ) -> Unit,
    onSignOut: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var name by remember(initialProfile) { mutableStateOf(initialProfile?.name ?: "") }
    var gender by remember(initialProfile) { mutableStateOf(initialProfile?.gender ?: "girl") }
    var skinToneIndex by remember(initialProfile) { mutableIntStateOf((initialProfile?.skinToneIndex ?: 1L).toInt()) }
    var hairStyleIndex by remember(initialProfile) { mutableIntStateOf((initialProfile?.hairStyleIndex ?: 0L).toInt()) }
    var hairColorIndex by remember(initialProfile) { mutableIntStateOf((initialProfile?.hairColorIndex ?: 1L).toInt()) }
    var hatId by remember(initialProfile) { mutableStateOf(initialProfile?.hatId ?: "cap") }
    var coatId by remember(initialProfile) { mutableStateOf(initialProfile?.coatId ?: "plum") }
    var accessoryId by remember(initialProfile) { mutableStateOf(initialProfile?.accessoryId ?: "loupe") }
    var nameError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .then(if (isOnboarding) Modifier.safeDrawingPadding() else Modifier),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isOnboarding) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_director_sliva),
                            contentDescription = "Слива Владиславовна",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Кошка Слива Владиславовна",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "«Мур-р! Заполни удостоверение юного детектива, выбери облик — и отправляйся в Сказочную страну!»",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            } else if (initialProfile != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Ателье детектива",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Счёт: ${initialProfile.totalScore} • Звёзд: ${initialProfile.totalStars}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (isGuestMode) {
                            Text(
                                text = "Режим без регистрации (прогресс на устройстве)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    if (onSignOut != null) {
                        OutlinedButton(
                            onClick = onSignOut,
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("sign_out_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout,
                                contentDescription = if (isGuestMode) "Войти в аккаунт" else "Выйти из аккаунта",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isGuestMode) "Google-вход" else "Выйти")
                        }
                    }
                }
            }

            // Live Avatar Preview Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DetectiveAvatarCanvas(
                        gender = gender,
                        skinToneIndex = skinToneIndex,
                        hairStyleIndex = hairStyleIndex,
                        hairColorIndex = hairColorIndex,
                        hatId = hatId,
                        coatId = coatId,
                        accessoryId = accessoryId,
                        modifier = Modifier.size(152.dp)
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            if (it.length <= 30) {
                                name = it
                                nameError = false
                            }
                        },
                        label = { Text("Имя юного детектива") },
                        placeholder = { Text("Например: Варя или Лёва") },
                        isError = nameError,
                        supportingText = if (nameError) {
                            { Text("Пожалуйста, введите имя сыщика") }
                        } else null,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("detective_name_input")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        GenderChip(
                            label = "Юная сыщица",
                            selected = gender == "girl",
                            onClick = { gender = "girl" },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("gender_girl_chip")
                        )
                        GenderChip(
                            label = "Юный сыщик",
                            selected = gender == "boy",
                            onClick = { gender = "boy" },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("gender_boy_chip")
                        )
                    }
                }
            }

            // Appearance: Skin & Hair
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Filled.Face, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Text("Внешность", style = MaterialTheme.typography.titleMedium)
                    }

                    Text("Тон кожи", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        AvatarPalette.skinTones.forEachIndexed { idx, color ->
                            ColorSwatchCircle(
                                color = color,
                                selected = skinToneIndex == idx,
                                contentDescription = "Тон кожи ${idx + 1}",
                                onClick = { skinToneIndex = idx }
                            )
                        }
                    }

                    Text("Цвет волос", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        AvatarPalette.hairColors.forEachIndexed { idx, color ->
                            ColorSwatchCircle(
                                color = color,
                                selected = hairColorIndex == idx,
                                contentDescription = "Цвет волос ${idx + 1}",
                                onClick = { hairColorIndex = idx }
                            )
                        }
                    }

                    Text("Причёска", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Классика", "Пробор", "Кудри", "Чёлка").forEachIndexed { idx, title ->
                            FilterChip(
                                selected = hairStyleIndex == idx,
                                onClick = { hairStyleIndex = idx },
                                label = { Text(title) },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            )
                        }
                    }
                }
            }

            // Wardrobe: Hat, Coat, Accessory
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                        Text("Детективный гардероб", style = MaterialTheme.typography.titleMedium)
                    }

                    Text("Головной убор", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        GameCatalog.hats.forEach { opt ->
                            FilterChip(
                                selected = hatId == opt.id,
                                onClick = { hatId = opt.id },
                                label = { Text(opt.title) },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            )
                        }
                    }

                    Text("Плащ или мундир", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        GameCatalog.coats.forEach { opt ->
                            FilterChip(
                                selected = coatId == opt.id,
                                onClick = { coatId = opt.id },
                                label = { Text(opt.title) },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            )
                        }
                    }

                    Text("Снаряжение сыщика", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        GameCatalog.accessories.forEach { opt ->
                            FilterChip(
                                selected = accessoryId == opt.id,
                                onClick = { accessoryId = opt.id },
                                label = { Text(opt.title) },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (name.trim().isEmpty()) {
                        nameError = true
                    } else {
                        onSave(
                            name.trim(),
                            gender,
                            skinToneIndex,
                            hairStyleIndex,
                            hairColorIndex,
                            hatId,
                            coatId,
                            accessoryId
                        )
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("save_detective_button")
            ) {
                Icon(Icons.Filled.Star, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isOnboarding) "Поступить в школу сыска" else "Сохранить образ",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun GenderChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        },
        leadingIcon = if (selected) {
            { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
        } else null,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = modifier.minimumInteractiveComponentSize()
    )
}

@Composable
private fun ColorSwatchCircle(
    color: androidx.compose.ui.graphics.Color,
    selected: Boolean,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(4.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (selected) 3.dp else 1.5.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
