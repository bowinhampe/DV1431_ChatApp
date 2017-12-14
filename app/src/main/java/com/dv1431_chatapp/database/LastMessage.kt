package com.dv1431_chatapp.database

import com.google.android.gms.maps.model.LatLng

/**
 * Created by dane on 12/14/17.
 */

class LastMessage : Message {
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

    fun getLocation() : Location? {
        return mLocation
    }

    fun setLocation(location: Location?) {
        mLocation = location
    }
}