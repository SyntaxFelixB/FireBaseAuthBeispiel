package com.crixaliz.firebaseauth.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.crixaliz.firebaseauth.data.FireBaseRepository
import com.crixaliz.firebaseauth.data.model.UserModel
import com.google.android.gms.tasks.Task

class UserViewModel: ViewModel() {
    private val repo = FireBaseRepository()
    val currentAuthUser = repo.currentAuthUser
    val currentAppUser = repo.currentAppUser


    fun changeMoney(value: Int) {
        val snapUser = currentAppUser.value
        if(snapUser != null) {
            val user = UserModel(
                snapUser.uid,
                snapUser.balance + value,
                snapUser.userName,
                snapUser.image
            )
            repo.updateUser(user)
        } else {
            Log.d("CHANGEMONEY", "Error user not found while changing money")
        }
    }

    fun uploadImage(uri: Uri) {
        repo.uploadImage(uri)
    }


    /*
    //  Auth methods
     */
    fun login(email: String, password: String): Task<String> {
        return repo.login(email, password)
    }

    fun register(email: String, password: String): Task<String> {
        return repo.register(email, password)
    }

    fun logout() {
        repo.logout()
    }
}