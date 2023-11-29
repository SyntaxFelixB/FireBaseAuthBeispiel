package com.crixaliz.firebaseauth.data.model

import com.google.firebase.firestore.DocumentSnapshot

data class UserModel(
    val uid: String,
    var balance: Int,
    val userName: String,
    val image: String?
) {
    companion object {
        fun fromFireBase(snapshot: DocumentSnapshot): UserModel {
            return UserModel(
                snapshot["uid"] as String,
                (snapshot["balance"] as Long).toInt(),
                snapshot["userName"] as String,
                snapshot["image"] as String?
            )
        }
    }

    fun toFireBase(): Map<String, *> {
        return mapOf(
            "uid" to uid,
            "balance" to balance,
            "userName" to userName,
            "image" to image
        )
    }
}