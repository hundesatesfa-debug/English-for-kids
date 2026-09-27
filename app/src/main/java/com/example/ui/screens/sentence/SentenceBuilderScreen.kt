package com.example.ui.screens.sentence

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
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
import com.example.data.model.SentenceTask
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SentenceBuilderScreen(
    sentenceTasks: List<SentenceTask>,
    onSpeak: (String) -> Unit,
    onAwardXp: (Int) -> Unit,
    onBack: () -> Unit,
    showOromo: Boolean = true,
    showAmharic: Boolean = false,
    modifier: Modifier = Modifier
) {
    var taskIndex by remember { mutableStateOf(0) }
    val currentTask = sentenceTasks[taskIndex % sentenceTasks.size]

    val placedWords = remember(currentTask) { mutableStateListOf<String>() }
    val availableWords = remember(currentTask) { mutableStateListOf<String>().apply { addAll(currentTask.scrambledWords) } }

    var isChecked by remember(currentTask) { mutableStateOf(false) }
    var isCorrect by remember(currentTask) { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Module 5: Sentence Builder", fontWeight = FontWeight.Bold)
                        Text(
                            text = if (showOromo) "Hima Sirrii Ijaaruu (Sentence Construction)" else "Assemble English Sentences",
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
                .testTag("sentence_builder_screen"),
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
                // Task Progress Indicator
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (showOromo) "Hima ${taskIndex + 1} / ${sentenceTasks.size}" else "Sentence Puzzle ${taskIndex + 1} of ${sentenceTasks.size}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (showOromo) "Fakkii irraa ijaaraa" else "Build from image",
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldDark
                        )
                    }
                }

                // Image & Target Prompt Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = WarmSurfaceLight),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(currentTask.emoji, fontSize = if (isNarrow) 50.sp else 60.sp)
                            Spacer(modifier = Modifier.height(10.dp))

                            // Afaan Oromoo Target Sentence meaning
                            if (showOromo && currentTask.oromoTranslation.isNotBlank()) {
                                Text(
                                    text = "Hiika: " + currentTask.oromoTranslation,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                            if (showAmharic && currentTask.amharicTranslation.isNotBlank()) {
                                Text(
                                    text = "ትርጉም፡ " + currentTask.amharicTranslation,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = SoftDarkText
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                            Text(
                                text = if (showOromo) "Jechoota gadii tuquudhaan hima Ingiliffaa sirrii ijaaraa." else "Tap the scrambled words in correct order to form the English sentence.",
                                style = MaterialTheme.typography.bodySmall,
                                color = SoftDarkText
                            )
                        }
                    }
                }

                // Sentence Construction Drop Zone
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("sentence_drop_zone"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isChecked) {
                                if (isCorrect) EmeraldLight.copy(alpha = 0.5f) else CoralEthiopia.copy(alpha = 0.2f)
                            } else Color.White
                        ),
                        border = CardDefaults.outlinedCardBorder()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (showOromo) "Hima Keessan (Your Sentence):" else "Your Sentence:",
                                style = MaterialTheme.typography.labelSmall,
                                color = SoftDarkText
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            if (placedWords.isEmpty()) {
                                Text(
                                    text = if (showOromo) "[ Jechoota gadii tuqaa ]" else "[ Tap words below to build here ]",
                                    color = Color.Gray,
                                    fontSize = 15.sp
                                )
                            } else {
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    placedWords.forEachIndexed { index, word ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(AmberSunDark)
                                                .clickable {
                                                    placedWords.removeAt(index)
                                                    availableWords.add(word)
                                                    isChecked = false
                                                }
                                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                        ) {
                                            Text(
                                                text = word,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                        }
                                    }
                                }
                            }

                            if (placedWords.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                IconButton(
                                    onClick = { onSpeak(placedWords.joinToString(" ")) },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(AmberSunLight)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Play sentence", tint = AmberSunDark)
                                }
                            }
                        }
                    }
                }

                // Available Scrambled Words Pool
                item {
                    Text(
                        text = if (showOromo) "Jechoota Filataman (Available Words):" else "Available Words (Tap to pick):",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        availableWords.forEach { word ->
                            Button(
                                onClick = {
                                    availableWords.remove(word)
                                    placedWords.add(word)
                                    isChecked = false
                                    onSpeak(word)
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = ButtonDefaults.outlinedButtonBorder
                            ) {
                                Text(
                                    text = word,
                                    color = DarkText,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }

                // Check & Reset Action Buttons
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                availableWords.clear()
                                availableWords.addAll(currentTask.scrambledWords)
                                placedWords.clear()
                                isChecked = false
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (showOromo) "Irra Deebi'i" else "Clear")
                        }

                        Button(
                            onClick = {
                                val formedSentence = placedWords.joinToString(" ").trim()
                                val targetNormalized = currentTask.targetSentence.trim()
                                isCorrect = formedSentence.equals(targetNormalized, ignoreCase = true)
                                isChecked = true
                                if (isCorrect) {
                                    onAwardXp(25)
                                    onSpeak("Excellent! $formedSentence")
                                } else {
                                    onSpeak("Try rearranging the words!")
                                }
                            },
                            enabled = placedWords.isNotEmpty(),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldDark)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Check")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (showOromo) "Mirkaneessi" else "Check")
                        }
                    }
                }

                // Feedback and Next Button
                if (isChecked) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isCorrect) EmeraldLight else CoralEthiopia.copy(alpha = 0.2f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isCorrect) {
                                        if (showOromo) "🎉 Baay'ee Gaarii! Hima sirrii ijaarte! (+25 XP)" else "🎉 Perfect! Correct Sentence! (+25 XP)"
                                    } else {
                                        if (showOromo) "Irra deebi'ii yaali!" else "Not quite, try another order!"
                                    },
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCorrect) EmeraldDark else CoralEthiopia
                                )

                                if (isCorrect) {
                                    Button(
                                        onClick = {
                                            taskIndex++
                                            isChecked = false
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = AmberSunDark)
                                    ) {
                                        Text(if (showOromo) "Itti Aani →" else "Next →")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
