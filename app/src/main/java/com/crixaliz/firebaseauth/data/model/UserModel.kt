package com.crixaliz.firebaseauth.data.model

import com.google.firebase.firestore.DocumentSnapshot

data class UserModel(
    val uid: String,
    val balance: Int,
    val userName: String
) {
    companion object {
        fun fromFireBase(snapshot: DocumentSnapshot): UserModel {
            return UserModel(
                snapshot["uid"] as String,
                (snapshot["balance"] as Long).toInt(),
                snapshot["userName"] as String,
            )
        }
    }

    fun toFireBase(): Map<String, *> {
        return mapOf(
            "uid" to uid,
            "balance" to balance,
            "userName" to userName
        )
    }
}