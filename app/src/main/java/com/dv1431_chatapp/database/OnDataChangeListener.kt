package com.dv1431_chatapp.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

/**
 * Created by dane on 11/28/17.
 */

interface OnDataChangeListener {
    fun onStart()
    fun onChange(dataSnapshot: DataSnapshot)
    fun onCancelled(databaseError: DatabaseError)
}