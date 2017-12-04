package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude

class ChatGroup : Group {
    private var mUserIds: ArrayList<String>
    private var mMessages: ArrayList<Message>

    constructor() : super() {
        mUserIds = ArrayList()
        mMessages = ArrayList()
    }

    constructor(groupId: String, groupName: String, users: ArrayList<String> = ArrayList(), messages: ArrayList<Message> = ArrayList()) : super(groupId, groupName) {
        mUserIds = users
        mMessages = messages
    }

    @Exclude
    fun addUser(user: String) {
        mUserIds.add(user)
    }

    @Exclude
    fun removeUser(user: String) : Boolean {
        return mUserIds.remove(user)
    }

    @Exclude
    fun addMessage(message: Message) {
        mMessages.add(message)
    }

    @Exclude
    fun removeMessage(message: Message) : Boolean {
        return mMessages.remove(message)
    }

    fun getUsers() : ArrayList<String> {
        return mUserIds
    }

    fun getMessages() : ArrayList<Message> {
        return mMessages
    }

    fun setUsers(users: ArrayList<String>) {
        mUserIds = users
    }

    fun setMessages(messages: ArrayList<Message>) {
        mMessages = messages
    }

}