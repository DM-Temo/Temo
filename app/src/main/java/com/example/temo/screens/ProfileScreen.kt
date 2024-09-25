package com.example.temo.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.temo.viewmodels.NavViewModel
import com.example.temo.R
import com.example.temo.viewmodels.TemoViewModel
import com.example.temo.ui.theme.TemoTheme
import com.example.temo.viewmodels.App

@Composable
fun ProfileScreen(
    innerPadding: Dp,
    temoViewModel: TemoViewModel,
    navViewModel: NavViewModel,
    navController: NavController
) {
    val userAppSnapshot = temoViewModel.userAppQueryFlow.collectAsState(initial = null).value ?: emptyList()
    LazyColumn(
        modifier = Modifier
            .padding(top = innerPadding)
            .fillMaxSize()
    ) {
        val userState = temoViewModel.userFlow.value
        temoViewModel.getUser(userState.userId)
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
                        contentDescription = "Profile Photo"
                    )
                    Text(
                        text = "Add Your Photo",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = userState.userName)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Config")
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "App List",
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "App Counts : ${userState.appCount}",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
        items(userAppSnapshot) { document ->
            val appData = document.toObject(App::class.java) ?: App()
            val appIcon by produceState<Uri?>(initialValue = null) {
                value = temoViewModel.getAppIcon(appData.userId, appData.appId)
            }
            HomeAppCard(img = appIcon,
                appName = appData.appName,
                creator = appData.creator,
                onCardClick = {
                    temoViewModel.onAppDetailPath(appData, appIcon)
                    navViewModel.navigateToDetail(navController)
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar() {
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
                    "User Profile",
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
fun ProfilePreview() {
    TemoTheme {
        val temoViewModel = TemoViewModel()
        val navController = rememberNavController()
//        ProfileScreen(innerPadding = 0.dp, temoViewModel, navController)
    }
}