package com.dv1431_chatapp.database

/**
 * Created by dane on 11/29/17.
 */
class Message() {
    private lateinit var mId: String
    private lateinit var mUserId: String
    private lateinit var mMessage: String

    constructor(id: String, userId: String, message: String) : this() {
        mId = id
        mUserId = userId
        mMessage = message
    }
}