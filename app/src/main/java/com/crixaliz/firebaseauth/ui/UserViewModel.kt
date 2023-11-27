package com.crixaliz.firebaseauth.ui

import androidx.lifecycle.ViewModel
import com.crixaliz.firebaseauth.data.FireBaseRepository

class UserViewModel: ViewModel() {
    val repo = FireBaseRepository()
    val currentAuthUser = repo.currentAuthUser
    val currentAppUser = repo.currentAppUser

    fun login(email: String, password: String) {
        repo.login(email, password)
    }

    fun register(email: String, password: String) {
        repo.register(email, password)
    }

    fun logout() {
        repo.logout()
    }
}