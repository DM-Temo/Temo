package com.example.temo.repository

import com.example.temo.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import android.net.Uri
import com.example.temo.model.App
import com.example.temo.model.AppIcon
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebaseDB: FirebaseFirestore
) {
    suspend fun getUser(userId: String): User? {
        return try {
            val document = firebaseDB.collection("Users").document(userId).get().await()
            val apps = document.reference.collection("Apps").get().await()
            document.toObject(User::class.java)?.copy(appCount = apps.size())
        } catch (e: Exception) {
            null
        }
    }
}

class AppRepository @Inject constructor(
    private val firebaseDB: FirebaseFirestore,
    private val fireStorage: FirebaseStorage
) {
    suspend fun getApps(): QuerySnapshot? {
        return try {
            firebaseDB.collection("Apps").whereEqualTo("activation", 0).get().await()
        } catch (e: Exception) {
            null
        }
    }

    fun addApp(appData: App, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firebaseDB.collection("Apps").document(appData.appId)
            .set(appData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }

        firebaseDB.collection("Users").document(appData.userId)
            .collection("Apps").document(appData.appId)
            .set(appData)
    }

    suspend fun getAppIcon(userId: String, appId: String): Uri? {
        return try {
            fireStorage.reference.child("appIcon/${userId}/${appId}.jpg").downloadUrl.await()
        } catch (e: Exception) {
            null
        }
    }

    fun uploadAppIcon(appIconData: AppIcon, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        fireStorage.reference.child("appIcon/${appIconData.userId}/${appIconData.appId}.jpg")
            .putFile(appIconData.imgUrl)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}