package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude

class Member {
    private var mId: String
    private var mLastMessage: LastMessage?

    constructor() {
        mId = "N/A"
        mLastMessage = LastMessage()
    }

    constructor(id: String, lastMessage: LastMessage?) {
        mId = id
        mLastMessage = lastMessage
    }

    @Exclude
    fun getId() : String {
        return mId
    }

    @Exclude
    fun setId(id: String) {
        mId = id
    }

    fun getLastMessage() : LastMessage? {
        return mLastMessage
    }

    fun setLastMessage(lastMessage: LastMessage?) {
        mLastMessage = lastMessage
    }

}