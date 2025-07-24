package com.example.hackaton2025.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen() {
    var text by remember { mutableStateOf("") }
    Column() {
        MoneyStatusScreen()
        Spacer(modifier = Modifier.height(16.dp))
        ChatAiScreen(
            text = text,
            onTextChange = { text = it },
            onMicClick = {  }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DashboardScreen()
    }
}