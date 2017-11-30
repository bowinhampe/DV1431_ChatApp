package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude
import java.io.Serializable

/**
 * Created by philzon on 11/29/17.
 */

open class Group : Serializable {
    private var mId: String
    private var mName: String

    constructor() {
        mId = "N/A"
        mName = "N/A"
    }

    constructor(id: String, name: String) {
        mId = id
        mName = name
    }

    @Exclude
    fun getId() : String {
        return mId
    }

    fun getName() : String {
        return mName
    }

    @Exclude
    fun setId(id: String) {
        mId = id
    }

    fun setName(name: String) {
        mName = name
    }
}