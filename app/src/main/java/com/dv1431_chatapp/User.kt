package com.dv1431_chatapp

import com.google.firebase.database.Exclude
import java.io.Serializable

/**
 * Created by dane on 11/27/17.
 */

class User() : Serializable {
    private lateinit var mId: String
    private lateinit var mEmail: String
    private lateinit var mUsername: String
    private lateinit var mGroups: MutableList<String>

    constructor(id: String, email: String, username: String, groups: MutableList<String> = ArrayList()) : this() {
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

    fun getGroups(): MutableList<String> {
        return mGroups
    }
}