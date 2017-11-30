package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude

/**
 * Created by dane on 11/30/17.
 */

class ChatGroup : Group {
    private var mUsers: ArrayList<User>
    private var mMessages: ArrayList<Message>

    constructor() : super() {
        mUsers = ArrayList()
        mMessages = ArrayList()
    }

    constructor(groupId: String, groupName: String, users: ArrayList<User> = ArrayList(), messages: ArrayList<Message> = ArrayList()) : super(groupId, groupName) {
        mUsers = users
        mMessages = messages
    }

    @Exclude
    fun addUser(user: User) {
        mUsers.add(user)
    }

    @Exclude
    fun removeUser(user: User) : Boolean {
        return mUsers.remove(user)
    }

    @Exclude
    fun addMessage(message: Message) {
        mMessages.add(message)
    }

    @Exclude
    fun removeMessage(message: Message) : Boolean {
        return mMessages.remove(message)
    }

    fun getUsers() : ArrayList<User> {
        return mUsers
    }

    fun getMessages() : ArrayList<Message> {
        return mMessages
    }

    fun setUsers(users: ArrayList<User>) {
        mUsers = users
    }

    fun setMessages(messages: ArrayList<Message>) {
        mMessages = messages
    }

}