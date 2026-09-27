package com.example.ui.screens.pronunciation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EvaluationScore
import com.example.data.model.PronunciationChallenge
import com.example.ui.components.VoicePracticeSection
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PronunciationLabScreen(
    challenges: List<PronunciationChallenge>,
    selectedChallenge: PronunciationChallenge,
    onSelectChallenge: (PronunciationChallenge) -> Unit,
    onSpeak: (String) -> Unit,
    isListening: Boolean,
    lastScore: EvaluationScore?,
    onStartListening: (String) -> Unit,
    onStopListening: () -> Unit,
    onBack: () -> Unit,
    showOromo: Boolean = true,
    showAmharic: Boolean = false,
    modifier: Modifier = Modifier
) {
    var activePair by remember(selectedChallenge) {
        mutableStateOf(selectedChallenge.targetWords.first())
    }
    var targetWordToTest by remember(activePair) {
        mutableStateOf(activePair.first)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Module 10: Pronunciation Lab", fontWeight = FontWeight.Bold)
                        Text(
                            text = if (showOromo) "Laabii Sagaleessuu (P vs B, V vs W, TH)" else "Sounds for Ethiopian Learners",
                            style = MaterialTheme.typography.labelSmall
                        )
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
                .testTag("pronunciation_lab_screen"),
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
                // Sound Category Selector (P vs F, V vs B, TH, R vs L, Short vs Long Vowels)
                item {
                    Text(
                        text = if (showOromo) "Qo'annoo Sagalee Filadhaa (Focus Pair):" else "Select Focus Sound Pair:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(challenges) { challenge ->
                            val isSelected = challenge.id == selectedChallenge.id
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    onSelectChallenge(challenge)
                                    activePair = challenge.targetWords.first()
                                    targetWordToTest = activePair.first
                                },
                                label = {
                                    Text(
                                        text = if (showOromo && challenge.oromoTitle.isNotBlank()) challenge.oromoTitle else challenge.title,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = CoralEthiopia,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                // Anatomical / Mouth Position & Afaan Oromoo guidance card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = WarmSurfaceLight),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (showOromo && selectedChallenge.oromoTitle.isNotBlank()) selectedChallenge.oromoTitle else selectedChallenge.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = CoralEthiopia
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(AmberSunLight)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "Lab Guide",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = DarkText
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = selectedChallenge.contrastExplanation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = DarkText
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            // Mouth Position Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(AmberSun.copy(alpha = 0.12f))
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "👄 Mouth & Tongue Guidance (Afaaniifi Arraba):",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = AmberSunDark
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = selectedChallenge.mouthShapeGuidance,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = DarkText
                                    )
                                }
                            }

                            // Afaan Oromoo Specific Tip
                            if (showOromo && selectedChallenge.oromoTip.isNotBlank()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(EmeraldEthiopia.copy(alpha = 0.12f))
                                        .padding(12.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "🌳 Gorsa Afaan Oromoo (Qubee Phonics):",
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = EmeraldDark
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = selectedChallenge.oromoTip,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = DarkText
                                        )
                                    }
                                }
                            }

                            if (showAmharic && selectedChallenge.amharicTip.isNotBlank()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "🇪🇹 ጠቃሚ የአማርኛ ማስታወሻ፡ " + selectedChallenge.amharicTip,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SoftDarkText
                                )
                            }
                        }
                    }
                }

                // Minimal Pair Audio Comparison Box
                item {
                    Text(
                        text = if (showOromo) "Sagalee Jechootaa Walbira Qabaa (Word Sound Comparison):" else "A/B Word Sound Comparison:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Word 1 Card
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    targetWordToTest = activePair.first
                                    onSpeak(activePair.first)
                                }
                                .testTag("pair_word_1"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (targetWordToTest == activePair.first) AmberSunLight.copy(alpha = 0.5f)
                                else MaterialTheme.colorScheme.surface
                            ),
                            border = if (targetWordToTest == activePair.first) CardDefaults.outlinedCardBorder() else null
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = activePair.first,
                                    fontSize = if (isNarrow) 20.sp else 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkText
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                FilledTonalIconButton(
                                    onClick = { onSpeak(activePair.first) },
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Play word 1")
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (targetWordToTest == activePair.first) "Selected for Mic" else "Tap to choose",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (targetWordToTest == activePair.first) AmberSunDark else SoftDarkText,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Word 2 Card
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    targetWordToTest = activePair.second
                                    onSpeak(activePair.second)
                                }
                                .testTag("pair_word_2"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (targetWordToTest == activePair.second) AmberSunLight.copy(alpha = 0.5f)
                                else MaterialTheme.colorScheme.surface
                            ),
                            border = if (targetWordToTest == activePair.second) CardDefaults.outlinedCardBorder() else null
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = activePair.second,
                                    fontSize = if (isNarrow) 20.sp else 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkText
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                FilledTonalIconButton(
                                    onClick = { onSpeak(activePair.second) },
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Play word 2")
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (targetWordToTest == activePair.second) "Selected for Mic" else "Tap to choose",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (targetWordToTest == activePair.second) AmberSunDark else SoftDarkText,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Word Pair Selector
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(selectedChallenge.targetWords) { pair ->
                            val isCurrent = pair == activePair
                            OutlinedButton(
                                onClick = {
                                    activePair = pair
                                    targetWordToTest = pair.first
                                    onSpeak("${pair.first} vs ${pair.second}")
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (isCurrent) SkyBlueEthiopia.copy(alpha = 0.15f) else Color.Transparent
                                )
                            ) {
                                Text("${pair.first} - ${pair.second}", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Live Pronunciation Evaluation
                item {
                    VoicePracticeSection(
                        targetText = targetWordToTest,
                        isListening = isListening,
                        onStartListening = { onStartListening(targetWordToTest) },
                        onStopListening = onStopListening,
                        onListenModelAudio = { onSpeak(targetWordToTest) },
                        lastScore = lastScore
                    )
                }
            }
        }
    }
}
