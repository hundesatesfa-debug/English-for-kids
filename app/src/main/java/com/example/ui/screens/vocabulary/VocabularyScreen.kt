package com.example.ui.screens.vocabulary

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
import com.example.data.model.VocabularyWord
import com.example.ui.components.VoicePracticeSection
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocabularyScreen(
    categories: List<String>,
    allWords: List<VocabularyWord>,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
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
    var activeWord by remember { mutableStateOf(allWords.firstOrNull() ?: allWords.first()) }
    val filteredWords = remember(selectedCategory, allWords) {
        if (selectedCategory == "All") allWords
        else allWords.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Module 4: Vocabulary Builder", fontWeight = FontWeight.Bold)
                        Text(
                            text = if (showOromo) "Kuusaa Jechootaa (Aadaa Oromoo dabalatee)" else "16 Vocabulary Categories",
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
                .testTag("vocabulary_screen"),
            contentAlignment = Alignment.TopCenter
        ) {
            val isNarrow = maxWidth < 360.dp
            val horizontalPadding = if (isNarrow) 10.dp else 16.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp)
            ) {
                // Category filter chips
                LazyRow(
                    contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        CategoryChip(
                            name = if (showOromo) "Hunda (All)" else "All Categories",
                            isSelected = selectedCategory == "All",
                            onClick = { onSelectCategory("All") }
                        )
                    }
                    items(categories) { cat ->
                        val oromoCatName = when (cat.lowercase()) {
                            "animals" -> "Bineensota (Animals)"
                            "food" -> "Nyaata (Food)"
                            "family" -> "Maatii (Family)"
                            "ethiopian culture" -> "Aadaa (Culture)"
                            "school" -> "Mana Barumsaa (School)"
                            "body parts" -> "Qaama (Body Parts)"
                            "colors" -> "Halluu (Colors)"
                            "numbers" -> "Lakkoofsa (Numbers)"
                            "transportation" -> "Geejjiba (Transport)"
                            "nature" -> "Uumama (Nature)"
                            "weather" -> "Haala Qilleensaa (Weather)"
                            "greetings" -> "Nagaa Gaafachuu (Greetings)"
                            else -> cat
                        }
                        CategoryChip(
                            name = if (showOromo) oromoCatName else cat,
                            isSelected = selectedCategory == cat,
                            onClick = { onSelectCategory(cat) }
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = horizontalPadding),
                    contentPadding = PaddingValues(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Featured Word Spotlight / Speaking Practice
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("active_vocab_card"),
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (activeWord.isEthiopianCulture) AmberSunLight.copy(alpha = 0.5f)
                                else WarmSurfaceLight
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            border = if (activeWord.isEthiopianCulture) CardDefaults.outlinedCardBorder() else null
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (activeWord.isEthiopianCulture) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(AmberSunDark)
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = if (showOromo) "🇪🇹 Aadaa Oromoo fi Itoophiyaa" else "🇪🇹 Ethiopian Culture Spotlight",
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                                Text(activeWord.emoji, fontSize = if (isNarrow) 52.sp else 64.sp)
                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = activeWord.englishWord,
                                    fontSize = if (isNarrow) 26.sp else 32.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = DarkText
                                )

                                if (showOromo && activeWord.oromoWord.isNotBlank()) {
                                    Text(
                                        text = "Afaan Oromoo: ${activeWord.oromoWord}",
                                        fontSize = if (isNarrow) 17.sp else 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark
                                    )
                                }

                                if (showAmharic && activeWord.amharicWord.isNotBlank()) {
                                    Text(
                                        text = "አማርኛ: ${activeWord.amharicWord}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SoftDarkText
                                    )
                                }

                                Text(
                                    text = "Pronunciation: /${activeWord.phonetic}/",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = SoftDarkText
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = "\"${activeWord.exampleSentence}\"",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold,
                                            color = DarkText
                                        )
                                        if (showOromo && activeWord.oromoSentence.isNotBlank()) {
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Text(
                                                text = activeWord.oromoSentence,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = EmeraldDark
                                            )
                                        }
                                        if (showAmharic && activeWord.amharicSentence.isNotBlank()) {
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Text(
                                                text = activeWord.amharicSentence,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = SoftDarkText
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Voice Recording & Evaluation for this Word
                    item {
                        VoicePracticeSection(
                            targetText = activeWord.englishWord,
                            isListening = isListening,
                            onStartListening = { onStartListening(activeWord.englishWord) },
                            onStopListening = onStopListening,
                            onListenModelAudio = {
                                onSpeak("${activeWord.englishWord}. ${activeWord.exampleSentence}")
                            },
                            lastScore = lastScore
                        )
                    }

                    // Word List in this Category
                    item {
                        Text(
                            text = if (showOromo) "Jechoota Garee Kanaa (${filteredWords.size}):" else "Words in this category (${filteredWords.size}):",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    items(filteredWords) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    activeWord = item
                                    onSpeak(item.englishWord)
                                }
                                .testTag("vocab_item_${item.id}"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (item.id == activeWord.id) AmberSunLight.copy(alpha = 0.35f)
                                else MaterialTheme.colorScheme.surface
                            ),
                            border = if (item.id == activeWord.id) CardDefaults.outlinedCardBorder() else null
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(item.emoji, fontSize = 28.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.englishWord,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (showOromo && item.oromoWord.isNotBlank()) {
                                        Text(
                                            text = item.oromoWord,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = EmeraldDark,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    if (showAmharic && item.amharicWord.isNotBlank()) {
                                        Text(
                                            text = item.amharicWord,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = SoftDarkText
                                        )
                                    }
                                }
                                IconButton(
                                    onClick = { onSpeak(item.englishWord) },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(EmeraldEthiopia.copy(alpha = 0.2f))
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Listen", tint = EmeraldDark)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(name, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = AmberSunDark,
            selectedLabelColor = Color.White
        )
    )
}
