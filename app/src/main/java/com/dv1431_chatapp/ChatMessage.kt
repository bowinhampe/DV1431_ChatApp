package com.dv1431_chatapp

/**
 * Created by hampus on 2017-11-24.
 */
class ChatMessage {
     var mMsg : String
     var mUsr : String

    constructor() {
        mMsg = "N/A"
        mUsr = "N/A"
    }

    constructor(msg: String,  usr: String) {
        mMsg = msg
        mUsr = usr
    }
}