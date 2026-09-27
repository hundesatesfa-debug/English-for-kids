package com.example.ui.screens.levels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LearningLevel
import com.example.ui.navigation.Screen
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelsMapScreen(
    levels: List<LearningLevel>,
    currentUnlockedLevel: Int,
    onBack: () -> Unit,
    onSelectLevel: (Int, Screen) -> Unit,
    showOromo: Boolean = true,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Curriculum Parts & Roadmap", fontWeight = FontWeight.Bold)
                        Text(
                            text = if (showOromo) "Qubee -> Sagalee -> Jecha -> Hima -> Qormaata Gad-fagoo" else "Letter -> Sound -> Word -> Sentence -> Deep Exam",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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
                .testTag("levels_map_screen"),
            contentAlignment = Alignment.TopCenter
        ) {
            val isNarrow = maxWidth < 360.dp
            val horizontalPadding = if (isNarrow) 10.dp else 16.dp

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp),
                contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Pedagogical Rule Banner
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = EmeraldDark.copy(alpha = 0.12f))
                    ) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🎯", fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (showOromo) "Tartiiba Barnootaa fi Qormaata" else "Pedagogical Learning Path:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = EmeraldDark
                                )
                                Text(
                                    text = if (showOromo)
                                        "Sadarkaa tokko keessatti: Qubee (Letter) -> Sagalee (Sound) -> Jecha (Word) -> Hima (Sentence) barattu. Kutaa lammaffaatti darbuuf Qormaata Gad-fagoo darbuu qabdu!"
                                    else
                                        "Each part progresses: Letter -> Sound -> Word -> Sentence. You MUST pass the Deep Exam to unlock the next part!",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                items(levels) { level ->
                    // Strict exam unlocking: Level 1 is always open, Level 2 requires Level 1 Deep Exam passed, etc.
                    val isUnlocked = level.levelNumber <= currentUnlockedLevel
                    val targetScreen = Screen.LevelJourney(level.levelNumber)

                    LevelItemCard(
                        level = level,
                        isUnlocked = isUnlocked,
                        isCurrent = level.levelNumber == currentUnlockedLevel,
                        showOromo = showOromo,
                        onClick = {
                            if (isUnlocked) onSelectLevel(level.levelNumber, targetScreen)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelItemCard(
    level: LearningLevel,
    isUnlocked: Boolean,
    isCurrent: Boolean,
    showOromo: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isUnlocked) { onClick() }
            .testTag("level_card_${level.levelNumber}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent) AmberSunLight.copy(alpha = 0.5f)
            else if (isUnlocked) MaterialTheme.colorScheme.surface
            else Color.LightGray.copy(alpha = 0.25f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isUnlocked) 2.dp else 0.dp),
        border = if (isCurrent) CardDefaults.outlinedCardBorder() else null
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Level Number Badge
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            if (isUnlocked) (if (isCurrent) AmberSunDark else EmeraldDark)
                            else Color.Gray
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isUnlocked) {
                        Text(
                            text = "${level.levelNumber}",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp
                        )
                    } else {
                        Icon(Icons.Default.Lock, contentDescription = "Locked", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Part ${level.levelNumber}: ${level.title}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isUnlocked) DarkText else SoftDarkText
                    )
                    if (showOromo && level.oromoTitle.isNotBlank()) {
                        Text(
                            text = level.oromoTitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isUnlocked) EmeraldDark else SoftDarkText,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (showOromo && level.oromoDescription.isNotBlank()) level.oromoDescription else level.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = SoftDarkText,
                        lineHeight = 16.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "XP",
                            tint = StarGold,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "+${level.xpReward}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = AmberSunDark
                        )
                    }
                    if (isCurrent) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(AmberSunDark)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "ACTIVE",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 5-Stage Step Flow Indicators on Card
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StageStepPill("1. Letter", isUnlocked)
                Text("➔", fontSize = 10.sp, color = Color.Gray)
                StageStepPill("2. Sound", isUnlocked)
                Text("➔", fontSize = 10.sp, color = Color.Gray)
                StageStepPill("3. Word", isUnlocked)
                Text("➔", fontSize = 10.sp, color = Color.Gray)
                StageStepPill("4. Sentence", isUnlocked)
                Text("➔", fontSize = 10.sp, color = Color.Gray)
                StageStepPill("5. Exam 📝", isUnlocked, isExam = true)
            }

            if (!isUnlocked) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (showOromo)
                            "🔒 Cufameera: Banuuf qormaata Kutaa ${level.levelNumber - 1} darbi!"
                        else
                            "🔒 Locked: Pass Part ${level.levelNumber - 1} Deep Exam to unlock!",
                        fontSize = 11.sp,
                        color = Color(0xFFC62828),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun StageStepPill(label: String, isUnlocked: Boolean, isExam: Boolean = false) {
    Surface(
        color = when {
            !isUnlocked -> Color.LightGray.copy(alpha = 0.3f)
            isExam -> Color(0xFFD32F2F).copy(alpha = 0.15f)
            else -> EmeraldDark.copy(alpha = 0.1f)
        },
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = label,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = when {
                !isUnlocked -> Color.Gray
                isExam -> Color(0xFFD32F2F)
                else -> EmeraldDark
            },
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
        )
    }
}
