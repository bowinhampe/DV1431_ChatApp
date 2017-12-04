package com.dv1431_chatapp.database

import com.google.firebase.database.Exclude
import android.R.attr.keySet



class IdMap : HashMap<String, Any> {

    constructor() {

    }

    constructor(key : String, value: Any) {
        this.put(key, value)
    }

    @Exclude
    fun getFirst() : String {
        return ArrayList(this.keys)[0]
    }

    @Exclude
    fun getLast() : String {
        return ArrayList(this.keys)[this.size - 1]
    }

    @Exclude
    fun toList() : ArrayList<String> {
        return ArrayList(this.keys)
    }

}