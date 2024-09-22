package com.example.temo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.temo.R
import com.example.temo.TemoViewModel
import com.example.temo.ui.theme.customBody

@Composable
fun HomeScreen(innerPadding: Dp, temoViewModel: TemoViewModel, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .padding(top = innerPadding, start = 4.dp, end = 4.dp)
    ) {
        items(10) {
            HomeAppCard(img = R.drawable.appicon_mockup,
                onCardClick = { temoViewModel.navigateToDetail(navController) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier.height(160.dp),
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text("Test Apps")
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(text = "credit")
                    }
                }
                Text(
                    text = "Test these apps to get credits",
                    style = MaterialTheme.typography.customBody
                )
                TopSearchBar(
                    Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBar(modifier: Modifier) {
    var isexpanded by remember { mutableStateOf(false) }
    var searchInput by remember { mutableStateOf("") }
    SearchBar(
        inputField = {
            Row(
                modifier = modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_search),
                    contentDescription = "searchIcon"
                )
                SearchBarDefaults.InputField(
                    query = searchInput,
                    onQueryChange = { searchInput = it },
                    onSearch = {},
                    expanded = isexpanded,
                    onExpandedChange = {},
                    placeholder = {
                        Text(
                            text = "Search for apps...",
                            modifier = Modifier.alpha(0.7f)
                        )
                    }
                )
            }
        },
        expanded = isexpanded,
        onExpandedChange = {}) {
    }
}

@Composable
fun HomeAppCard(
    img: Int,
    onCardClick: () -> Unit
) {
    Card(
        onClick = { onCardClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(108.dp)
                    .padding(6.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
            ) {
                Image(painter = painterResource(id = img), contentDescription = "appImg")
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 20.dp)
                    .padding(start = 6.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "appName",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "creatorName",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                modifier = Modifier
                    .height(60.dp)
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Box(modifier = Modifier) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_arrow_next),
                        contentDescription = "nextIcon"
                    )
                }
                Text(
                    text = "20 Credits",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}