package com.example.temo.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TemoViewModel : ViewModel() {
    private val firebaseDB = Firebase.firestore
    private val fireStorage = Firebase.storage.reference

    val userFlow = MutableStateFlow(User())
    val appQueryFlow = MutableStateFlow<QuerySnapshot?>(null)
    val userAppQueryFlow = MutableStateFlow<List<DocumentSnapshot>?>(null)

    val appDetailFlow = MutableStateFlow(App())

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
                firebaseDB.collection("Users").document(appData.userId)
                    .set("userName" to userFlow.value.userName)
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

    fun getUser(userId: String) {
        firebaseDB.collection("Users").document(userId)
            .get()
            .addOnSuccessListener { documents ->
                documents.reference.collection("Apps")
                    .get()
                    .addOnSuccessListener { apps ->
                        userFlow.value = userFlow.value.copy(appCount = apps.size())
                        userAppQueryFlow.value = apps.documents
                    }
            }
    }

    suspend fun getAppIcon(userId: String?, appId: String?): Uri? {
        if (userId == null || appId == null) return null
        return try {
            val imgRef = fireStorage.child("appIcon/${userId}/${appId}.jpg")
            imgRef.downloadUrl.await()  // Firebase Task를 suspend로 변환
        } catch (e: Exception) {
            Log.d("storage", "img download fail: $e")
            null
        }
    }

    fun onAppDetailPath(
        appDetail: App,
        appIcon: Uri?
    ) {
        appDetailFlow.value = appDetail
        appDetailFlow.value.appIcon = appIcon
    }
}

data class User(
    var userName: String = "",
    var userId: String = "",
    var appCount: Int = 0
)

data class App(
    val userId: String = "",
    var appName: String = "Loading..",
    var creator: String = "Loading..",
    val postDate: String = "",
    val postTime: String = "",
    var appLink: String = "",
    var appDescription: String = "",
    val appId: String = userId + postTime,
    var tester: Int = 0,
    var activation: Int = 0,
    var appIcon: Uri? = null
)

data class AppIcon(
    val userId: String,
    val postTime: String,
    val imgUrl: Uri,
    val appId: String = userId + postTime
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