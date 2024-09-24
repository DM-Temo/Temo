package com.example.temo.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.temo.viewmodels.App
import com.example.temo.viewmodels.AppIcon
import com.example.temo.R
import com.example.temo.viewmodels.TemoViewModel
import java.time.LocalDateTime

@Composable
fun AddScreen(
    innerPadding: Dp,
    temoViewModel: TemoViewModel
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = innerPadding)
            .fillMaxSize()
    ) {
        val userDataState = temoViewModel.userFlow.value
        item {
            var appName by remember { mutableStateOf("") }
            var creator by remember { mutableStateOf("") }
            var appLink by remember { mutableStateOf("") }
            var appDescription by remember { mutableStateOf("") }
            var imageUri by remember {
                mutableStateOf<Uri?>(null)
            }
            val pickMedia =
                rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    imageUri = uri
                }

            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(108.dp)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri == null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .size(108.dp)
                            .border(width = 1.dp, color = Color.Gray)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_add_circle),
                            contentDescription = "defaultAppImg"
                        )
                        Text(
                            text = "Add App Icon",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                } else
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "appImg"
                    )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                AddScreenTextField(
                    input = appName,
                    label = "App Name",
                    onValueChange = { appName = it }
                )
                AddScreenTextField(
                    input = creator,
                    label = "Creator",
                    onValueChange = { creator = it })
                AddScreenTextField(
                    input = appLink,
                    label = "App Link",
                    onValueChange = { appLink = it })
                AddScreenTextField(
                    input = appDescription,
                    label = "App Description",
                    onValueChange = { appDescription = it },
                    Modifier.height(240.dp)
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    try {
                        val localTimeNow = LocalDateTime.now()
                        val addAppData = App(
                            userId = userDataState.userId,
                            appName = appName,
                            creator = creator,
                            releaseDate = localTimeNow.toString(),
                            appLink = appLink,
                            appDescription = appDescription
                        )
                        val addAppIconData = AppIcon(
                            userId = userDataState.userId,
                            releaseDate = localTimeNow.toString(),
                            imgUrl = imageUri!!
                        )
                        Log.d("test", "$addAppData")
                        temoViewModel.addApp(addAppData, onSuccess = {})
                        temoViewModel.addAppIcon(addAppIconData, onSuccess = {})
                    } catch (e: Exception) {
                        Log.e("ButtonClick", "Error: ${e.message}", e)
                    }
                }) {
                    Text(text = "Add")
                }
            }
            Spacer(modifier = Modifier.size(200.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTopBar() {
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
                    "Add App",
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

@Composable
fun AddScreenTextField(
    input: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = input,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White
        ),
        label = {
            Text(
                text = label,
                modifier = Modifier.alpha(
                    if (isFocused) 1f else 0.5f
                )
            )
        },
        shape = RoundedCornerShape(15.dp)
    )
}