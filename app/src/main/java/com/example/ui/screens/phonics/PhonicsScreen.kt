package com.example.ui.screens.phonics

import androidx.compose.foundation.background
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
import com.example.data.model.EvaluationScore
import com.example.data.model.PhonicsSound
import com.example.ui.components.VoicePracticeSection
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PhonicsScreen(
    phonicsList: List<PhonicsSound>,
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
    var selectedSound by remember { mutableStateOf(phonicsList.first()) }
    var practiceWord by remember { mutableStateOf(selectedSound.words.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Module 2: Phonics & Qubee Dachaa", fontWeight = FontWeight.Bold)
                        Text(
                            text = if (showOromo) "Sagalee Qubee Dachaa (SH, CH, TH, WH, PH)" else "Letter Sounds & Digraphs",
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
                .testTag("phonics_screen"),
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
                // Sound Cards Grid / Selector (Scrollable horizontally for any phone size)
                item {
                    Text(
                        text = if (showOromo) "Qubee Dachaa Filadhaa (Select Sound):" else "Select Sound / Digraph:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(phonicsList) { sound ->
                            val isSelected = sound.id == selectedSound.id
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (isSelected) SkyBlueEthiopia else MaterialTheme.colorScheme.surface)
                                    .clickable {
                                        selectedSound = sound
                                        practiceWord = sound.words.first()
                                        onSpeak("${sound.pattern}. Listen and repeat: $practiceWord")
                                    }
                                    .padding(horizontal = 14.dp, vertical = 10.dp)
                                    .testTag("phonics_tab_${sound.pattern}"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = sound.pattern,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = if (isSelected) Color.White else DarkText,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                // Sound Guidance Card with Afaan Oromoo comparisons
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = WarmSurfaceLight),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Sound: ${selectedSound.pattern}",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = SkyBlueEthiopia
                                    )
                                    Text(
                                        text = selectedSound.category,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = SoftDarkText
                                    )
                                }
                                IconButton(
                                    onClick = { onSpeak("${selectedSound.pattern}. ${selectedSound.pronunciationTip}") },
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(SkyBlueEthiopia)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Play sound", tint = Color.White)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "💡 How to make this sound:",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = selectedSound.pronunciationTip,
                                style = MaterialTheme.typography.bodySmall,
                                color = SoftDarkText
                            )

                            // Afaan Oromoo Qubee Dachaa Comparison Box
                            if (showOromo && selectedSound.oromoComparison.isNotBlank()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(EmeraldEthiopia.copy(alpha = 0.12f))
                                        .padding(10.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "🌳 Qubee Dachaa Afaan Oromoo:",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = EmeraldDark
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = selectedSound.oromoComparison,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = DarkText
                                        )
                                    }
                                }
                            }

                            if (showAmharic && selectedSound.amharicComparison.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "🇪🇹 የአማርኛ ንጽጽር፡ " + selectedSound.amharicComparison,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SoftDarkText
                                )
                            }
                        }
                    }
                }

                // Word practice cards for this sound (using FlowRow for screen size flexibility)
                item {
                    Text(
                        text = if (showOromo) "Jechoota Sagalee Kanaan Jalqaban (Practice Words):" else "Words with this sound:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        selectedSound.words.forEach { word ->
                            val isCurrent = word == practiceWord
                            Button(
                                onClick = {
                                    practiceWord = word
                                    onSpeak(word)
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isCurrent) AmberSunDark else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Text(
                                    text = word,
                                    color = if (isCurrent) Color.White else DarkText,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                // Sample sentences
                if (selectedSound.sampleSentences.isNotEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Sample Sentences (Himoota Fakeenyaa):",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = AmberSunDark
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                selectedSound.sampleSentences.forEach { sentence ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 2.dp)
                                            .clickable { onSpeak(sentence) },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("• ", fontWeight = FontWeight.Bold, color = EmeraldDark)
                                        Text(
                                            text = sentence,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = DarkText,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(
                                            Icons.Default.VolumeUp,
                                            contentDescription = "Speak",
                                            tint = AmberSunDark,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Speech Evaluation for selected phonics word
                item {
                    VoicePracticeSection(
                        targetText = practiceWord,
                        isListening = isListening,
                        onStartListening = { onStartListening(practiceWord) },
                        onStopListening = onStopListening,
                        onListenModelAudio = { onSpeak(practiceWord) },
                        lastScore = lastScore
                    )
                }
            }
        }
    }
}
