package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude

class ChatGroup : Group {
    private var mUserIds: IdMap
    private var mMessages: ArrayList<Message>

    constructor() : super() {
        mUserIds = IdMap()
        mMessages = ArrayList()
    }

    constructor(groupId: String, groupName: String, users: IdMap = IdMap(), messages: ArrayList<Message> = ArrayList()) : super(groupId, groupName) {
        mUserIds = users
        mMessages = messages
    }

    @Exclude
    fun addUser(user: String) {
        mUserIds.put(user, "N/A")
    }

    @Exclude
    fun removeUser(userId: String) : Any? {
        return mUserIds.remove(userId)
    }

    @Exclude
    fun addMessage(message: Message) {
        mMessages.add(message)
    }

    @Exclude
    fun removeMessage(message: Message) : Boolean {
        return mMessages.remove(message)
    }

    fun getUsers() : IdMap {
        return mUserIds
    }

    fun getMessages() : ArrayList<Message> {
        return mMessages
    }

    fun setUsers(users: IdMap) {
        mUserIds = users
    }

    fun setMessages(messages: ArrayList<Message>) {
        mMessages = messages
    }

}