package com.example.temo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class TemoViewModel : ViewModel() {
//    private val firebaseDB = Firebase.firestore
//    private val fireStorage = Firebase.storage
    val userFlow = MutableStateFlow(User(userName = "", userId = ""))

    fun navigateToHome(navController: NavController) {
        viewModelScope.launch {
            navController.navigate("home")
        }
    }

    fun navigateToProfile(navController: NavController) {
        viewModelScope.launch {
            navController.navigate("profile")
        }
    }

    fun navigateToAdd(navController: NavController) {
        viewModelScope.launch {
            navController.navigate("add")
        }
    }

    fun navigateToDetail(navController: NavController) {
        viewModelScope.launch {
            navController.navigate("detail")
        }
    }

    fun updateUser(
        userName: String,
        userId: String
    ) {
        userFlow.value = userFlow.value.copy(
            userName = userName,
            userId = userId
        )
    }

//    fun addApp(appData: App,
//               onSuccess: () -> Unit) {
//        firebaseDB.collection("Apps").document(appData.userId).set(appData)
//            .addOnSuccessListener { onSuccess()
//                Log.d("firestore", "success")}
//            .addOnFailureListener {
//                Log.e("firestore", "ee")
//            }
//    }
}

data class User(
    var userName: String,
    var userId: String
)

data class App(
    val userId: String,
    var appName: String,
    var creator: String,
    val releaseDate: LocalDate,
    var appLink: String,
    var appDescription: String,
    val appId: String = userId+releaseDate.toString()
)

data class AppIcon(
    val userId: String,
    val releaseDate: LocalDate,
    val appId: String = userId+releaseDate.toString(),
    val imgUrl: Int
)