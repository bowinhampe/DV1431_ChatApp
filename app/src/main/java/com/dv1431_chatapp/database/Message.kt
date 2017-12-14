package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude

/**
 * Created by dane on 11/29/17.
 */
open class Message {
    private var mId: String
    private var mUser: String
    private var mMessage: String

    constructor() {
        mId = "N/A"
        mUser = "N/A"
        mMessage = "N/A"
    }

    constructor(id: String, user: String, message: String) {
        mId = id
        mUser = user
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