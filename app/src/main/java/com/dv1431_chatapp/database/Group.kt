package com.dv1431_chatapp.database

import java.io.Serializable

/**
 * Created by philzon on 11/29/17.
 */

class Group() : Serializable {
    private lateinit var mGroupId: String
    private lateinit var mGroupName: String

    constructor(groupId: String, groupName: String = "N/A") : this() {

    }
}