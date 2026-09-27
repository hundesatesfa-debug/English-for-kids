package com.example.ui.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.ui.theme.*

data class MemoryCardItem(val id: Int, val content: String, val pairId: Int, var isFlipped: Boolean = false, var isMatched: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiniGamesScreen(
    onSpeak: (String) -> Unit,
    onAwardReward: (xp: Int, stars: Int, coins: Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedGameIndex by remember { mutableStateOf(0) }
    val gameTitles = listOf(
        "Spelling Bee 🐝",
        "Memory Cards 🃏",
        "Vocabulary Quiz 🎯",
        "Word Match 🧩"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("10 Arcade Mini-Games", fontWeight = FontWeight.Bold)
                        Text("Taphaafi Shaakala Afaan Ingilizii", style = MaterialTheme.typography.labelSmall)
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
                .testTag("mini_games_screen"),
            contentAlignment = Alignment.TopCenter
        ) {
            val isNarrow = maxWidth < 360.dp
            val horizontalPadding = if (isNarrow) 10.dp else 16.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp)
            ) {
                // Game Selector Row
                LazyRow(
                    contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(gameTitles.size) { index ->
                        val isSelected = selectedGameIndex == index
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedGameIndex = index },
                            label = { Text(gameTitles[index], fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PurpleStar,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Box(modifier = Modifier.fillMaxSize().padding(horizontal = horizontalPadding)) {
                    when (selectedGameIndex) {
                        0 -> SpellingBeeGame(onSpeak, onAwardReward)
                        1 -> MemoryCardsGame(onSpeak, onAwardReward)
                        2 -> QuickVocabQuiz(onSpeak, onAwardReward)
                        3 -> WordMatchGame(onSpeak, onAwardReward)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SpellingBeeGame(
    onSpeak: (String) -> Unit,
    onAwardReward: (Int, Int, Int) -> Unit
) {
    val words = listOf("LION", "CAT", "TREE", "FISH", "DOG", "STAR")
    var wordIdx by remember { mutableStateOf(0) }
    val targetWord = words[wordIdx % words.size]

    val scrambled = remember(targetWord) { targetWord.toList().shuffled() }
    val currentGuess = remember(targetWord) { mutableStateListOf<Char>() }
    var resultMsg by remember(targetWord) { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🐝 Spelling Bee (Qubeeffama)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = AmberSunDark)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Qubeewwan wal-duraa duubaan sirreessaa:", style = MaterialTheme.typography.bodyMedium, color = SoftDarkText)

        Spacer(modifier = Modifier.height(20.dp))
        // Target slots (using FlowRow to avoid overflow on small screens)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            targetWord.indices.forEach { i ->
                val char = currentGuess.getOrNull(i)
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (char != null) AmberSunLight else Color.White)
                        .border(2.dp, AmberSunDark, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = char?.toString() ?: "",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DarkText
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        // Letter tiles
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            scrambled.forEach { char ->
                Button(
                    onClick = {
                        if (currentGuess.size < targetWord.length) {
                            currentGuess.add(char)
                            onSpeak("$char")
                            if (currentGuess.size == targetWord.length) {
                                val formed = currentGuess.joinToString("")
                                if (formed == targetWord) {
                                    resultMsg = "🎉 Baay'ee Gaarii! Qubeeffama sirrii! (+25 XP)"
                                    onAwardReward(25, 2, 5)
                                    onSpeak("Correct! $targetWord")
                                } else {
                                    resultMsg = "Irra deebi'ii yaali! Tap Clear."
                                }
                            }
                        }
                    },
                    modifier = Modifier.size(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldDark),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("$char", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))
        resultMsg?.let {
            Text(it, fontWeight = FontWeight.Bold, color = if (it.startsWith("🎉")) EmeraldDark else CoralEthiopia)
        }

        Spacer(modifier = Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { currentGuess.clear(); resultMsg = null },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Qulqulleessi (Clear)")
            }
            Button(
                onClick = { wordIdx++; resultMsg = null },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AmberSunDark)
            ) {
                Text("Itti Aani →")
            }
        }
    }
}

@Composable
private fun MemoryCardsGame(
    onSpeak: (String) -> Unit,
    onAwardReward: (Int, Int, Int) -> Unit
) {
    val cardPairs = remember {
        mutableStateListOf(
            MemoryCardItem(1, "Lion 🦁", 1),
            MemoryCardItem(2, "Leenca 🦁", 1),
            MemoryCardItem(3, "Coffee ☕", 2),
            MemoryCardItem(4, "Buna ☕", 2),
            MemoryCardItem(5, "Book 📖", 3),
            MemoryCardItem(6, "Kitaaba 📖", 3),
            MemoryCardItem(7, "Sun ☀️", 4),
            MemoryCardItem(8, "Aduu ☀️", 4)
        ).apply { shuffle() }
    }
    var flippedCards by remember { mutableStateOf(listOf<Int>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🃏 Memory Match (Tapha Yaadannoo)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("Match English and Afaan Oromoo pairs!", style = MaterialTheme.typography.bodySmall, color = SoftDarkText)
        Spacer(modifier = Modifier.height(14.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 130.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(cardPairs) { card ->
                Card(
                    modifier = Modifier
                        .height(84.dp)
                        .clickable(enabled = !card.isMatched && !card.isFlipped) {
                            card.isFlipped = true
                            flippedCards = flippedCards + card.id
                            onSpeak(card.content)

                            if (flippedCards.size == 2) {
                                val c1 = cardPairs.find { it.id == flippedCards[0] }
                                val c2 = cardPairs.find { it.id == flippedCards[1] }
                                if (c1 != null && c2 != null && c1.pairId == c2.pairId) {
                                    c1.isMatched = true
                                    c2.isMatched = true
                                    onAwardReward(20, 1, 3)
                                } else {
                                    c1?.isFlipped = false
                                    c2?.isFlipped = false
                                }
                                flippedCards = emptyList()
                            }
                        },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (card.isFlipped || card.isMatched) EmeraldLight else PurpleStar.copy(alpha = 0.25f)
                    ),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        if (card.isFlipped || card.isMatched) {
                            Text(card.content, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DarkText)
                        } else {
                            Text("❓", fontSize = 26.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickVocabQuiz(
    onSpeak: (String) -> Unit,
    onAwardReward: (Int, Int, Int) -> Unit
) {
    var questionIdx by remember { mutableStateOf(0) }
    val quizList = listOf(
        Triple("Jechi 'Buddeena / Injeeraa' Afaan Ingiliffaatiin maali?", "Injera", listOf("Injera", "Pizza", "Apple", "Rice")),
        Triple("Jechi 'Leenca' Afaan Ingilizii keessatti maali?", "Lion", listOf("Lion", "Elephant", "Monkey", "Fish")),
        Triple("Jechi 'Adurree' Afaan Ingilizii keessatti maali?", "Cat", listOf("Dog", "Cat", "Bird", "Horse")),
        Triple("Jechi 'Bishaan' Afaan Ingilizii keessatti maali?", "Water", listOf("Milk", "Tea", "Water", "Juice"))
    )
    val current = quizList[questionIdx % quizList.size]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🎯 Speed Vocabulary Quiz", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(current.first, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = EmeraldDark)
        Spacer(modifier = Modifier.height(16.dp))

        current.third.forEach { choice ->
            Button(
                onClick = {
                    if (choice == current.second) {
                        onAwardReward(20, 2, 5)
                        onSpeak("Correct! $choice")
                        questionIdx++
                    } else {
                        onSpeak("Try again!")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AmberSunDark)
            ) {
                Text(choice, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun WordMatchGame(
    onSpeak: (String) -> Unit,
    onAwardReward: (Int, Int, Int) -> Unit
) {
    var matchIdx by remember { mutableStateOf(0) }
    val matchQuestions = listOf(
        Pair("Bineensi 'Lion' (Leenca) jedhamu kami?", "🦁"),
        Pair("Bineensi 'Elephant' (Arba) jedhamu kami?", "🐘"),
        Pair("Qurxummiin 'Fish' jedhamu kami?", "🐟"),
        Pair("Qamaleen 'Monkey' jedhamtu kami?", "🐒")
    )
    val current = matchQuestions[matchIdx % matchQuestions.size]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🧩 Picture & Word Connector", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(current.first, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = AmberSunDark)
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf("🦁", "🐟", "🐒", "🐘").forEach { emoji ->
                Button(
                    onClick = {
                        if (emoji == current.second) {
                            onAwardReward(20, 1, 4)
                            onSpeak("Yes! Excellent job!")
                            matchIdx++
                        } else {
                            onSpeak("That is not correct, try again!")
                        }
                    },
                    modifier = Modifier.size(60.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Text(emoji, fontSize = 24.sp)
                }
            }
        }
    }
}
