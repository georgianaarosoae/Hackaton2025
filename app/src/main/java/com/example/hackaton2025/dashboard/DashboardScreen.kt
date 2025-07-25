package com.example.hackaton2025.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hackaton2025.R
import com.example.hackaton2025.viewmodel.OverviewViewModel

@Composable
fun DashboardScreen(navController: NavController) {
    val vm: OverviewViewModel = hiltViewModel()

    var text by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val imageHeight = screenHeightDp * 0.3f

    Column(modifier = Modifier.padding(horizontal = 16.dp))  {
        Row {
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .width(64.dp)
                    .clip(CircleShape)
                    .offset(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_kid),
                    contentDescription = "",
                )
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            MoneyStatusScreen()

            Spacer(modifier = Modifier.padding(top = 32.dp))

            ChatAiScreen(
                text = text,
                onTextChange = { text = it },
                onMicClick = {  }
            )

            Row(Modifier.padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Main Tasks",
                    color = Color(0xFF0D5688),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = "View All",
                    color = Color.Gray,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            MainTasks()


            Row(Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Main Objectives",
                    color = Color(0xFF0D5688),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = "View All",
                    color = Color.Gray,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            MainObjectives()
        }
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
        DashboardScreen(rememberNavController())
    }
}