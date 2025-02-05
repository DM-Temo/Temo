package com.example.temo.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.temo.model.App
import com.example.temo.model.AppIcon
import com.example.temo.model.User
import com.example.temo.repository.AppRepository
import com.example.temo.repository.UserRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TemoViewModel(
    private val userRepository: UserRepository,
    private val appRepository: AppRepository
) : ViewModel() {

    private val _userFlow = MutableStateFlow(User())
    val userFlow: StateFlow<User> = _userFlow

    private val _appQueryFlow = MutableStateFlow<QuerySnapshot?>(null)
    val appQueryFlow: StateFlow<QuerySnapshot?> = _appQueryFlow

    private val _userAppQueryFlow = MutableStateFlow<List<DocumentSnapshot>?>(null)
    val userAppQueryFlow: StateFlow<List<DocumentSnapshot>?> = _userAppQueryFlow

    private val _appDetailFlow = MutableStateFlow(App())
    val appDetailFlow: StateFlow<App> = _appDetailFlow

    fun updateUser(userName: String, userId: String) {
        _userFlow.value = _userFlow.value.copy(userName = userName, userId = userId)
    }

    fun addApp(appData: App, onSuccess: () -> Unit) {
        appRepository.addApp(appData, onSuccess, { e -> e.printStackTrace() })
    }

    fun addAppIcon(appIconData: AppIcon, onSuccess: () -> Unit) {
        appRepository.uploadAppIcon(appIconData, onSuccess, { e -> e.printStackTrace() })
    }

    fun getApps() {
        viewModelScope.launch {
            _appQueryFlow.value = appRepository.getApps()
        }
    }

    fun getUser(userId: String) {
        viewModelScope.launch {
            val user = userRepository.getUser(userId)
            if (user != null) {
                _userFlow.value = user
            }
        }
    }

    suspend fun getAppIcon(userId: String?, appId: String?): Uri? {
        return if (userId != null && appId != null) {
            appRepository.getAppIcon(userId, appId)
        } else null
    }

    fun onAppDetailPath(appDetail: App, appIcon: Uri?) {
        _appDetailFlow.value = appDetail.copy(appIcon = appIcon)
    }
}