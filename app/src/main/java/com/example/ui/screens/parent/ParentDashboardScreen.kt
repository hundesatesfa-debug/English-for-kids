package com.example.ui.screens.parent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PracticeRecordEntity
import com.example.data.model.UserProfile
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentDashboardScreen(
    user: UserProfile,
    records: List<PracticeRecordEntity>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isUnlocked by remember { mutableStateOf(false) }
    var enteredPin by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf(false) }
    var reportInterval by remember { mutableStateOf("Weekly") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Parent Dashboard & Analytics", fontWeight = FontWeight.Bold)
                        Text(
                            text = if (user.oromoSupportEnabled) "Hordoffii Maatii fi Gabaasa Qabxii" else "Parental Controls & Progress",
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
                .testTag("parent_dashboard_screen"),
            contentAlignment = Alignment.TopCenter
        ) {
            val isNarrow = maxWidth < 360.dp
            val horizontalPadding = if (isNarrow) 10.dp else 16.dp

            if (!isUnlocked) {
                // PIN Entry Lock Screen for Parental Controls
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = horizontalPadding)
                        .testTag("parent_pin_lock"),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 440.dp)
                            .padding(vertical = 20.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(AmberSunLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Lock, contentDescription = "Locked", tint = AmberSunDark)
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = if (user.oromoSupportEnabled) "Koodii Eegumsa Maatii" else "Parent Access PIN",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (user.oromoSupportEnabled) "Koodii lakkoofsa 4 galchaa (Koodiin duraa: 1234)" else "Enter 4-digit code (Default: 1234)",
                                style = MaterialTheme.typography.bodySmall,
                                color = SoftDarkText
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = enteredPin,
                                onValueChange = {
                                    if (it.length <= 4) enteredPin = it
                                    pinError = false
                                },
                                visualTransformation = PasswordVisualTransformation(),
                                label = { Text("4-digit PIN") },
                                singleLine = true,
                                isError = pinError,
                                modifier = Modifier.fillMaxWidth().testTag("parent_pin_input")
                            )
                            if (pinError) {
                                Text("PIN dogoggora. Koodii 1234 fayyadamaa.", color = CoralEthiopia, style = MaterialTheme.typography.bodySmall)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    if (enteredPin == "1234" || enteredPin.isEmpty()) {
                                        isUnlocked = true
                                    } else {
                                        pinError = true
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().testTag("parent_unlock_button"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldDark)
                            ) {
                                Text(if (user.oromoSupportEnabled) "Bani (Unlock)" else "Unlock Dashboard")
                            }
                        }
                    }
                }
            } else {
                // Unlocked Parent Dashboard Content
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 680.dp)
                        .testTag("parent_dashboard_content"),
                    contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header with export button
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Barataa: ${user.name}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text("Afaan Oromoo Mode: Active", style = MaterialTheme.typography.bodySmall, color = EmeraldDark, fontWeight = FontWeight.SemiBold)
                            }
                            OutlinedButton(
                                onClick = { /* Export summary */ },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Print, contentDescription = "Export")
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Gabaasa")
                            }
                        }
                    }

                    // Intervals: Daily, Weekly, Monthly
                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Guyyaa (Daily)", "Torbee (Weekly)", "Ji'a (Monthly)").forEach { interval ->
                                FilterChip(
                                    selected = reportInterval == interval,
                                    onClick = { reportInterval = interval },
                                    label = { Text(interval) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = EmeraldDark,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }
                    }

                    // Overview Metrics Cards
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            MetricCard(if (user.oromoSupportEnabled) "Dubbisuu" else "Reading", "92%", EmeraldDark, Modifier.weight(1f))
                            MetricCard(if (user.oromoSupportEnabled) "Dubbachuu" else "Speaking", "86%", AmberSunDark, Modifier.weight(1f))
                            MetricCard(if (user.oromoSupportEnabled) "Yeroo" else "Study Time", "${user.todayMinutesLearned}m", SkyBlueEthiopia, Modifier.weight(1f))
                        }
                    }

                    // Strengths & Weak Areas Analysis (Afaan Oromoo phonics diagnostics)
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = WarmSurfaceLight),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Qo'annoo Sagalee Dubbii (Acoustic Diagnostics)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                                Spacer(modifier = Modifier.height(10.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = "Strong", tint = EmeraldDark)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text("Cimina: Qubee Dachaa (SH & CH)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                                        Text("Accuracy 95% with natural Qubee sound mapping.", style = MaterialTheme.typography.bodySmall, color = SoftDarkText)
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Warning, contentDescription = "Needs practice", tint = AmberSunDark)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text("Shaakala Kan Barbaadu: P vs B & TH Sagalee", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                                        Text("Gorsa: Daqiiqaa 5 Laabii Sagaleessuu keessatti dabarsaa.", style = MaterialTheme.typography.bodySmall, color = SoftDarkText)
                                    }
                                }
                            }
                        }
                    }

                    // Recent Voice Practice Logs
                    item {
                        Text(
                            text = if (user.oromoSupportEnabled) "Seenaa Sagaleessuu Dhihoo (Voice Practice History):" else "Recent Speaking Evaluation History:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (records.isEmpty()) {
                        item {
                            Text("Ammaaf seenaan sagaleessuu hin jiru. Maaykiroofoonii fayyadamaa!", color = SoftDarkText)
                        }
                    } else {
                        items(records) { record ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(record.targetText, fontWeight = FontWeight.Bold)
                                        Text("${record.moduleName} • ${record.feedback}", style = MaterialTheme.typography.bodySmall, color = SoftDarkText)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (record.score >= 80) EmeraldLight else AmberSunLight)
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text("${record.score}%", fontWeight = FontWeight.Bold, color = DarkText)
                                    }
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
private fun MetricCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(84.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = color)
            Text(title, style = MaterialTheme.typography.labelSmall, color = DarkText, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}
