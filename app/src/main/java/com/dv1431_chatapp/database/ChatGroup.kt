package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude

class ChatGroup : Group {
    private var mUsers: RelationMap

    constructor() : super() {
        mUsers = RelationMap()
    }

    constructor(groupId: String, groupName: String, users: RelationMap = RelationMap()) : super(groupId, groupName) {
        mUsers = users
    }

    @Exclude
    fun addUser(user: String) {
        mUsers.put(user, true)
    }

    @Exclude
    fun removeUser(userId: String) : Any? {
        return mUsers.remove(userId)
    }

    fun getUsers() : RelationMap {
        return mUsers
    }

    fun setUsers(users: RelationMap) {
        mUsers = users
    }

}