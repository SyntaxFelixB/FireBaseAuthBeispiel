package com.crixaliz.firebaseauth.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.crixaliz.firebaseauth.data.FireBaseRepository
import com.crixaliz.firebaseauth.data.model.UserModel

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

    fun changeMoney(value: Int) {
        val snapUser = currentAppUser.value
        if(snapUser != null) {
            val user = UserModel(
                snapUser.uid,
                snapUser.balance + value,
                snapUser.userName
            )
            repo.updateUser(user)
        } else {
            Log.d("CHANGEMONEY", "Error user not found while changing money")
        }
    }
}