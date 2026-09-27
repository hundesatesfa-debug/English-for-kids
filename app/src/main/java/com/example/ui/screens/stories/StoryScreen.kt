package com.example.ui.screens.stories

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
import com.example.data.model.ReadingStory
import com.example.ui.components.VoicePracticeSection
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryScreen(
    stories: List<ReadingStory>,
    selectedStory: ReadingStory,
    onSelectStory: (ReadingStory) -> Unit,
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
    var activeParagraphIndex by remember(selectedStory) { mutableStateOf(0) }
    val currentParagraph = selectedStory.paragraphs[activeParagraphIndex.coerceIn(0, selectedStory.paragraphs.size - 1)]
    var selectedAnswers by remember(selectedStory) { mutableStateOf(mutableMapOf<Int, Int>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Module 7: Story Reading", fontWeight = FontWeight.Bold)
                        Text(
                            text = if (showOromo) "Ooduu fi Seenaa (Irreecha, Finfinnee & Tales)" else "Ethiopian Children Stories",
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
                .testTag("story_screen"),
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
                // Story Chooser
                item {
                    Text(
                        text = if (showOromo) "Seenaa Filadhaa (Select Story):" else "Select Story:",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(stories) { story ->
                            val isSelected = story.id == selectedStory.id
                            Card(
                                modifier = Modifier
                                    .width(if (isNarrow) 180.dp else 220.dp)
                                    .clickable {
                                        onSelectStory(story)
                                        activeParagraphIndex = 0
                                    }
                                    .testTag("story_select_${story.id}"),
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
                                    Text(story.coverEmoji, fontSize = 32.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(story.title, fontWeight = FontWeight.Bold, maxLines = 1)
                                        if (showOromo && story.oromoTitle.isNotBlank()) {
                                            Text(
                                                text = story.oromoTitle,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = EmeraldDark,
                                                maxLines = 1
                                            )
                                        }
                                        Text(story.difficulty, style = MaterialTheme.typography.labelSmall, color = SoftDarkText)
                                    }
                                }
                            }
                        }
                    }
                }

                // Paragraph View
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = WarmSurfaceLight),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${selectedStory.title} (Part ${activeParagraphIndex + 1}/${selectedStory.paragraphs.size})",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = IndigoPlay
                                    )
                                    if (showOromo && selectedStory.oromoTitle.isNotBlank()) {
                                        Text(
                                            text = selectedStory.oromoTitle,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = EmeraldDark
                                        )
                                    }
                                }
                                IconButton(
                                    onClick = { onSpeak(currentParagraph.englishText) },
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(IndigoPlay)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Play story", tint = Color.White)
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = currentParagraph.englishText,
                                style = MaterialTheme.typography.bodyLarge,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight.Medium,
                                color = DarkText
                            )

                            // Afaan Oromoo Translation Box
                            if (showOromo && currentParagraph.oromoText.isNotBlank()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(EmeraldEthiopia.copy(alpha = 0.1f))
                                        .padding(12.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "🌳 Hiika Afaan Oromoo:",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = EmeraldDark
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = currentParagraph.oromoText,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = DarkText,
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
                            }

                            if (showAmharic && currentParagraph.amharicText.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = currentParagraph.amharicText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SoftDarkText
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            // Paragraph switcher row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        if (activeParagraphIndex > 0) activeParagraphIndex--
                                    },
                                    enabled = activeParagraphIndex > 0
                                ) {
                                    Text("Previous")
                                }
                                Button(
                                    onClick = {
                                        if (activeParagraphIndex < selectedStory.paragraphs.size - 1) {
                                            activeParagraphIndex++
                                        }
                                    },
                                    enabled = activeParagraphIndex < selectedStory.paragraphs.size - 1,
                                    colors = ButtonDefaults.buttonColors(containerColor = IndigoPlay)
                                ) {
                                    Text("Next Part")
                                }
                            }
                        }
                    }
                }

                // Voice Practice on this Paragraph
                item {
                    VoicePracticeSection(
                        targetText = currentParagraph.englishText,
                        isListening = isListening,
                        onStartListening = { onStartListening(currentParagraph.englishText) },
                        onStopListening = onStopListening,
                        onListenModelAudio = { onSpeak(currentParagraph.englishText) },
                        lastScore = lastScore
                    )
                }

                // Comprehension Quiz
                item {
                    Text(
                        text = if (showOromo) "Gaaffilee Hubannoo Seenaa (Comprehension Questions):" else "Story Comprehension Questions:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(selectedStory.questions.indices.toList()) { qIdx ->
                    val q = selectedStory.questions[qIdx]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "${qIdx + 1}. ${q.questionEnglish}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            if (showOromo && q.questionOromo.isNotBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = q.questionOromo,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = EmeraldDark,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            if (showAmharic && q.questionAmharic.isNotBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = q.questionAmharic,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SoftDarkText
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            q.options.forEachIndexed { optIdx, option ->
                                val isSelected = selectedAnswers[qIdx] == optIdx
                                val isCorrect = optIdx == q.correctIndex
                                OutlinedButton(
                                    onClick = {
                                        selectedAnswers = selectedAnswers.toMutableMap().apply {
                                            put(qIdx, optIdx)
                                        }
                                        if (isCorrect) {
                                            onAwardXp(15)
                                            onSpeak("Correct! Great comprehension!")
                                        } else {
                                            onSpeak("Try again, think about the story!")
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 3.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (isSelected) {
                                            if (isCorrect) EmeraldLight else CoralEthiopia.copy(alpha = 0.2f)
                                        } else Color.Transparent
                                    )
                                ) {
                                    Text(
                                        text = option,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = DarkText
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
