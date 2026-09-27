package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.data.model.EvaluationScore
import com.example.data.model.UserProfile
import com.example.ui.theme.*

@Composable
fun TopStatsBar(
    user: UserProfile,
    onRoleClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("top_stats_bar"),
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            val isSmallScreen = maxWidth < 360.dp
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // User Avatar & Name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onRoleClick() }
                        .padding(2.dp)
                        .testTag("profile_role_button")
                ) {
                    Box(
                        modifier = Modifier
                            .size(if (isSmallScreen) 34.dp else 40.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(AmberSun, EmeraldEthiopia))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (user.role.name == "CHILD") "🦁" else if (user.role.name == "PARENT") "👨‍👩‍👧" else "👩‍🏫",
                            fontSize = if (isSmallScreen) 16.sp else 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = user.name,
                            style = if (isSmallScreen) MaterialTheme.typography.bodySmall else MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (user.oromoSupportEnabled) "Afaan Oromoo" else user.role.name,
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldDark,
                            fontSize = 10.sp
                        )
                    }
                }

                // Gamification Badges: Streak, Stars, Level, Settings
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(if (isSmallScreen) 4.dp else 8.dp)
                ) {
                    // Streak
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(CoralEthiopia.copy(alpha = 0.15f))
                            .padding(horizontal = if (isSmallScreen) 5.dp else 8.dp, vertical = 4.dp)
                    ) {
                        Text("🔥", fontSize = if (isSmallScreen) 11.sp else 13.sp)
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${user.streakDays}d",
                            fontWeight = FontWeight.Bold,
                            fontSize = if (isSmallScreen) 11.sp else 12.sp,
                            color = CoralEthiopia
                        )
                    }

                    // Stars
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(StarGold.copy(alpha = 0.2f))
                            .padding(horizontal = if (isSmallScreen) 5.dp else 8.dp, vertical = 4.dp)
                    ) {
                        Text("⭐", fontSize = if (isSmallScreen) 11.sp else 13.sp)
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${user.stars}",
                            fontWeight = FontWeight.Bold,
                            fontSize = if (isSmallScreen) 11.sp else 12.sp,
                            color = AmberSunDark
                        )
                    }

                    // Level
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(EmeraldEthiopia.copy(alpha = 0.15f))
                            .padding(horizontal = if (isSmallScreen) 5.dp else 8.dp, vertical = 4.dp)
                    ) {
                        Text("🏆", fontSize = if (isSmallScreen) 11.sp else 13.sp)
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "L${user.currentLevel}",
                            fontWeight = FontWeight.Bold,
                            fontSize = if (isSmallScreen) 11.sp else 12.sp,
                            color = EmeraldDark
                        )
                    }

                    IconButton(
                        onClick = onSettingsClick,
                        modifier = Modifier
                            .size(if (isSmallScreen) 32.dp else 36.dp)
                            .testTag("settings_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = SoftDarkText,
                            modifier = Modifier.size(if (isSmallScreen) 18.dp else 22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AbebeMascotCard(
    englishMessage: String,
    oromoMessage: String = "",
    amharicMessage: String = "",
    showOromo: Boolean = true,
    showAmharic: Boolean = false,
    onSpeakClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("abebe_mascot_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = AmberSunLight.copy(alpha = 0.45f)
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, AmberSun, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("🦁", fontSize = 32.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (showOromo) "Barsiisaa Caalaa 🦁 (Teacher Leo)" else "Teacher Abebe (አበበ)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = AmberSunDark
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = englishMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )
                if (showOromo && oromoMessage.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = oromoMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = EmeraldDark,
                        fontWeight = FontWeight.Medium
                    )
                }
                if (showAmharic && amharicMessage.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = amharicMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = SoftDarkText
                    )
                }
            }
            IconButton(
                onClick = onSpeakClick,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(EmeraldEthiopia)
                    .testTag("mascot_audio_button")
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Listen to teacher",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun VoicePracticeSection(
    targetText: String,
    isListening: Boolean,
    onStartListening: () -> Unit,
    onStopListening: () -> Unit,
    onListenModelAudio: () -> Unit,
    lastScore: EvaluationScore?,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mic_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("voice_practice_section"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Voice Pronunciation Practice",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Listen carefully, then speak into the microphone!",
                style = MaterialTheme.typography.bodySmall,
                color = SoftDarkText
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Listen to native model pronunciation
                OutlinedButton(
                    onClick = onListenModelAudio,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = SkyBlueEthiopia),
                    modifier = Modifier.testTag("listen_target_audio_button")
                ) {
                    Icon(Icons.Default.VolumeUp, contentDescription = "Play model voice")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Listen (አዳምጥ)", fontWeight = FontWeight.Bold)
                }

                // Child Voice recording button
                Box(
                    modifier = Modifier
                        .scale(if (isListening) pulseScale else 1f)
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(if (isListening) CoralEthiopia else EmeraldEthiopia)
                        .clickable {
                            if (isListening) onStopListening() else onStartListening()
                        }
                        .testTag("record_speech_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isListening) Icons.Default.Stop else Icons.Default.Mic,
                        contentDescription = if (isListening) "Stop listening" else "Start speaking",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isListening) "🎙️ Listening... Speak now! (እየሰማን ነው)" else "Tap microphone to speak (ይናገሩ)",
                style = MaterialTheme.typography.labelMedium,
                color = if (isListening) CoralEthiopia else SoftDarkText,
                fontWeight = if (isListening) FontWeight.Bold else FontWeight.Normal
            )

            // Live Evaluation Score Display
            if (lastScore != null) {
                Spacer(modifier = Modifier.height(14.dp))
                EvaluationResultCard(score = lastScore)
            }
        }
    }
}

@Composable
fun EvaluationResultCard(
    score: EvaluationScore,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("evaluation_result_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (score.score >= 80) EmeraldLight.copy(alpha = 0.4f)
            else AmberSunLight.copy(alpha = 0.4f)
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = score.feedbackLabel,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (score.score >= 80) EmeraldDark else AmberSunDark)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${score.score}%",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ScoreMetric("Pronunciation", "${score.pronunciationAccuracy}%")
                ScoreMetric("Words", "${score.wordAccuracy}%")
                ScoreMetric("Fluency", "${score.fluencyScore}%")
                ScoreMetric("Confidence", "${score.confidenceScore}%")
            }
        }
    }
}

@Composable
private fun ScoreMetric(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = SoftDarkText
        )
    }
}
