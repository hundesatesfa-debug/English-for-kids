package com.example.ui.screens.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
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
import com.example.ui.components.VoicePracticeSection
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingScreen(
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
    val sampleParagraphs = listOf(
        "Caaltuu is an active young girl who loves to read English books. Every morning in Bishoftu, she wakes up early, washes her face, and drinks warm milk with fresh bread. She walks with her smiling friends Boontuu and Tolasaa to school.",
        "Lake Hora Harsadi is a famous beautiful lake in Bishoftu, Oromia. Many green acacia and sycamore trees grow around the water. During Irreecha, thousands of happy people wear beautiful traditional clothes and give thanks to God by the lake.",
        "In our classroom, we learn how to read English with Qubee phonics. Our teacher teaches us how to speak with confidence. We read exciting stories together, ask smart questions, and sing English songs happily every afternoon."
    )
    val oromoParagraphs = listOf(
        "Caaltuun intala qaxalee kitaabota Ingiliffaa dubbisuu jaallattuudha. Ganama barii Bishooftuutti hirriba kaatee fuula dhiqatti; aannan ho'aafi daabboo dhandhamti. Hiriyyoota ishee Boontuufi Tolasaa wajjin kolfiteeti gara mana barumsaa deemti.",
        "Haroon Harsadii haroo miidhagduu magaalaa Bishooftuu keessatti argamtuudha. Mukkeen heddun haricha marsee biqila. Yeroo ayyaana Irreechaa, namoonni kumaatamaan uffata aadaa uffatanii haricha biratti Waaqa galateeffatu.",
        "Kutaa keenya keessatti Qubee fayyadamuudhaan akkaataa itti Afaan Ingilizii dubbifamu baranna. Barsiisaan keenya akka ofitti amannee dubbannu nu leenjisa. Seenaawwan garaagaraa walin dubbifna, sirba Ingiliffaas waliin sirbina."
    )
    val amharicParagraphs = listOf(
        "ጫልቱ የእንግሊዝኛ መጽሐፍትን ማንበብ የምትወድ ንቁ ልጅ ናት። በቢሾፍቱ ማለዳ ቀድማ ትነሳለች...",
        "የሆራ ሃርሰዲ ሐይቅ በቢሾፍቱ የሚገኝ ውብ ሐይቅ ነው። በኢሬቻ ወቅት በርካታ ህዝብ እግዚአብሔርን ያመሰግናል...",
        "በክፍላችን የእንግሊዝኛ ድምጾችን እንማራለን፤ ታሪኮችን አብረን እናነባለን..."
    )

    var currentParagraphIndex by remember { mutableStateOf(0) }
    val currentText = sampleParagraphs[currentParagraphIndex]
    val currentOromo = oromoParagraphs[currentParagraphIndex]
    val currentAmharic = amharicParagraphs[currentParagraphIndex]

    // Fluency timer states
    var isTimerRunning by remember { mutableStateOf(false) }
    var elapsedSeconds by remember { mutableStateOf(0) }
    var calculatedWpm by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            elapsedSeconds = 0
            while (isTimerRunning) {
                delay(1000)
                elapsedSeconds++
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Module 3 & 6: Reading Fluency", fontWeight = FontWeight.Bold)
                        Text(
                            text = if (showOromo) "Keeyyata Dubbisuu (Saffisaafi Qulqullina)" else "Read with Speed & Accuracy",
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
                .testTag("reading_screen"),
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
                // Paragraph Selector
                item {
                    Text(
                        text = if (showOromo) "Keeyyata Filadhaa (Choose Paragraph):" else "Choose Paragraph to Read:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("1. Caaltuu in Bishoftu", "2. Lake Hora Harsadi", "3. Our Classroom").forEachIndexed { index, title ->
                            val isSelected = currentParagraphIndex == index
                            Button(
                                onClick = {
                                    currentParagraphIndex = index
                                    isTimerRunning = false
                                    calculatedWpm = null
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) AmberSunDark else MaterialTheme.colorScheme.surface
                                ),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "Text ${index + 1}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = if (isSelected) Color.White else DarkText
                                )
                            }
                        }
                    }
                }

                // Reading Stopwatch & Fluency Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = WarmSurfaceLight),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (isTimerRunning) "⏱️ Dubbisaa Jirtu: ${elapsedSeconds}s" else "⏱️ Stopwatch: ${elapsedSeconds}s",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isTimerRunning) AmberSunDark else DarkText
                                )
                                calculatedWpm?.let { wpm ->
                                    Text(
                                        text = "Speed: $wpm Words Per Minute (WPM) 🚀",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = EmeraldDark,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    if (isTimerRunning) {
                                        isTimerRunning = false
                                        val wordCount = currentText.split("\\s+".toRegex()).size
                                        if (elapsedSeconds > 0) {
                                            calculatedWpm = ((wordCount.toFloat() / elapsedSeconds.toFloat()) * 60).toInt()
                                            onAwardXp(25)
                                            onSpeak("Great reading! Your speed was $calculatedWpm words per minute.")
                                        }
                                    } else {
                                        isTimerRunning = true
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isTimerRunning) CoralEthiopia else EmeraldDark
                                )
                            ) {
                                Icon(
                                    imageVector = if (isTimerRunning) Icons.Default.Stop else Icons.Default.PlayArrow,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isTimerRunning) "Stop" else "Start Timer")
                            }
                        }
                    }
                }

                // Main Paragraph Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "English Reading Text",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = AmberSunDark
                                )
                                IconButton(
                                    onClick = { onSpeak(currentText) },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(AmberSunDark)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Listen to passage", tint = Color.White)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = currentText,
                                style = MaterialTheme.typography.bodyLarge,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight.Normal,
                                color = DarkText
                            )

                            // Afaan Oromoo translation
                            if (showOromo && currentOromo.isNotBlank()) {
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
                                            text = currentOromo,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = DarkText,
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
                            }

                            if (showAmharic && currentAmharic.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = currentAmharic,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SoftDarkText
                                )
                            }
                        }
                    }
                }

                // Voice Recording & Evaluation for Fluency
                item {
                    VoicePracticeSection(
                        targetText = currentText,
                        isListening = isListening,
                        onStartListening = { onStartListening(currentText) },
                        onStopListening = onStopListening,
                        onListenModelAudio = { onSpeak(currentText) },
                        lastScore = lastScore
                    )
                }
            }
        }
    }
}
