package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude
import java.io.Serializable

class User : Serializable {
    private lateinit var mId: String
    private lateinit var mEmail: String
    private lateinit var mUsername: String
    private lateinit var mGroupIds: IdMap

    constructor() {
        mId = "N/A"
        mEmail = "N/A"
        mUsername = "N/A"
        mGroupIds = IdMap()
    }

    constructor(id: String, email: String, username: String, groupIds: IdMap = IdMap()) {
        mId = id
        mEmail = email
        mUsername = username
        mGroupIds = groupIds
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

    fun getGroups(): IdMap {
        return mGroupIds
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

    fun setGroups(groupIds: IdMap) {
        mGroupIds = groupIds
    }
}