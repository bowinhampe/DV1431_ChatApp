package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude

/**
 * Created by dane on 11/29/17.
 */
class Message() {
    private lateinit var mId: String
    private lateinit var mUser: String
    private lateinit var mMessage: String

    constructor(id: String, userId: String, message: String) : this() {
        mId = id
        mUser = userId
        mMessage = message
    }

    @Exclude
    fun getId() : String {
        return mId
    }

    fun getUser() : String {
        return mUser
    }

    fun getMessage() : String {
        return mMessage
    }

    @Exclude
    fun setId(id: String) {
        mId = id
    }

    fun setUser(user: String) {
        mUser = user
    }

    fun setMessage(message: String) {
        mMessage = message
    }
}