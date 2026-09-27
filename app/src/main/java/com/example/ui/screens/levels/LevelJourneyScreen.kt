package com.example.ui.screens.levels

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.EvaluationResultCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelJourneyScreen(
    levelJourney: LevelJourney,
    initialStage: Int = 0,
    currentUnlockedLevel: Int,
    onSpeak: (String) -> Unit,
    isListening: Boolean,
    lastScore: EvaluationScore?,
    onStartListening: (String, List<String>) -> Unit,
    onStopListening: () -> Unit,
    onPassExam: (Int, Int, (Boolean) -> Unit) -> Unit,
    onAwardXp: (Int) -> Unit,
    onNavigateNextLevel: (Int) -> Unit,
    onBack: () -> Unit,
    showOromo: Boolean = true,
    showAmharic: Boolean = false,
    modifier: Modifier = Modifier
) {
    var selectedStageIndex by remember { mutableIntStateOf(initialStage.coerceIn(0, 4)) }

    // Stage 1: Letter state
    var selectedLetterIndex by remember { mutableIntStateOf(0) }
    val letters = levelJourney.targetLetters
    val currentLetter = letters.getOrElse(selectedLetterIndex) { letters.first() }

    // Stage 2: Sound state
    var selectedPhonicsIndex by remember { mutableIntStateOf(0) }
    val phonicsList = levelJourney.targetPhonics
    val currentPhonics = phonicsList.getOrElse(selectedPhonicsIndex) { phonicsList.first() }

    // Stage 3: Word state
    var selectedWordIndex by remember { mutableIntStateOf(0) }
    val words = levelJourney.targetWords
    val currentWord = words.getOrElse(selectedWordIndex) { words.first() }

    // Stage 4: Sentence state
    var selectedSentenceIndex by remember { mutableIntStateOf(0) }
    val sentences = levelJourney.targetSentences
    val currentSentence = sentences.getOrElse(selectedSentenceIndex) { sentences.first() }
    var currentSentenceUserWords by remember(selectedSentenceIndex) {
        mutableStateOf<List<String>>(emptyList())
    }

    // Stage 5: Deep Exam state
    var currentExamQuestionIndex by remember { mutableIntStateOf(0) }
    var examScores by remember { mutableStateOf<MutableMap<Int, Int>>(mutableMapOf()) }
    var selectedExamOption by remember { mutableIntStateOf(-1) }
    var isExamFinished by remember { mutableStateOf(false) }
    var examTotalScore by remember { mutableIntStateOf(0) }
    var isExamPassed by remember { mutableStateOf(false) }
    var isSubmittingExam by remember { mutableStateOf(false) }

    val exam = levelJourney.deepExam
    val examQuestions = exam.questions

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = levelJourney.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (showOromo) levelJourney.oromoTitle else levelJourney.description,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("level_journey_back_button")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { paddingValues ->
        BoxWithConstraints(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .testTag("level_journey_screen"),
            contentAlignment = Alignment.TopCenter
        ) {
            val isNarrow = maxWidth < 380.dp
            val horizontalPadding = if (isNarrow) 10.dp else 16.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp)
            ) {
                // Progression Stage Stepper Bar (Letter -> Sound -> Word -> Sentence -> Deep Exam)
                StageProgressionBar(
                    selectedStageIndex = selectedStageIndex,
                    onSelectStage = { stageIndex ->
                        selectedStageIndex = stageIndex
                        if (stageIndex == 4 && isExamFinished) {
                            // Don't auto reset if finished, allow reviewing
                        }
                    },
                    showOromo = showOromo,
                    modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 8.dp)
                )

                // Stage Body
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    when (selectedStageIndex) {
                        0 -> {
                            // Stage 1: LETTER (Qubee)
                            LetterStageView(
                                letters = letters,
                                selectedLetterIndex = selectedLetterIndex,
                                currentLetter = currentLetter,
                                onSelectLetter = { selectedLetterIndex = it },
                                onSpeak = onSpeak,
                                isListening = isListening,
                                lastScore = lastScore,
                                onStartListening = { target -> onStartListening(target, listOf(target.lowercase())) },
                                onStopListening = onStopListening,
                                onContinueNextStage = {
                                    onAwardXp(15)
                                    selectedStageIndex = 1
                                },
                                showOromo = showOromo,
                                showAmharic = showAmharic,
                                horizontalPadding = horizontalPadding
                            )
                        }
                        1 -> {
                            // Stage 2: SOUND (Sagalee & Mouth Guidance)
                            SoundStageView(
                                phonicsList = phonicsList,
                                selectedPhonicsIndex = selectedPhonicsIndex,
                                currentPhonics = currentPhonics,
                                onSelectPhonics = { selectedPhonicsIndex = it },
                                onSpeak = onSpeak,
                                isListening = isListening,
                                lastScore = lastScore,
                                onStartListening = { target -> onStartListening(target, listOf(target.lowercase())) },
                                onStopListening = onStopListening,
                                onContinueNextStage = {
                                    onAwardXp(20)
                                    selectedStageIndex = 2
                                },
                                showOromo = showOromo,
                                horizontalPadding = horizontalPadding
                            )
                        }
                        2 -> {
                            // Stage 3: WORD (Jechoota)
                            WordStageView(
                                words = words,
                                selectedWordIndex = selectedWordIndex,
                                currentWord = currentWord,
                                onSelectWord = { selectedWordIndex = it },
                                onSpeak = onSpeak,
                                isListening = isListening,
                                lastScore = lastScore,
                                onStartListening = { target -> onStartListening(target, listOf(target.lowercase())) },
                                onStopListening = onStopListening,
                                onContinueNextStage = {
                                    onAwardXp(25)
                                    selectedStageIndex = 3
                                },
                                showOromo = showOromo,
                                showAmharic = showAmharic,
                                horizontalPadding = horizontalPadding
                            )
                        }
                        3 -> {
                            // Stage 4: SENTENCE (Hima)
                            SentenceStageView(
                                sentences = sentences,
                                selectedSentenceIndex = selectedSentenceIndex,
                                currentSentence = currentSentence,
                                userConstructedWords = currentSentenceUserWords,
                                onAddWord = { word ->
                                    currentSentenceUserWords = currentSentenceUserWords + word
                                },
                                onRemoveWord = { wordIndex ->
                                    currentSentenceUserWords = currentSentenceUserWords.filterIndexed { index, _ -> index != wordIndex }
                                },
                                onResetSentence = {
                                    currentSentenceUserWords = emptyList()
                                },
                                onSelectSentence = {
                                    selectedSentenceIndex = it
                                    currentSentenceUserWords = emptyList()
                                },
                                onSpeak = onSpeak,
                                isListening = isListening,
                                lastScore = lastScore,
                                onStartListening = { target, keywords -> onStartListening(target, keywords) },
                                onStopListening = onStopListening,
                                onContinueToExam = {
                                    onAwardXp(30)
                                    selectedStageIndex = 4
                                    // Reset exam state for a fresh test
                                    currentExamQuestionIndex = 0
                                    examScores = mutableMapOf()
                                    selectedExamOption = -1
                                    isExamFinished = false
                                },
                                showOromo = showOromo,
                                horizontalPadding = horizontalPadding
                            )
                        }
                        4 -> {
                            // Stage 5: DEEP EXAM (Qormaata Gad-fagoo)
                            DeepExamStageView(
                                exam = exam,
                                questions = examQuestions,
                                currentQuestionIndex = currentExamQuestionIndex,
                                selectedOption = selectedExamOption,
                                onSelectOption = { selectedExamOption = it },
                                examScores = examScores,
                                onRecordQuestionScore = { qIdx, score ->
                                    examScores = examScores.toMutableMap().apply { put(qIdx, score) }
                                },
                                onNextQuestion = {
                                    if (currentExamQuestionIndex < examQuestions.size - 1) {
                                        currentExamQuestionIndex += 1
                                        selectedExamOption = -1
                                    } else {
                                        // Calculate total score
                                        var total = 0
                                        for (i in examQuestions.indices) {
                                            total += examScores[i] ?: 0
                                        }
                                        val averageScore = if (examQuestions.isNotEmpty()) total / examQuestions.size else 0
                                        examTotalScore = averageScore
                                        isSubmittingExam = true
                                        onPassExam(levelJourney.levelNumber, averageScore) { passed ->
                                            isExamPassed = passed
                                            isExamFinished = true
                                            isSubmittingExam = false
                                        }
                                    }
                                },
                                isFinished = isExamFinished,
                                isPassed = isExamPassed,
                                totalScore = examTotalScore,
                                onRetryExam = {
                                    currentExamQuestionIndex = 0
                                    examScores = mutableMapOf()
                                    selectedExamOption = -1
                                    isExamFinished = false
                                },
                                onReviewStages = {
                                    selectedStageIndex = 0
                                },
                                onProceedToNextLevel = {
                                    onNavigateNextLevel(levelJourney.levelNumber + 1)
                                },
                                onSpeak = onSpeak,
                                isListening = isListening,
                                lastScore = lastScore,
                                onStartListening = { target, keywords -> onStartListening(target, keywords) },
                                onStopListening = onStopListening,
                                showOromo = showOromo,
                                horizontalPadding = horizontalPadding
                            )
                        }
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 1. Stage Progression Bar (Letter -> Sound -> Word -> Sentence -> Deep Exam)
// ---------------------------------------------------------------------------
@Composable
private fun StageProgressionBar(
    selectedStageIndex: Int,
    onSelectStage: (Int) -> Unit,
    showOromo: Boolean,
    modifier: Modifier = Modifier
) {
    val stages = listOf(
        LevelStage.LETTER,
        LevelStage.SOUND,
        LevelStage.WORD,
        LevelStage.SENTENCE,
        LevelStage.EXAM
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            stages.forEachIndexed { index, stage ->
                val isSelected = selectedStageIndex == index
                val isCompleted = selectedStageIndex > index

                val stageColor = when {
                    isSelected && stage == LevelStage.EXAM -> Color(0xFFD32F2F)
                    isSelected -> EmeraldDark
                    isCompleted -> AmberSunDark
                    else -> Color.Gray.copy(alpha = 0.5f)
                }

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onSelectStage(index) }
                        .testTag("stage_tab_$index"),
                    color = if (isSelected) stageColor.copy(alpha = 0.15f) else Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(stageColor),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCompleted) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = "Done",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            } else if (stage == LevelStage.EXAM) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "Exam",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            } else {
                                Text(
                                    text = "${index + 1}",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = if (showOromo) stage.oromoTitle else stage.title,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) MaterialTheme.colorScheme.onSurface else Color.Gray,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 2. Stage 1: Letter View
// ---------------------------------------------------------------------------
@Composable
private fun LetterStageView(
    letters: List<AlphabetLetter>,
    selectedLetterIndex: Int,
    currentLetter: AlphabetLetter,
    onSelectLetter: (Int) -> Unit,
    onSpeak: (String) -> Unit,
    isListening: Boolean,
    lastScore: EvaluationScore?,
    onStartListening: (String) -> Unit,
    onStopListening: () -> Unit,
    onContinueNextStage: () -> Unit,
    showOromo: Boolean,
    showAmharic: Boolean,
    horizontalPadding: androidx.compose.ui.unit.Dp
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding, vertical = 8.dp)
            .testTag("stage_letter_view"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Stage Goal Banner
            StageGoalHeader(
                stepLabel = "Step 1 of 5: Alphabet & Letter Shapes",
                oromoStepLabel = "Kutaa 1: Qubee fi Bocawwan Qubee",
                instruction = "Tap each letter, listen to its sound, and practice speaking.",
                oromoInstruction = "Qubee hunda tuquun dhaggeeffadhaa, sagalee isaaf shaakalaa.",
                showOromo = showOromo
            )
        }

        item {
            // Letter selector chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(letters) { index, letter ->
                    val isSelected = index == selectedLetterIndex
                    Card(
                        modifier = Modifier
                            .size(width = 54.dp, height = 64.dp)
                            .clickable { onSelectLetter(index) }
                            .testTag("letter_chip_${letter.letter}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) EmeraldDark else MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${letter.letter}${letter.lowercase}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = letter.emoji,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }

        item {
            // Big Letter Card with Audio & Practice
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${currentLetter.letter} ${currentLetter.lowercase}",
                        fontSize = 56.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = EmeraldDark
                    )

                    Text(
                        text = "Phonetic Sound: /${currentLetter.phoneticSound}/",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AmberSunDark
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Example Word
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(EmeraldDark.copy(alpha = 0.08f))
                            .padding(vertical = 10.dp, horizontal = 16.dp)
                    ) {
                        Text(text = currentLetter.emoji, fontSize = 32.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = currentLetter.exampleWord,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (showOromo) "Afaan Oromoo: ${currentLetter.oromoWord}" else currentLetter.exampleWord,
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Afaan Oromoo Phonics Tip
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = AmberSunLight.copy(alpha = 0.35f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.Info, contentDescription = "Tip", tint = AmberSunDark, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = currentLetter.qubeeComparison,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action Controls: Audio + Mic
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { onSpeak("${currentLetter.letter}. ${currentLetter.exampleWord}") },
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBlueEthiopia),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.testTag("listen_letter_button")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Listen")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Listen (${if (showOromo) "Dhaggeeffadhu" else "Audio"})")
                        }

                        LevelMicButton(
                            isListening = isListening,
                            onClick = {
                                if (isListening) onStopListening()
                                else onStartListening(currentLetter.exampleWord)
                            },
                            modifier = Modifier.testTag("mic_letter_button")
                        )
                    }

                    if (lastScore != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        EvaluationResultCard(score = lastScore)
                    }
                }
            }
        }

        item {
            // CTA: Continue to Sound
            Button(
                onClick = onContinueNextStage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("continue_to_sound_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldDark)
            ) {
                Text(
                    text = if (showOromo) "2. Gara Sagaleetti Ce'i (Sound) ->" else "Continue to Sound (Step 2) ->",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// 3. Stage 2: Sound View (Phonics & Physical Mouth Articulation)
// ---------------------------------------------------------------------------
@Composable
private fun SoundStageView(
    phonicsList: List<PhonicsSound>,
    selectedPhonicsIndex: Int,
    currentPhonics: PhonicsSound,
    onSelectPhonics: (Int) -> Unit,
    onSpeak: (String) -> Unit,
    isListening: Boolean,
    lastScore: EvaluationScore?,
    onStartListening: (String) -> Unit,
    onStopListening: () -> Unit,
    onContinueNextStage: () -> Unit,
    showOromo: Boolean,
    horizontalPadding: androidx.compose.ui.unit.Dp
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding, vertical = 8.dp)
            .testTag("stage_sound_view"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            StageGoalHeader(
                stepLabel = "Step 2 of 5: Letter Sounds & Mouth Positions",
                oromoStepLabel = "Kutaa 2: Sagalee Qubeefi Qophii Afaanii",
                instruction = "Master sounds difficult for Afaan Oromoo speakers with physical mouth positioning guidance.",
                oromoInstruction = "Sagalee addaa boca afaanii qabatamaan shaakaluun sirriitti baasaa.",
                showOromo = showOromo
            )
        }

        item {
            // Sound Selector
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(phonicsList) { index, phonics ->
                    val isSelected = index == selectedPhonicsIndex
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectPhonics(index) },
                        label = { Text(phonics.pattern, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldDark,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("sound_chip_$index")
                    )
                }
            }
        }

        item {
            // Phonics Card with Mouth Guide
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = currentPhonics.pattern,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = EmeraldDark
                            )
                            Text(
                                text = currentPhonics.category,
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        }

                        IconButton(
                            onClick = { onSpeak("${currentPhonics.pattern}. ${currentPhonics.words.firstOrNull() ?: ""}") },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(SkyBlueEthiopia.copy(alpha = 0.15f))
                                .testTag("play_sound_audio_button")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Play sound", tint = SkyBlueEthiopia)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Physical Mouth Articulation Guide Card (Crucial Pedagogical Requirement)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "👄", fontSize = 22.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (showOromo) "Boca Afaanii (Mouth Position Guide)" else "Mouth Articulation Guide",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6A1B9A)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = currentPhonics.pronunciationTip,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = currentPhonics.oromoComparison,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF4A148C)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Practice Words with this sound:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Words list chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        currentPhonics.words.take(4).forEach { word ->
                            AssistChip(
                                onClick = { onSpeak(word) },
                                label = { Text(word, fontWeight = FontWeight.Bold) },
                                leadingIcon = {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Microphone Practice for this sound
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LevelMicButton(
                            isListening = isListening,
                            onClick = {
                                if (isListening) onStopListening()
                                else onStartListening(currentPhonics.words.firstOrNull() ?: currentPhonics.pattern)
                            },
                            modifier = Modifier.testTag("mic_sound_button")
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (isListening) "Listening..." else "Tap mic to test your sound!",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    if (lastScore != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        EvaluationResultCard(score = lastScore)
                    }
                }
            }
        }

        item {
            Button(
                onClick = onContinueNextStage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("continue_to_word_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldDark)
            ) {
                Text(
                    text = if (showOromo) "3. Gara Jechootaatti Ce'i (Word) ->" else "Continue to Word (Step 3) ->",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// 4. Stage 3: Word View (Vocabulary, Phonetics, and Speech Scoring)
// ---------------------------------------------------------------------------
@Composable
private fun WordStageView(
    words: List<VocabularyWord>,
    selectedWordIndex: Int,
    currentWord: VocabularyWord,
    onSelectWord: (Int) -> Unit,
    onSpeak: (String) -> Unit,
    isListening: Boolean,
    lastScore: EvaluationScore?,
    onStartListening: (String) -> Unit,
    onStopListening: () -> Unit,
    onContinueNextStage: () -> Unit,
    showOromo: Boolean,
    showAmharic: Boolean,
    horizontalPadding: androidx.compose.ui.unit.Dp
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding, vertical = 8.dp)
            .testTag("stage_word_view"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            StageGoalHeader(
                stepLabel = "Step 3 of 5: Words & Blending",
                oromoStepLabel = "Kutaa 3: Jechootafi Walitti Qindeessuu",
                instruction = "Combine letters and sounds to build first words with pictures and speech feedback.",
                oromoInstruction = "Qubee fi sagalee walitti fiduun jechoota fakkii wajjin dubbisaa.",
                showOromo = showOromo
            )
        }

        item {
            // Words carousel
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(words) { index, word ->
                    val isSelected = index == selectedWordIndex
                    Card(
                        modifier = Modifier
                            .clickable { onSelectWord(index) }
                            .testTag("word_chip_$index"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) EmeraldDark else MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = word.emoji, fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = word.englishWord,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        item {
            // Word Flashcard
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = currentWord.emoji, fontSize = 64.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentWord.englishWord,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = currentWord.phonetic,
                        fontSize = 16.sp,
                        color = AmberSunDark,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Afaan Oromoo translation pill
                    Surface(
                        color = EmeraldDark.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (showOromo) "Afaan Oromoo: ${currentWord.oromoWord}" else currentWord.englishWord,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Audio & Speaking action controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { onSpeak(currentWord.englishWord) },
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBlueEthiopia),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.testTag("play_word_audio_button")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Listen")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Listen (${if (showOromo) "Dhaggeeffadhu" else "Audio"})")
                        }

                        LevelMicButton(
                            isListening = isListening,
                            onClick = {
                                if (isListening) onStopListening()
                                else onStartListening(currentWord.englishWord)
                            },
                            modifier = Modifier.testTag("mic_word_button")
                        )
                    }

                    if (lastScore != null) {
                        Spacer(modifier = Modifier.height(14.dp))
                        EvaluationResultCard(score = lastScore)
                    }
                }
            }
        }

        item {
            Button(
                onClick = onContinueNextStage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("continue_to_sentence_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldDark)
            ) {
                Text(
                    text = if (showOromo) "4. Gara Himaatti Ce'i (Sentence) ->" else "Continue to Sentence (Step 4) ->",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// 5. Stage 4: Sentence View (SVO sentence construction and spoken fluency)
// ---------------------------------------------------------------------------
@Composable
private fun SentenceStageView(
    sentences: List<SentenceTask>,
    selectedSentenceIndex: Int,
    currentSentence: SentenceTask,
    userConstructedWords: List<String>,
    onAddWord: (String) -> Unit,
    onRemoveWord: (Int) -> Unit,
    onResetSentence: () -> Unit,
    onSelectSentence: (Int) -> Unit,
    onSpeak: (String) -> Unit,
    isListening: Boolean,
    lastScore: EvaluationScore?,
    onStartListening: (String, List<String>) -> Unit,
    onStopListening: () -> Unit,
    onContinueToExam: () -> Unit,
    showOromo: Boolean,
    horizontalPadding: androidx.compose.ui.unit.Dp
) {
    val targetWords = remember(currentSentence) {
        currentSentence.targetSentence.split(" ").map { it.trim() }
    }
    val isCorrectSentence = remember(userConstructedWords, targetWords) {
        userConstructedWords.joinToString(" ") == currentSentence.targetSentence
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding, vertical = 8.dp)
            .testTag("stage_sentence_view"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            StageGoalHeader(
                stepLabel = "Step 4 of 5: Sentence Builder & Fluency",
                oromoStepLabel = "Kutaa 4: Hima Ijaaruufi Dubbii Saffisaa",
                instruction = "Build English sentences using SVO order (Subject + Verb + Object) and speak them aloud.",
                oromoInstruction = "Tartiiba himaa Ingilizii (Mata-duree + Gochima + Antoo) shaakalaa.",
                showOromo = showOromo
            )
        }

        item {
            // Sentence selector chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(sentences) { index, sentence ->
                    val isSelected = index == selectedSentenceIndex
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectSentence(index) },
                        label = { Text("Sentence ${index + 1}: ${sentence.emoji}") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldDark,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("sentence_chip_$index")
                    )
                }
            }
        }

        item {
            // SVO Grammar Scaffolding Explanation Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AmberSunLight.copy(alpha = 0.35f)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Info, contentDescription = "Grammar Tip", tint = AmberSunDark, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = if (showOromo) "Qajeelfama Hima Ijaaruu (SVO Order):" else "English SVO Word Order:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (showOromo)
                                "Afaan Oromoo keessatti gochimni dhuma irratti dhufa (SOV: 'Mucichi kubbaa qaba'). Afaan Ingilizii keessatti immoo gochimni gidduu gala (SVO: 'The boy [S] has [V] a ball [O]')."
                            else
                                "English follows Subject + Verb + Object: 'The boy (S) has (V) a ball (O)'.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        item {
            // Interactive Sentence Assembly
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Text(
                        text = if (showOromo) "Hima kana uumi (Afaan Oromoo: ${currentSentence.oromoTranslation}):" else "Build this sentence:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Construction Drop Zone
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 60.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                if (isCorrectSentence) EmeraldDark.copy(alpha = 0.15f)
                                else MaterialTheme.colorScheme.background
                            )
                            .border(
                                width = 1.dp,
                                color = if (isCorrectSentence) EmeraldDark else Color.LightGray,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .padding(10.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (userConstructedWords.isEmpty()) {
                            Text(
                                text = if (showOromo) "Jechoota armaan gadii tuquun asitti qindeessi..." else "Tap words below in order...",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        } else {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                userConstructedWords.forEachIndexed { idx, word ->
                                    Surface(
                                        color = if (isCorrectSentence) EmeraldDark else SkyBlueEthiopia,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { onRemoveWord(idx) }
                                    ) {
                                        Text(
                                            text = word,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Scrambled Available Word Tiles
                    Text(
                        text = "Available Words (Tuqi):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        currentSentence.scrambledWords.forEach { word ->
                            val isUsed = userConstructedWords.contains(word)
                            Button(
                                onClick = { if (!isUsed) onAddWord(word) },
                                enabled = !isUsed,
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = word,
                                    color = if (isUsed) Color.Gray else MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        IconButton(onClick = onResetSentence) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset sentence")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Listen & Speak the Full Sentence
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { onSpeak(currentSentence.targetSentence) },
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBlueEthiopia),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.testTag("play_sentence_audio_button")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Listen")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Hear Full Sentence")
                        }

                        LevelMicButton(
                            isListening = isListening,
                            onClick = {
                                if (isListening) onStopListening()
                                else {
                                    val keywords = currentSentence.targetSentence
                                        .replace(".", "")
                                        .lowercase()
                                        .split(" ")
                                        .filter { it.length > 2 }
                                    onStartListening(currentSentence.targetSentence, keywords)
                                }
                            },
                            modifier = Modifier.testTag("mic_sentence_button")
                        )
                    }

                    if (lastScore != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        EvaluationResultCard(score = lastScore)
                    }
                }
            }
        }

        item {
            // CTA: Go to Deep Exam!
            Button(
                onClick = onContinueToExam,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("start_deep_exam_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Icon(Icons.Default.Star, contentDescription = "Exam")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (showOromo) "5. Qormaata Gad-fagoo Fudhadhu (Deep Exam) 📝" else "Take the Deep Exam to Pass Level ->",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// 6. Stage 5: Deep Exam View (Gated Assessment to Unlock Next Level/Part)
// ---------------------------------------------------------------------------
@Composable
private fun DeepExamStageView(
    exam: DeepExam,
    questions: List<ExamQuestion>,
    currentQuestionIndex: Int,
    selectedOption: Int,
    onSelectOption: (Int) -> Unit,
    examScores: Map<Int, Int>,
    onRecordQuestionScore: (Int, Int) -> Unit,
    onNextQuestion: () -> Unit,
    isFinished: Boolean,
    isPassed: Boolean,
    totalScore: Int,
    onRetryExam: () -> Unit,
    onReviewStages: () -> Unit,
    onProceedToNextLevel: () -> Unit,
    onSpeak: (String) -> Unit,
    isListening: Boolean,
    lastScore: EvaluationScore?,
    onStartListening: (String, List<String>) -> Unit,
    onStopListening: () -> Unit,
    showOromo: Boolean,
    horizontalPadding: androidx.compose.ui.unit.Dp
) {
    if (isFinished) {
        // EXAM FINISHED SCREEN (PASS OR FAIL)
        ExamResultCard(
            exam = exam,
            isPassed = isPassed,
            totalScore = totalScore,
            onRetry = onRetryExam,
            onReviewStages = onReviewStages,
            onProceedToNextLevel = onProceedToNextLevel,
            showOromo = showOromo,
            modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 14.dp)
        )
        return
    }

    val currentQuestion = questions.getOrElse(currentQuestionIndex) { questions.first() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding, vertical = 8.dp)
            .testTag("stage_exam_view"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Exam Header Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD32F2F)),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (showOromo) exam.oromoTitle else exam.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "Pass: ${exam.passScore}%",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (showOromo)
                            "Kutaa lammaffaatti darbuuf qormaata kana 75% fi isaa ol galmeessuu qabda!"
                        else
                            "You must score 75% or higher on this deep exam to unlock the next part!",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress indicator
                    LinearProgressIndicator(
                        progress = { (currentQuestionIndex + 1).toFloat() / questions.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = AmberSunLight,
                        trackColor = Color.White.copy(alpha = 0.3f),
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Question ${currentQuestionIndex + 1} of ${questions.size} (${currentQuestion.stageCategory.name})",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 11.sp
                    )
                }
            }
        }

        item {
            // Question Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Surface(
                        color = EmeraldDark.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (showOromo) currentQuestion.stageCategory.oromoTitle else currentQuestion.stageCategory.title,
                            color = EmeraldDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = currentQuestion.questionText,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (showOromo && currentQuestion.oromoInstruction.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentQuestion.oromoInstruction,
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Question Content by Type
                    when {
                        currentQuestion.options.isNotEmpty() -> {
                            // Multiple Choice (Letters & Sounds)
                            currentQuestion.options.forEachIndexed { optIndex, optionText ->
                                val isSelected = selectedOption == optIndex
                                OutlinedCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                        .clickable {
                                            onSelectOption(optIndex)
                                            val isCorrect = optIndex == currentQuestion.correctOptionIndex
                                            onRecordQuestionScore(currentQuestionIndex, if (isCorrect) 100 else 40)
                                        }
                                        .testTag("exam_option_$optIndex"),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.outlinedCardColors(
                                        containerColor = if (isSelected) EmeraldDark.copy(alpha = 0.15f) else Color.Transparent
                                    ),
                                    border = CardDefaults.outlinedCardBorder(
                                        enabled = true
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = isSelected,
                                            onClick = {
                                                onSelectOption(optIndex)
                                                val isCorrect = optIndex == currentQuestion.correctOptionIndex
                                                onRecordQuestionScore(currentQuestionIndex, if (isCorrect) 100 else 40)
                                            }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = optionText,
                                            fontSize = 15.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    }
                                }
                            }
                        }

                        currentQuestion.spokenTarget.isNotEmpty() -> {
                            // Spoken Challenge Question
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "\"${currentQuestion.spokenTarget}\"",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SkyBlueEthiopia
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        onClick = { onSpeak(currentQuestion.spokenTarget) },
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(CircleShape)
                                            .background(SkyBlueEthiopia.copy(alpha = 0.15f))
                                    ) {
                                        Icon(Icons.Default.PlayArrow, contentDescription = "Hear target", tint = SkyBlueEthiopia)
                                    }

                                    LevelMicButton(
                                        isListening = isListening,
                                        onClick = {
                                            if (isListening) onStopListening()
                                            else onStartListening(currentQuestion.spokenTarget, currentQuestion.asrKeywords)
                                        },
                                        modifier = Modifier.testTag("exam_mic_button")
                                    )
                                }

                                if (lastScore != null) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    EvaluationResultCard(score = lastScore)
                                    LaunchedEffect(lastScore) {
                                        onRecordQuestionScore(currentQuestionIndex, lastScore.score)
                                    }
                                }
                            }
                        }
                    }

                    if (currentQuestion.tipAfaanOromoo.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "💡 Qajeelfama: ${currentQuestion.tipAfaanOromoo}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        item {
            // Next Question or Finish Button
            val isAnswered = examScores.containsKey(currentQuestionIndex) || selectedOption != -1
            Button(
                onClick = onNextQuestion,
                enabled = isAnswered,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("next_exam_question_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldDark)
            ) {
                Text(
                    text = if (currentQuestionIndex < questions.size - 1)
                        (if (showOromo) "Gaaffii Itti Aanu ->" else "Next Question ->")
                    else
                        (if (showOromo) "Qormaata Xumuri (Submit Exam) ->" else "Submit Deep Exam ->"),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// 7. Exam Result Card (Pass / Fail Celebration & Remedial Guidance)
// ---------------------------------------------------------------------------
@Composable
private fun ExamResultCard(
    exam: DeepExam,
    isPassed: Boolean,
    totalScore: Int,
    onRetry: () -> Unit,
    onReviewStages: () -> Unit,
    onProceedToNextLevel: () -> Unit,
    showOromo: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("exam_result_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon Badge
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(if (isPassed) EmeraldDark.copy(alpha = 0.15f) else Color(0xFFFFEBEE)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isPassed) "🏆" else "💪",
                    fontSize = 44.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (isPassed)
                    (if (showOromo) "BAGA GAMMADDAN! DARBITEETTA!" else "EXCELLENT! YOU PASSED!")
                else
                    (if (showOromo) "ITTUMA FUFI! AMMAS YAALI!" else "ALMOST THERE! RETRY TO PASS!"),
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (isPassed) EmeraldDark else Color(0xFFC62828),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Score: $totalScore% (Pass: ${exam.passScore}%)",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Explanation & Result message
            Surface(
                color = if (isPassed) EmeraldDark.copy(alpha = 0.08f) else Color(0xFFFFF3E0),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (isPassed) {
                            if (showOromo)
                                "Qormaata Gad-fagoo milkiin xumurtanii jirtu! Kutaan 2ffaan (Sadarkaa itti aanu) amma banameera. Qubee, Sagalee, Jechaafi Hima haaraa baradhaa!"
                            else
                                "You mastered this level! The next part/level is now UNLOCKED. Continue your English journey!"
                        } else {
                            if (showOromo)
                                "Sadarkaa itti aanu banuuf qabxii 75% barbaachisa. Qubee, Sagalee, ykn Hima irra deebi'ii shaakaliitii ammas qormaata kana yaali!"
                            else
                                "You need 75% to unlock the next level. Review the Letter, Sound, Word, and Sentence stages, then retry!"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (isPassed) {
                // Button to proceed to next level / part
                Button(
                    onClick = onProceedToNextLevel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("proceed_next_level_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldDark)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (showOromo) "Kutaa Itti Aanutti Ce'i (Next Part) ->" else "Proceed to Next Level ->",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            } else {
                // Retry and Review Buttons
                Button(
                    onClick = onRetry,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("retry_exam_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (showOromo) "Ammas Qormaata Yaali (Retry Exam)" else "Retry Deep Exam",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = onReviewStages,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("review_stages_button"),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Menu, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (showOromo) "Qubee fi Sagalee Irra Deebi'i (Review)" else "Review Letter, Sound, Word",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Helper: Stage Header Banner
// ---------------------------------------------------------------------------
@Composable
private fun StageGoalHeader(
    stepLabel: String,
    oromoStepLabel: String,
    instruction: String,
    oromoInstruction: String,
    showOromo: Boolean
) {
    Surface(
        color = EmeraldDark.copy(alpha = 0.08f),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = if (showOromo) oromoStepLabel else stepLabel,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = EmeraldDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = if (showOromo) oromoInstruction else instruction,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LevelMicButton(
    isListening: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mic_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Box(
        modifier = modifier
            .scale(if (isListening) pulseScale else 1f)
            .size(54.dp)
            .clip(CircleShape)
            .background(if (isListening) CoralEthiopia else EmeraldDark)
            .clickable { onClick() }
            .testTag("record_speech_button"),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isListening) Icons.Default.Stop else Icons.Default.Mic,
            contentDescription = if (isListening) "Stop" else "Speak",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
    }
}

