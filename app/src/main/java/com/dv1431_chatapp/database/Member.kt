package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude

class Member {
    private var mId: String
    private var mLocation: Any

    constructor() {
        mId = "N/A"
        mLocation = "N/A"
    }

    constructor(id: String, location: Any) {
        mId = id
        mLocation = location
    }

    @Exclude
    fun getId() : String {
        return mId
    }

    @Exclude
    fun setId(id: String) {
        mId = id
    }

    fun getLocation() : Any {
        return mLocation
    }

    fun setLocation(location: Any) {
        mLocation = location
    }
}