package com.example.temo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.temo.screens.AddScreen
import com.example.temo.screens.AddTopBar
import com.example.temo.screens.DetailScreen
import com.example.temo.screens.DetailTopBar
import com.example.temo.screens.HomeScreen
import com.example.temo.screens.HomeTopBar
import com.example.temo.screens.ProfileScreen
import com.example.temo.screens.ProfileTopBar
import com.example.temo.ui.theme.TemoTheme
import com.example.temo.viewmodels.NavViewModel
import com.example.temo.viewmodels.TemoViewModel

class HomeActivity : ComponentActivity() {
    private val temoViewModel: TemoViewModel by viewModels()
    private val navViewModel: NavViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userName = intent.getStringExtra("userName")
        val userId = intent.getStringExtra("userId")
        temoViewModel.updateUser(userName!!, userId!!)

        enableEdgeToEdge()
        setContent {
            temoViewModel
            TemoTheme {
                val navController = rememberNavController()

                MainScaffold(
                    temoViewModel = temoViewModel,
                    navViewModel = navViewModel,
                    navController = navController
                )
            }
        }
    }
}

sealed class Screen(
    val route: String,
    val icon: Int? = null
) {
    data object Home : Screen("home", R.drawable.icon_home)
    data object Profile : Screen("profile", R.drawable.icon_person)
    data object Add : Screen("add", R.drawable.icon_add)
    data object Detail : Screen("detail")
}

@Composable
fun MainScaffold(
    temoViewModel: TemoViewModel,
    navViewModel: NavViewModel,
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            when (currentRoute) {
//                            Screen.Home.route -> HomeTopBar()
                Screen.Profile.route -> ProfileTopBar()
                Screen.Add.route -> AddTopBar()
                Screen.Detail.route -> DetailTopBar()
                else -> HomeTopBar()
            }
        },
        content = { innerPadding ->
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        innerPadding = innerPadding.calculateTopPadding(),
                        temoViewModel = temoViewModel,
                        navViewModel = navViewModel,
                        navController = navController
                    )
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(
                        innerPadding = innerPadding.calculateTopPadding(),
                        temoViewModel = temoViewModel,
                        navViewModel = navViewModel,
                        navController = navController
                    )
                }
                composable(Screen.Add.route) {
                    AddScreen(
                        innerPadding = innerPadding.calculateTopPadding(),
                        temoViewModel = temoViewModel
                    )
                }
                composable(Screen.Detail.route) {
                    DetailScreen(
                        innerPadding = innerPadding.calculateTopPadding(),
                        temoViewModel = temoViewModel
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(navViewModel, navController) },
    )
}


@Composable
fun BottomNavigationBar(
    navViewModel: NavViewModel,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        FloatCircleButton(
            navViewModel,
            navController
        )
    }
    BottomAppBar(
        containerColor = Color.Transparent,
        modifier = Modifier
            .padding(10.dp)
            .background(Color.Transparent)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
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
                                    navViewModel.navigateToHome(navController)
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
                                    navViewModel.navigateToProfile(navController)
                                }
                            }
                    )
                }
            }
        }
    }
}


@Composable
fun FloatCircleButton(
    navViewModel: NavViewModel,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .offset(
                y = (-25).dp
            )
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
                        navViewModel.navigateToAdd(navController)
                    }
                }
        )
    }
}