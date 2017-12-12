package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude
import java.io.Serializable

class User : Serializable {
    private var mId: String
    private var mEmail: String
    private var mUsername: String
    private var mGroups: RelationMap

    constructor() {
        mId = "N/A"
        mEmail = "N/A"
        mUsername = "N/A"
        mGroups = RelationMap()
    }

    constructor(id: String, email: String, username: String, groupIds: RelationMap = RelationMap()) {
        mId = id
        mEmail = email
        mUsername = username
        mGroups = groupIds
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

    fun getGroups(): RelationMap {
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

    fun setGroups(groups: HashMap<String, Any>) {
        mGroups.putAll(groups)
    }
}