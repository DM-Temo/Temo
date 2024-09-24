package com.example.temo.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TemoViewModel : ViewModel() {
    private val firebaseDB = Firebase.firestore
    private val fireStorage = Firebase.storage.reference

    val userFlow = MutableStateFlow(User(userName = "", userId = ""))
    val appQueryFlow = MutableStateFlow<QuerySnapshot?>(null)

    private val _imageUriFlow = MutableStateFlow<Uri?>(null)
    val imageUriFlow: StateFlow<Uri?> get() = _imageUriFlow

    fun updateUser(
        userName: String,
        userId: String
    ) {
        userFlow.value = userFlow.value.copy(
            userName = userName,
            userId = userId
        )
    }

    fun addApp(
        appData: App,
        onSuccess: () -> Unit
    ) {
        firebaseDB.collection("Apps").document(appData.appId)
            .set(appData)
            .addOnSuccessListener {
                onSuccess()
                Log.d("firestore", "Apps add success")
            }
            .addOnFailureListener {
                Log.e("firestore", "Apps add fail")
            }
        firebaseDB.collection("Users").document(appData.userId)
            .collection("Apps").document(appData.appId)
            .set(appData)
            .addOnSuccessListener {
                onSuccess()
                Log.d("firestore", "Users add success")
            }
            .addOnFailureListener {
                Log.e("firestore", "Users add fail")
            }
    }

    fun addAppIcon(
        appIconData: AppIcon,
        onSuccess: () -> Unit
    ) {
        fireStorage.child("appIcon/${appIconData.userId}/${appIconData.appId}.jpg")
            .putFile(appIconData.imgUrl)
            .addOnSuccessListener {
                onSuccess()
            }
    }

    fun getApps() {
        firebaseDB.collection("Apps").whereEqualTo("activation", 0)
            .get()
            .addOnSuccessListener { documents ->
                appQueryFlow.value = documents
            }
    }

    fun getAppIcon(
        userId: String?,
        appId: String?
    ) {
        if (userId==null || appId==null) return
        val imgRef = fireStorage.child("appIcon/${userId}/${appId}.jpg")
        imgRef.downloadUrl
            .addOnSuccessListener {
                _imageUriFlow.value = it
            }.addOnFailureListener {
                Log.d("storage","img download fail")
            }
    }
}

data class User(
    var userName: String,
    var userId: String
)

data class App(
    val userId: String = "",
    var appName: String = "",
    var creator: String = "",
    val releaseDate: String = "",
    var appLink: String = "",
    var appDescription: String = "",
    val appId: String = userId + releaseDate,
    var tester: Int = 0,
    var activation: Int = 0
)

data class AppIcon(
    val userId: String,
    val releaseDate: String,
    val imgUrl: Uri,
    val appId: String = userId + releaseDate
)

class NavViewModel : ViewModel() {
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
}