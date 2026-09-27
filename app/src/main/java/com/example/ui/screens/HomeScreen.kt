package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.components.AbebeMascotCard
import com.example.ui.components.TopStatsBar
import com.example.ui.navigation.Screen
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    user: UserProfile,
    onNavigate: (Screen) -> Unit,
    onSpeakTeacher: (String) -> Unit,
    onRoleClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        val isNarrow = maxWidth < 360.dp
        val horizontalPadding = if (isNarrow) 10.dp else 16.dp

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp)
                .testTag("home_screen"),
            contentPadding = PaddingValues(bottom = 36.dp)
        ) {
            item {
                TopStatsBar(
                    user = user,
                    onRoleClick = onRoleClick,
                    onSettingsClick = onSettingsClick
                )
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
                // Mascot Greeting
                AbebeMascotCard(
                    englishMessage = "Hello ${user.name}! Ready for today's English adventure?",
                    oromoMessage = "Akkam ${user.name}! Nageenyi kee haa baay'atu? Barnoota Ingiliffaa har'aa jalqabnaa?",
                    amharicMessage = "ሰላም ${user.name}! ለዛሬው የእንግሊዝኛ ጀብዱ ተዘጋጅተሃል?",
                    showOromo = user.oromoSupportEnabled,
                    showAmharic = user.amharicSupportEnabled,
                    onSpeakClick = {
                        onSpeakTeacher("Hello ${user.name}! Welcome to Speak English Kids. Let us learn together!")
                    },
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                )
            }

            // Daily Learning Goal Progress Card
            item {
                Spacer(modifier = Modifier.height(12.dp))
                DailyGoalCard(
                    minutesSpent = user.todayMinutesLearned,
                    goalMinutes = user.dailyGoalMinutes,
                    isOromo = user.oromoSupportEnabled,
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                )
            }

            // Main CTA: Continue Learning Adventure (Big Level Card)
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding)
                        .clickable { onNavigate(Screen.LevelJourney(user.currentLevel)) }
                        .testTag("resume_learning_card"),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = AmberSunDark),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(AmberSunDark, EmeraldDark)
                                )
                            )
                            .padding(if (isNarrow) 14.dp else 18.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "KUTAA ${user.currentLevel} • PART ${user.currentLevel}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = AmberSunLight
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Start Part ${user.currentLevel} Adventure 🚀",
                                        style = if (isNarrow) MaterialTheme.typography.titleMedium else MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = "Letter ➔ Sound ➔ Word ➔ Sentence ➔ Deep Exam",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.White.copy(alpha = 0.95f),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .size(if (isNarrow) 42.dp else 50.dp)
                                        .clip(CircleShape)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Start",
                                        tint = EmeraldDark,
                                        modifier = Modifier.size(if (isNarrow) 26.dp else 32.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = Color.White.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Qormaata Gad-fagoon Kutaa 2ffaa bani 📝",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }

                                TextButton(
                                    onClick = { onNavigate(Screen.LevelsMap) },
                                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                                ) {
                                    Text(
                                        text = "Roadmap 🗺️",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Featured Daily Practice Modules
            item {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = if (user.oromoSupportEnabled) "Barnoota Har'aa (Today's Lessons)" else "Today's Learning Modules",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = horizontalPadding),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        ModuleQuickCard(
                            title = "Qubee & Trace",
                            subtitle = "Qubee A hanga Z",
                            emoji = "🔤",
                            color = AmberSunDark,
                            onClick = { onNavigate(Screen.Alphabet) },
                            testTag = "quick_alphabet_button"
                        )
                    }
                    item {
                        ModuleQuickCard(
                            title = "Phonics Sounds",
                            subtitle = "Qubee Dachaa (SH, CH, TH)",
                            emoji = "🗣️",
                            color = SkyBlueEthiopia,
                            onClick = { onNavigate(Screen.Phonics) },
                            testTag = "quick_phonics_button"
                        )
                    }
                    item {
                        ModuleQuickCard(
                            title = "Vocabulary Safari",
                            subtitle = "Jechoota Aadaafi Jireenyaa",
                            emoji = "🦁",
                            color = EmeraldDark,
                            onClick = { onNavigate(Screen.Vocabulary) },
                            testTag = "quick_vocab_button"
                        )
                    }
                    item {
                        ModuleQuickCard(
                            title = "Pronunciation Lab",
                            subtitle = "P vs B, V vs W, TH",
                            emoji = "🎙️",
                            color = CoralEthiopia,
                            onClick = { onNavigate(Screen.PronunciationLab) },
                            testTag = "quick_pronunciation_button"
                        )
                    }
                }
            }

            // Complete Skill Pathways Grid
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = if (user.oromoSupportEnabled) "Shaakalaafi Tapha (Practice & Games)" else "Skill Centers & Activities",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ActivityCard(
                            title = "Story Reading",
                            desc = "Irreecha, Finfinne & Tales",
                            emoji = "📖",
                            color = IndigoPlay,
                            onClick = { onNavigate(Screen.Stories) },
                            modifier = Modifier.weight(1f)
                        )
                        ActivityCard(
                            title = "Conversations",
                            desc = "School, Gabaa & Buna",
                            emoji = "💬",
                            color = AmberSunDark,
                            onClick = { onNavigate(Screen.Conversations) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ActivityCard(
                            title = "Sentence Builder",
                            desc = "Hima Sirrii Ijaaruu",
                            emoji = "🧩",
                            color = EmeraldDark,
                            onClick = { onNavigate(Screen.SentenceBuilder) },
                            modifier = Modifier.weight(1f)
                        )
                        ActivityCard(
                            title = "10 Mini-Games",
                            desc = "Spelling Bee, Memory Cards",
                            emoji = "🎮",
                            color = CoralEthiopia,
                            onClick = { onNavigate(Screen.MiniGames) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ActivityCard(
                            title = "Reading Fluency",
                            desc = "Saffisaafi Qulqullina (WPM)",
                            emoji = "⚡",
                            color = AmberSunDark,
                            onClick = { onNavigate(Screen.ReadingFluency) },
                            modifier = Modifier.weight(1f)
                        )
                        ActivityCard(
                            title = "Parent & Teacher",
                            desc = "Hordoffii Maatiifi Barsiisaa",
                            emoji = "📊",
                            color = SoftDarkText,
                            onClick = { onNavigate(Screen.ParentDashboard) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyGoalCard(
    minutesSpent: Int,
    goalMinutes: Int,
    isOromo: Boolean,
    modifier: Modifier = Modifier
) {
    val progress = (minutesSpent.toFloat() / goalMinutes.toFloat()).coerceIn(0f, 1f)
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isOromo) "Kaayyoo Shaakala Guyyaa" else "Daily Practice Goal",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$minutesSpent / $goalMinutes daqiiqaa",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = EmeraldEthiopia,
                trackColor = EmeraldLight.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
private fun ModuleQuickCard(
    title: String,
    subtitle: String,
    emoji: String,
    color: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(136.dp)
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f)),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Text(emoji, fontSize = 22.sp)
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = SoftDarkText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun ActivityCard(
    title: String,
    desc: String,
    emoji: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(90.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(emoji, fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.labelSmall,
                    color = SoftDarkText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 10.sp
                )
            }
        }
    }
}
