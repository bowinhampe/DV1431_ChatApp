package com.dv1431_chatapp.database

/**
 * Created by dane on 12/14/17.
 */

class LastMessage : Message {
    private var mLocation: Any

    constructor() : super() {
        mLocation = "N/A"
    }

    constructor(id: String, user: String, message: String, location: Any) : super(id, user, message) {
        mLocation = location
    }

    constructor(message: Message) {
        setId(message.getId())
        setUser(message.getUser())
        setMessage(message.getMessage())
        mLocation = "N/A"
    }

    fun getLocation() : Any {
        return mLocation
    }

    fun setLocation(location: Any) {
        mLocation = location
    }
}