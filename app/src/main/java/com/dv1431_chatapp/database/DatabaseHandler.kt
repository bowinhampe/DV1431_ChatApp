package com.dv1431_chatapp.database

import android.util.Log
import com.dv1431_chatapp.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DatabaseHandler {

    // For logging
    private val TAG = LoginActivity::class.java.simpleName as String

    fun register(email: String, password: String, username: String, listener: OnCompleteListener) {
        listener.onStart()

        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        listener.onSuccess(task)
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        listener.onFail(task)
                    }
                }
    }

    fun login(email: String, password: String, listener: OnCompleteListener) {
        listener.onStart()

        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        listener.onSuccess(task)
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        listener.onFail(task)
                    }
                }
    }

    fun retrieveDataOnce(dbReference: String, listener: OnDataChangeListener) {
        listener.onStart()

        val ref = FirebaseDatabase.getInstance().getReference(dbReference)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onChange(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                listener.onCancelled(databaseError)
            }
        }

        ref.addListenerForSingleValueEvent(valueEventListener)
    }
}