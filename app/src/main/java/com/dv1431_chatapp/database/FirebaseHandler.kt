package com.dv1431_chatapp.database

import com.dv1431_chatapp.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.Serializable

class FirebaseHandler : Serializable{

    companion object {

        // Used for logging
        private val TAG = LoginActivity::class.java.simpleName as String

        private val mAuth = FirebaseAuth.getInstance()

        fun login(email: String, password: String, listener: OnCompleteListener<AuthResult>) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener)
        }

        fun retrieveDataOnce(databaseReference: String, listener: ValueEventListener) {
            FirebaseDatabase.getInstance()
                    .getReference(databaseReference)
                    .addListenerForSingleValueEvent(listener)
        }

        // TODO: Test if faster than retrieveDataOnce
        fun retrieveUser(userId: String, listener: ValueEventListener){
            FirebaseDatabase.getInstance()
                    .getReference("usersTest")
                    .orderByKey()
                    .equalTo(userId)
                    .addListenerForSingleValueEvent(listener)
        }

        fun getAuth() : FirebaseAuth {
            return mAuth
        }

    }

}