package com.example.temo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.temo.R
import com.example.temo.ui.theme.TemoTheme

@Composable
fun ProfileScreen(innerPadding: Dp) {
    LazyColumn(
        modifier = Modifier
            .padding(top = innerPadding)
            .fillMaxSize()
    ) {
        item {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(108.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .size(108.dp)
                        .border(width = 1.dp, color = Color.Gray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_add_circle),
                        contentDescription = "appImg"
                    )
                    Text(
                        text = "Add App Icon",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                addScreenTextField(label = "App Name")
                addScreenTextField(label = "Creator")
                addScreenTextField(label = "App Link")
                addScreenTextField(label = "App Description", Modifier.height(240.dp))
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Add")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun profilePreview() {
    TemoTheme {
        ProfileScreen(innerPadding = 0.dp)
    }
}