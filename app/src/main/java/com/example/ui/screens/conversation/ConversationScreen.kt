package com.example.ui.screens.conversation

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
import com.example.data.model.ConversationScenario
import com.example.data.model.EvaluationScore
import com.example.ui.components.VoicePracticeSection
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    scenarios: List<ConversationScenario>,
    selectedScenario: ConversationScenario,
    onSelectScenario: (ConversationScenario) -> Unit,
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
    var stepIndex by remember(selectedScenario) { mutableStateOf(0) }
    val currentStep = selectedScenario.steps[stepIndex.coerceIn(0, selectedScenario.steps.size - 1)]
    var selectedOptionIndex by remember(currentStep) { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Module 8: Conversations", fontWeight = FontWeight.Bold)
                        Text(
                            text = if (showOromo) "Waliin Dubbii (Mana Barumsaa, Gabaa & Buna)" else "Daily Conversations",
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
                .testTag("conversation_screen"),
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
                // Scenario Chooser
                item {
                    Text(
                        text = if (showOromo) "Haasaa Filadhaa (Select Scenario):" else "Select Scenario:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(scenarios) { scenario ->
                            val isSelected = scenario.id == selectedScenario.id
                            Card(
                                modifier = Modifier
                                    .width(if (isNarrow) 200.dp else 230.dp)
                                    .clickable {
                                        onSelectScenario(scenario)
                                        stepIndex = 0
                                        selectedOptionIndex = null
                                    }
                                    .testTag("scenario_card_${scenario.id}"),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) AmberSunLight.copy(alpha = 0.5f)
                                    else MaterialTheme.colorScheme.surface
                                ),
                                border = if (isSelected) CardDefaults.outlinedCardBorder() else null
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(scenario.bannerEmoji, fontSize = 30.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(scenario.title, fontWeight = FontWeight.Bold, maxLines = 1)
                                        if (showOromo && scenario.oromoTitle.isNotBlank()) {
                                            Text(
                                                text = scenario.oromoTitle,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = EmeraldDark,
                                                maxLines = 1
                                            )
                                        }
                                        Text(scenario.location, style = MaterialTheme.typography.labelSmall, color = SoftDarkText)
                                    }
                                }
                            }
                        }
                    }
                }

                // Partner Dialogue Card
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(AmberSunDark),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("👤", fontSize = 18.sp)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = currentStep.speaker,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = AmberSunDark
                                    )
                                }
                                IconButton(
                                    onClick = { onSpeak(currentStep.speechEnglish) },
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(AmberSunDark)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Listen to partner", tint = Color.White)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "\"${currentStep.speechEnglish}\"",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = DarkText
                            )

                            // Afaan Oromoo translation
                            if (showOromo && currentStep.speechOromo.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(EmeraldEthiopia.copy(alpha = 0.1f))
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = "Hiika: " + currentStep.speechOromo,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = EmeraldDark,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            if (showAmharic && currentStep.speechAmharic.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "ትርጉም፡ " + currentStep.speechAmharic,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SoftDarkText
                                )
                            }
                        }
                    }
                }

                // Child Prompt Guidance
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SkyBlueEthiopia.copy(alpha = 0.12f))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "🎯 Your Turn to Reply:",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall,
                                color = SkyBlueEthiopia
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = currentStep.childPromptEnglish,
                                style = MaterialTheme.typography.bodyMedium,
                                color = DarkText
                            )
                            if (showOromo && currentStep.childPromptOromo.isNotBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Gorsa: " + currentStep.childPromptOromo,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = EmeraldDark
                                )
                            }
                        }
                    }
                }

                // Response Options
                item {
                    Text(
                        text = if (showOromo) "Deebii Sirrii Filadhaa (Choose Response):" else "Choose Your English Response:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(currentStep.responseOptions.indices.toList()) { optIdx ->
                    val optionText = currentStep.responseOptions[optIdx]
                    val isSelected = selectedOptionIndex == optIdx
                    val isCorrect = optIdx == currentStep.correctOptionIndex

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOptionIndex = optIdx
                                if (isCorrect) {
                                    onAwardXp(20)
                                    onSpeak(optionText)
                                } else {
                                    onSpeak("Try a more polite response!")
                                }
                            }
                            .testTag("response_option_$optIdx"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) {
                                if (isCorrect) EmeraldLight else CoralEthiopia.copy(alpha = 0.2f)
                            } else MaterialTheme.colorScheme.surface
                        ),
                        border = if (isSelected) CardDefaults.outlinedCardBorder() else null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${('A' + optIdx)}. ",
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected && isCorrect) EmeraldDark else DarkText
                            )
                            Text(
                                text = optionText,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = DarkText,
                                modifier = Modifier.weight(1f)
                            )
                            if (isSelected && isCorrect) {
                                Text("✅", fontSize = 18.sp)
                            }
                        }
                    }
                }

                // Speak practice for correct response
                if (selectedOptionIndex != null && selectedOptionIndex == currentStep.correctOptionIndex) {
                    val targetResponse = currentStep.responseOptions[currentStep.correctOptionIndex]
                    item {
                        VoicePracticeSection(
                            targetText = targetResponse,
                            isListening = isListening,
                            onStartListening = { onStartListening(targetResponse) },
                            onStopListening = onStopListening,
                            onListenModelAudio = { onSpeak(targetResponse) },
                            lastScore = lastScore
                        )
                    }

                    // Next dialogue step button
                    item {
                        Button(
                            onClick = {
                                if (stepIndex < selectedScenario.steps.size - 1) {
                                    stepIndex++
                                    selectedOptionIndex = null
                                } else {
                                    onAwardXp(30)
                                    onSpeak("Congratulations! You completed this conversation scenario!")
                                    onBack()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AmberSunDark)
                        ) {
                            Text(
                                text = if (stepIndex < selectedScenario.steps.size - 1) "Next Conversation Turn →" else "Complete Conversation 🎉",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
