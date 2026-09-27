package com.example.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.InstructionLanguage
import com.example.data.model.OfflinePack
import com.example.data.model.UserProfile
import com.example.data.model.UserRole
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    user: UserProfile,
    packs: List<OfflinePack>,
    onSelectLanguageMode: (InstructionLanguage) -> Unit,
    onToggleOromo: (Boolean) -> Unit,
    onToggleAmharic: (Boolean) -> Unit,
    onToggleSlowSpeech: (Boolean) -> Unit,
    onToggleDyslexia: (Boolean) -> Unit,
    onSelectRole: (UserRole) -> Unit,
    onTogglePack: (String, Boolean) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Settings & Accessibility", fontWeight = FontWeight.Bold)
                        Text("Qindaa'inaafi Filannoowwan (Settings)", style = MaterialTheme.typography.labelSmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        BoxWithConstraints(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .testTag("settings_screen"),
            contentAlignment = Alignment.TopCenter
        ) {
            val isNarrow = maxWidth < 360.dp
            val horizontalPadding = if (isNarrow) 10.dp else 16.dp

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp),
                contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Primary Instruction Language Selection
                item {
                    Text(
                        text = "Afaan Gargaarsaa (Instruction Language)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Choose language to guide your child in learning English from basic.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SoftDarkText
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        InstructionLanguage.values().forEach { mode ->
                            val isSelected = user.languageMode == mode
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) AmberSunLight.copy(alpha = 0.45f)
                                    else MaterialTheme.colorScheme.surface
                                ),
                                border = if (isSelected) CardDefaults.outlinedCardBorder() else null
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = mode.label,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) AmberSunDark else DarkText
                                        )
                                        if (mode == InstructionLanguage.OROMO) {
                                            Text(
                                                text = "Default & Recommended for Afaan Oromoo speakers with Qubee phonics",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = EmeraldDark
                                            )
                                        }
                                    }
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { onSelectLanguageMode(mode) }
                                    )
                                }
                            }
                        }
                    }
                }

                // User Role Switcher
                item {
                    Text("Current User Profile & Role", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        UserRole.values().forEach { role ->
                            val isSelected = user.role == role
                            FilterChip(
                                selected = isSelected,
                                onClick = { onSelectRole(role) },
                                label = {
                                    Text(
                                        when (role) {
                                            UserRole.CHILD -> "🦁 Child (Daa'ima)"
                                            UserRole.PARENT -> "👨‍👩‍👧 Parent (Maatii)"
                                            UserRole.TEACHER -> "👩‍🏫 Teacher (Barsiisaa)"
                                        }
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AmberSunDark,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                // Language & Guidance Toggles
                item {
                    Text("Bilingual Guidance & Translations", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Afaan Oromoo (Qubee) Support", fontWeight = FontWeight.Bold)
                                    Text("Show Qubee phonics, comparisons, Oromo translations, and tips.", style = MaterialTheme.typography.bodySmall, color = SoftDarkText)
                                }
                                Switch(
                                    checked = user.oromoSupportEnabled,
                                    onCheckedChange = onToggleOromo,
                                    modifier = Modifier.testTag("toggle_oromo_switch")
                                )
                            }

                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Amharic Support (አማርኛ)", fontWeight = FontWeight.Bold)
                                    Text("Show additional Amharic translations for English words and stories.", style = MaterialTheme.typography.bodySmall, color = SoftDarkText)
                                }
                                Switch(
                                    checked = user.amharicSupportEnabled,
                                    onCheckedChange = onToggleAmharic,
                                    modifier = Modifier.testTag("toggle_amharic_switch")
                                )
                            }
                        }
                    }
                }

                // Accessibility Options
                item {
                    Text("Accessibility & Audio Options", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Slow Speech Mode (0.65x)", fontWeight = FontWeight.Bold)
                                    Text("Pronounce phonemes and words at a slower tempo for clearer listening.", style = MaterialTheme.typography.bodySmall, color = SoftDarkText)
                                }
                                Switch(
                                    checked = user.slowSpeechEnabled,
                                    onCheckedChange = onToggleSlowSpeech,
                                    modifier = Modifier.testTag("toggle_slow_speech")
                                )
                            }

                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Dyslexia-Friendly Spacing", fontWeight = FontWeight.Bold)
                                    Text("Wider letter and line spacing for children with reading difficulty.", style = MaterialTheme.typography.bodySmall, color = SoftDarkText)
                                }
                                Switch(
                                    checked = user.dyslexiaFontEnabled,
                                    onCheckedChange = onToggleDyslexia,
                                    modifier = Modifier.testTag("toggle_dyslexia")
                                )
                            }
                        }
                    }
                }

                // Offline Content Packs System
                item {
                    Text("Offline Learning Packs (No Internet Required)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                }

                items(packs) { pack ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(pack.name, fontWeight = FontWeight.Bold)
                                if (pack.oromoName.isNotBlank()) {
                                    Text(pack.oromoName, style = MaterialTheme.typography.labelSmall, color = EmeraldDark, fontWeight = FontWeight.SemiBold)
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("${pack.sizeMb} MB • ${pack.itemCount} lessons and audio files", style = MaterialTheme.typography.bodySmall, color = SoftDarkText)
                            }
                            Button(
                                onClick = { onTogglePack(pack.id, !pack.isInstalled) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (pack.isInstalled) EmeraldDark else AmberSunDark
                                )
                            ) {
                                Icon(
                                    imageVector = if (pack.isInstalled) Icons.Default.Check else Icons.Default.Download,
                                    contentDescription = if (pack.isInstalled) "Installed" else "Download"
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (pack.isInstalled) "Ready" else "Download")
                            }
                        }
                    }
                }
            }
        }
    }
}
