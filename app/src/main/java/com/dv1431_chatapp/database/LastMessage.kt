package com.dv1431_chatapp.database

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.Exclude
import java.io.Serializable

/**
 * Created by dane on 12/14/17.
 */

class LastMessage : Message, Serializable {
    private lateinit var mUserId: String
    private var mLocation: Location? = null

    constructor() : super() {
        mLocation = null
    }

    constructor(id: String, user: String, message: String, location: Location) : super(id, user, message) {
        mLocation = location
    }

    constructor(message: Message) {
        setId(message.getId())
        setUser(message.getUser())
        setMessage(message.getMessage())
        mLocation = null
    }

    @Exclude
    fun getUserId() : String {
        return mUserId
    }

    @Exclude
    fun setUserId(userId: String) {
        mUserId = userId
    }

    fun getLocation() : Location? {
        return mLocation
    }

    fun setLocation(location: Location?) {
        mLocation = location
    }
}