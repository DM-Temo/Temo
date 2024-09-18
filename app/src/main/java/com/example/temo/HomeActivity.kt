package com.example.temo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.temo.ui.theme.TemoTheme
import com.example.temo.ui.theme.customBody

class HomeActivity : ComponentActivity() {
    private val TemoViewModel: TemoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TemoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) { HomeScreen(innerPadding = PaddingValues(0.dp)) }
//            composable(Screen.Profile.route) { ProfileScreen(innerPadding = PaddingValues(0.dp))}
//            composable(Screen.Add.route) { AddScreen(innerPadding = PaddingValues(0.dp))}
//            composable(Screen.Detail.route) { DetailScreen(innerPadding = PaddingValues(0.dp))}
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { HomeTopBar() },
                    bottomBar = { BottomNavigationBar(TemoViewModel, navController) }
                ) { innerPadding ->
                    HomeScreen(innerPadding)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues = innerPadding)
    ) {

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
fun BottomNavigationBar(
    TemoViewModel: TemoViewModel, navController: NavHostController
) {
    BottomAppBar(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(10.dp)
    ) {

        var boxSize by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .onSizeChanged { size ->
                    // Box의 사이즈를 저장
                    boxSize = size
                }
                .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                .drawWithContent {
                    drawContent()
                    drawCircle(
                        color = Color.Transparent,
                        center = Offset(size.width / 2, 0f),
                        radius = 124f,
                        blendMode = BlendMode.Clear
                        //움푹 파인 효과
                    )
                }
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(15.dp)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = Screen.Home.icon!!),
                            contentDescription = "homeIcon",
                            modifier = Modifier
                                .size(36.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        TemoViewModel.navigateToHome(navController)
                                    }
                                }
                        )
                    }
                    Box(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = Screen.Profile.icon!!),
                            contentDescription = "profileIcon",
                            modifier = Modifier
                                .size(36.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        TemoViewModel.navigateToProfile(navController)
                                    }
                                }
                        )
                    }
                }

            }
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .offset(y = (-40).dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = Screen.Add.icon!!),
                    contentDescription = "addIcon",
                    modifier = Modifier
                        .size(50.dp)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                TemoViewModel.navigateToAdd(navController)
                            }
                        }
                )
            }
        }
    }
}

sealed class Screen(
    val route: String,
    val icon: Int? = null
) {
    object Home : Screen("home", R.drawable.icon_home)
    object Profile : Screen("profile", R.drawable.icon_person)
    object Add : Screen("add", R.drawable.icon_add)
    object Detail : Screen("detail")
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    TemoTheme {
//        HomeTopBar()
//        TopSearchBar()
//        BottomNavigationBar()
    }
}