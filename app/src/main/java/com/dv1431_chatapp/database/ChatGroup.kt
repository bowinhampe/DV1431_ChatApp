package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude

class ChatGroup : Group {
    private var mMembers: RelationMap

    constructor() : super() {
        mMembers = RelationMap()
    }

    constructor(groupId: String, groupName: String, users: RelationMap = RelationMap()) : super(groupId, groupName) {
        mMembers = users
    }

    @Exclude
    fun addMember(user: String) {
        mMembers.put(user, true)
    }

    @Exclude
    fun removeMember(userId: String) : Any? {
        return mMembers.remove(userId)
    }

    fun getMembers() : RelationMap {
        return mMembers
    }

    fun setMembers(users: RelationMap) {
        mMembers = users
    }

}