package com.crixaliz.firebaseauth.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crixaliz.firebaseauth.data.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class FireBaseRepository {

    val auth = Firebase.auth
    val db = Firebase.firestore

    private val _currentAuthUser = MutableLiveData<FirebaseUser?>(auth.currentUser)
    val currentAuthUser: LiveData<FirebaseUser?>
        get() = _currentAuthUser

    private val _currentAppUser = MutableLiveData<UserModel?>(null)
    val currentAppUser: LiveData<UserModel?>
        get() = _currentAppUser

    init {
        if(auth.currentUser != null) {
            db.collection("user")
                .document(auth.currentUser?.uid!!)
                .get()
                .addOnSuccessListener { user ->
                    _currentAppUser.postValue(
                        UserModel.fromFireBase(user)
                    )
                }
        }
    }





    /*
    // FIRE BASE AUTH METHODEN
     */
    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            db.collection("user")
                .document(it.user?.uid!!)
                .get()
                .addOnSuccessListener { user ->
                    _currentAppUser.postValue(
                        UserModel.fromFireBase(user)
                    )
                    _currentAuthUser.postValue(
                        it.user
                    )
                }
        }
    }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val user = UserModel(
                it.user?.uid!!,
                0,
                "random"
            )

            db.collection("user")
                .document(it.user?.uid!!)
                .set(user.toFireBase())

            _currentAppUser.value = user
            _currentAuthUser.value = it.user
        }
    }

    fun logout() {
        auth.signOut()
        _currentAppUser.value = null
        _currentAuthUser.value = null
    }
}