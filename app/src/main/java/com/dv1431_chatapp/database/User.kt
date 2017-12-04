package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude
import java.io.Serializable

class User : Serializable {
    private lateinit var mId: String
    private lateinit var mEmail: String
    private lateinit var mUsername: String
    private lateinit var mGroups: Map<String, String>

    constructor() {
        mId = "N/A"
        mEmail = "N/A"
        mUsername = "N/A"
        mGroups = HashMap()
    }

    constructor(id: String, email: String, username: String, groups: Map<String, String> = HashMap()) {
        mId = id
        mEmail = email
        mUsername = username
        mGroups = groups
    }

    @Exclude
    fun getId(): String {
        return mId
    }

    fun getEmail(): String {
        return mEmail
    }

    fun getUsername(): String {
        return mUsername
    }

    fun getGroups(): Map<String, String> {
        return mGroups
    }

    @Exclude
    fun setId(id: String) {
        mId = id
    }

    fun setEmail(email: String) {
        mEmail = email
    }

    fun setUsername(username: String) {
        mUsername = username
    }

    fun setGroups(groups: Map<String, String>) {
        mGroups = groups
    }
}