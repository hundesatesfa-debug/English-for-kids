package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class DrawnLine(
    val path: List<Offset>,
    val color: Color,
    val strokeWidth: Float = 22f
)

@Composable
fun TracingCanvas(
    targetLetter: String,
    onTracingComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lines = remember { mutableStateListOf<DrawnLine>() }
    var currentLine by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var selectedColor by remember { mutableStateOf(EmeraldDark) }
    var traceDone by remember { mutableStateOf(false) }

    val colors = listOf(EmeraldDark, AmberSunDark, CoralEthiopia, SkyBlueEthiopia, PurpleStar)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("tracing_canvas_container"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .border(3.dp, AmberSunLight, RoundedCornerShape(20.dp))
                .pointerInput(targetLetter) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentLine = listOf(offset)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            currentLine = currentLine + change.position
                        },
                        onDragEnd = {
                            if (currentLine.isNotEmpty()) {
                                lines.add(DrawnLine(currentLine, selectedColor))
                                currentLine = emptyList()
                            }
                        }
                    )
                }
                .testTag("drawing_board"),
            contentAlignment = Alignment.Center
        ) {
            // Background dotted letter outline for tracing
            Canvas(modifier = Modifier.fillMaxSize()) {
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.parseColor("#E0E0E0")
                    textSize = 420f
                    textAlign = android.graphics.Paint.Align.CENTER
                    isAntiAlias = true
                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                }
                val xPos = size.width / 2f
                val yPos = (size.height / 2f) - ((paint.descent() + paint.ascent()) / 2f)
                drawContext.canvas.nativeCanvas.drawText(targetLetter, xPos, yPos, paint)

                // Draw existing strokes
                lines.forEach { line ->
                    if (line.path.size > 1) {
                        val path = Path()
                        path.moveTo(line.path.first().x, line.path.first().y)
                        for (i in 1 until line.path.size) {
                            path.lineTo(line.path[i].x, line.path[i].y)
                        }
                        drawPath(
                            path = path,
                            color = line.color,
                            style = Stroke(
                                width = line.strokeWidth,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }

                // Draw current drawing stroke
                if (currentLine.size > 1) {
                    val path = Path()
                    path.moveTo(currentLine.first().x, currentLine.first().y)
                    for (i in 1 until currentLine.size) {
                        path.lineTo(currentLine[i].x, currentLine[i].y)
                    }
                    drawPath(
                        path = path,
                        color = selectedColor,
                        style = Stroke(
                            width = 22f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Tools row: colors + clear + done
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (selectedColor == color) 3.dp else 1.dp,
                                color = if (selectedColor == color) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { selectedColor = color }
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = {
                        lines.clear()
                        currentLine = emptyList()
                        traceDone = false
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("clear_tracing_button")
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Clear")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Clear")
                }

                Button(
                    onClick = {
                        traceDone = true
                        onTracingComplete()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldEthiopia),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("done_tracing_button")
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Done")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (traceDone) "Great! ⭐" else "Check")
                }
            }
        }
    }
}
