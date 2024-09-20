package com.example.temo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.temo.R
import com.example.temo.ui.theme.TemoTheme

@Composable
fun DetailScreen(innerPadding: Dp) {
    LazyColumn(
        modifier = Modifier
            .padding(top = innerPadding)
            .fillMaxSize()
    ) {
        item {
            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
                    .height(108.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.appicon_mockup),
                    contentDescription = "appImg"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                val detailList = listOf("App Name", "Creator", "Post Date", "Credits")
                detailList.forEach {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = it,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            Text(text = "App Description",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp))
            Text(text = "test",
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, bottom = 24.dp))
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Current Applicants : 5/20")
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Test Now!")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar() {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier.height(88.dp),
        title = {
            Row(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 8.dp, start = 4.dp, end = 8.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_arrow_back),
                        contentDescription = "back"
                    )
                }
                Text(
                    "App Details",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 30.dp)
                )
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_more),
                        contentDescription = "moreOpt"
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    TemoTheme {
        DetailScreen(innerPadding = 0.dp)
    }
}