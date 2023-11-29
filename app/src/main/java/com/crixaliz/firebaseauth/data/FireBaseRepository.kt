package com.crixaliz.firebaseauth.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crixaliz.firebaseauth.data.model.UserModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

class FireBaseRepository {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val store = FirebaseStorage.getInstance().reference

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

    fun uploadImage(uri: Uri) {
        val ref = store.child(auth.currentUser?.uid!!)
        ref.putFile(uri)
        _currentAppUser.value?.copy(image = uri.toString())?.let { updateUser(it) }
    }


    fun updateUser(user: UserModel) {
        db.collection("user")
            .document(auth.currentUser?.uid!!)
            .set(
                user.toFireBase()
            ).addOnSuccessListener {
                _currentAppUser.postValue(
                    user
                )
            }
    }

    /*
    // FIRE BASE AUTH METHODEN
     */
    fun login(email: String, password: String): Task<String> {
        val src = TaskCompletionSource<String>()
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
                    src.setResult("Fertig")
                }.addOnFailureListener {
                    src.setException(it)
                }
        }
        return src.task
    }

    fun register(email: String, password: String): Task<String> {
        val src = TaskCompletionSource<String>()
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val user = UserModel(
                it.user?.uid!!,
                0,
                "random",
                null
            )

            db.collection("user")
                .document(it.user?.uid!!)
                .set(user.toFireBase())

            _currentAppUser.value = user
            _currentAuthUser.value = it.user
            src.setResult("Fertig")
        }.addOnFailureListener {
            src.setException(it)
        }
        return src.task
    }

    fun logout() {
        auth.signOut()
        _currentAppUser.value = null
        _currentAuthUser.value = null
    }
}