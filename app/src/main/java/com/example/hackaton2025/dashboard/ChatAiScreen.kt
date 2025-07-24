package com.example.hackaton2025.dashboard

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackaton2025.R

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChatAiScreen(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onMicClick: () -> Unit
) {
    val borderColor = Color(0xFF0D5688)

    var rotationDegrees by remember { mutableStateOf(0f) }
    var isTyping by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(text) {
        if (text.isNotEmpty()) {

            isTyping = true
            debounceJob?.cancel()

            debounceJob = scope.launch {
                delay(1000L)
                isTyping = false
                rotationDegrees = 0f
            }
        } else {
            debounceJob?.cancel()
            isTyping = false
            rotationDegrees = 0f
        }
    }

    LaunchedEffect(isTyping) {
        if (isTyping) {
            while (true) {
                val steps = 36
                val stepDuration = 500L / steps
                repeat(steps) {
                    rotationDegrees = (rotationDegrees + 10f) % 360f
                    delay(stepDuration)
                }
            }
        }
    }

    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chat_gpt),
                        contentDescription = "GPT Icon",
                        tint = borderColor,
                        modifier = Modifier
                            .size(32.dp)
                            .graphicsLayer {
                                rotationZ = rotationDegrees
                            }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    if (text.isEmpty()) {
                        Text(
                            text = "Ask anything",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    } else {
                        innerTextField()
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = Color(0xFFEDF4FC),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(onClick = onMicClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_voice_foreground),
                            contentDescription = "Mic",
                            tint = Color.Black
                        )
                    }
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ChatAiScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ChatAiScreen(
            modifier = Modifier,
            text = "",
            onTextChange = {},
            onMicClick = {}
        )
    }
}