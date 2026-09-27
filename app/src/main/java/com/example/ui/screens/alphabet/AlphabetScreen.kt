package com.example.ui.screens.alphabet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.data.model.AlphabetLetter
import com.example.data.model.EvaluationScore
import com.example.ui.components.TracingCanvas
import com.example.ui.components.VoicePracticeSection
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlphabetScreen(
    alphabet: List<AlphabetLetter>,
    selectedLetter: AlphabetLetter,
    onSelectLetter: (AlphabetLetter) -> Unit,
    onSpeak: (String) -> Unit,
    isListening: Boolean,
    lastScore: EvaluationScore?,
    onStartListening: (String) -> Unit,
    onStopListening: () -> Unit,
    onAwardXp: (Int) -> Unit,
    onBack: () -> Unit,
    showOromo: Boolean = true,
    showAmharic: Boolean = false,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf(0) } // 0: Learn & Trace, 1: Quiz & Match

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Module 1: Alphabet & Qubee", fontWeight = FontWeight.Bold)
                        Text(
                            text = if (showOromo) "Qubee A hanga Z (Afaan Ingilizii)" else "English Alphabet Letters",
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
                .testTag("alphabet_screen"),
            contentAlignment = Alignment.TopCenter
        ) {
            val isNarrow = maxWidth < 360.dp
            val horizontalPadding = if (isNarrow) 10.dp else 16.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp)
            ) {
                // Letter selector carousel A to Z
                LazyRow(
                    contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(alphabet) { item ->
                        val isSelected = item.letter == selectedLetter.letter
                        Box(
                            modifier = Modifier
                                .size(if (isNarrow) 44.dp else 50.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) AmberSunDark else MaterialTheme.colorScheme.surface)
                                .border(
                                    width = 2.dp,
                                    color = if (isSelected) AmberSun else Color.LightGray.copy(alpha = 0.5f),
                                    shape = CircleShape
                                )
                                .clickable {
                                    onSelectLetter(item)
                                    onSpeak("${item.letter}, ${item.exampleWord}")
                                }
                                .testTag("letter_tile_${item.letter}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${item.letter}",
                                color = if (isSelected) Color.White else DarkText,
                                fontWeight = FontWeight.Bold,
                                fontSize = if (isNarrow) 18.sp else 20.sp
                            )
                        }
                    }
                }

                // Tabs: Learn & Trace vs Letter Match Game
                TabRow(
                    selectedTabIndex = activeTab,
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    Tab(
                        selected = activeTab == 0,
                        onClick = { activeTab = 0 },
                        text = {
                            Text(
                                if (showOromo) "Qubee & Trace" else "Trace & Speak",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                    Tab(
                        selected = activeTab == 1,
                        onClick = { activeTab = 1 },
                        text = {
                            Text(
                                if (showOromo) "Qubee Hir'ate (Quiz)" else "Find Missing Letter",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                }

                if (activeTab == 0) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = horizontalPadding),
                        contentPadding = PaddingValues(vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Hero Letter Card: Capital + Small + Example Word
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
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "${selectedLetter.letter} ${selectedLetter.lowercase}",
                                            fontSize = if (isNarrow) 40.sp else 48.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = AmberSunDark
                                        )
                                        Text(selectedLetter.emoji, fontSize = if (isNarrow) 44.sp else 54.sp)
                                        IconButton(
                                            onClick = {
                                                onSpeak("${selectedLetter.letter}. ${selectedLetter.exampleWord}. ${selectedLetter.exampleSentence}")
                                            },
                                            modifier = Modifier
                                                .size(44.dp)
                                                .clip(CircleShape)
                                                .background(EmeraldEthiopia)
                                                .testTag("speak_letter_button")
                                        ) {
                                            Icon(Icons.Default.VolumeUp, contentDescription = "Play sound", tint = Color.White)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = selectedLetter.exampleWord,
                                                style = MaterialTheme.typography.titleLarge,
                                                fontWeight = FontWeight.Bold,
                                                color = DarkText
                                            )
                                            if (showOromo && selectedLetter.oromoWord.isNotBlank()) {
                                                Text(
                                                    text = "Afaan Oromoo: ${selectedLetter.oromoWord}",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = EmeraldDark,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                            if (showAmharic && selectedLetter.amharicWord.isNotBlank()) {
                                                Text(
                                                    text = "አማርኛ: ${selectedLetter.amharicWord}",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = SoftDarkText
                                                )
                                            }
                                        }
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(SkyBlueEthiopia.copy(alpha = 0.15f))
                                                .padding(horizontal = 8.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text = "Sound: ${selectedLetter.phoneticSound}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = SkyBlueEthiopia,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "\"${selectedLetter.exampleSentence}\"",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = DarkText
                                    )
                                    if (showOromo && selectedLetter.oromoSentence.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = selectedLetter.oromoSentence,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = EmeraldDark
                                        )
                                    }

                                    // Qubee Phonics Comparison Tip Box
                                    if (selectedLetter.qubeeComparison.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(AmberSun.copy(alpha = 0.12f))
                                                .padding(10.dp)
                                        ) {
                                            Text(
                                                text = "💡 Qubee Phonics: ${selectedLetter.qubeeComparison}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = DarkText,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Interactive Finger Tracing Canvas
                        item {
                            Text(
                                text = if (showOromo) "Qubee '${selectedLetter.letter}' Qubaan BarreesSAA (Trace)" else "Trace Letter ${selectedLetter.letter}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            TracingCanvas(
                                targetLetter = "${selectedLetter.letter}",
                                onTracingComplete = {
                                    onAwardXp(15)
                                    onSpeak("Great job tracing letter ${selectedLetter.letter}!")
                                }
                            )
                        }

                        // Speaking Evaluation Section
                        item {
                            VoicePracticeSection(
                                targetText = selectedLetter.exampleWord,
                                isListening = isListening,
                                onStartListening = {
                                    onStartListening(selectedLetter.exampleWord)
                                },
                                onStopListening = onStopListening,
                                onListenModelAudio = {
                                    onSpeak(selectedLetter.exampleWord)
                                },
                                lastScore = lastScore
                            )
                        }
                    }
                } else {
                    // Find Missing Letter Game
                    AlphabetQuizGame(
                        currentLetter = selectedLetter,
                        showOromo = showOromo,
                        onCorrectAnswer = {
                            onAwardXp(20)
                            onSpeak("Correct! Excellent choice!")
                        },
                        onSpeak = onSpeak
                    )
                }
            }
        }
    }
}

@Composable
private fun AlphabetQuizGame(
    currentLetter: AlphabetLetter,
    showOromo: Boolean,
    onCorrectAnswer: () -> Unit,
    onSpeak: (String) -> Unit
) {
    val word = currentLetter.exampleWord
    val missingChar = word.first()
    val options = remember(currentLetter) {
        listOf(missingChar, ('A'..'Z').filter { it != missingChar }.random(), ('A'..'Z').filter { it != missingChar }.random()).shuffled()
    }
    var answeredCorrectly by remember(currentLetter) { mutableStateOf<Boolean?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(currentLetter.emoji, fontSize = 64.sp)
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = if (showOromo) "Qubee Hir'ate Filadhaa:" else "Find the missing letter for:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        if (showOromo && currentLetter.oromoWord.isNotBlank()) {
            Text(
                text = "(${currentLetter.oromoWord})",
                style = MaterialTheme.typography.bodyMedium,
                color = EmeraldDark,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "_ " + word.substring(1),
            fontSize = 38.sp,
            fontWeight = FontWeight.ExtraBold,
            color = AmberSunDark,
            letterSpacing = 4.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            options.forEach { option ->
                Button(
                    onClick = {
                        if (option == missingChar) {
                            answeredCorrectly = true
                            onCorrectAnswer()
                        } else {
                            answeredCorrectly = false
                            onSpeak("Try again! The word is $word")
                        }
                    },
                    modifier = Modifier
                        .size(64.dp)
                        .testTag("quiz_option_$option"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (answeredCorrectly == true && option == missingChar) EmeraldDark else AmberSunDark
                    )
                ) {
                    Text("$option", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        if (answeredCorrectly == true) {
            Text("🎉 Baay'ee Gaarii! Qubee sirrii filatte! (+20 XP)", color = EmeraldDark, fontWeight = FontWeight.Bold)
        } else if (answeredCorrectly == false) {
            Text("Irra deebi'ii yaali! You can do it! 💪", color = CoralEthiopia, fontWeight = FontWeight.Bold)
        }
    }
}
