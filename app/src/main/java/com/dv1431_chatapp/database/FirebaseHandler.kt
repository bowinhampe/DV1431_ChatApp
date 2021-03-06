package com.dv1431_chatapp.database

import com.dv1431_chatapp.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirebaseHandler {

    companion object {
        private val mFirebaseHandler: FirebaseHandler by lazy {
            FirebaseHandler()
        }

        fun getInstance(): FirebaseHandler {
            return mFirebaseHandler
        }
    }

    // Used for logging
    private val TAG = LoginActivity::class.java.simpleName as String

    private val mAuthentication = FirebaseAuth.getInstance()
    private val mDatabase = FirebaseDatabase.getInstance()

    fun register(email: String, password: String, listener: OnCompleteListener<AuthResult>) {
        mAuthentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener)
    }

    fun signIn(email: String, password: String, listener: OnCompleteListener<AuthResult>) {
        mAuthentication.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener)
    }

    fun signOut() {
        mAuthentication.signOut()
    }

    fun insertData(databaseReference: String, data: Any?) {
        if (data != null)
            mDatabase.getReference(databaseReference).setValue(data)
        else
            mDatabase.getReference(databaseReference).setValue("N/A")
    }

    fun updateData(databaseReference: String, data: Map<String, Any?>) {
        mDatabase.getReference(databaseReference).updateChildren(data)
    }

    fun createRef(databaseReference: String) : DatabaseReference {
        return mDatabase.getReference(databaseReference).push()
    }

    fun retrieveDataOnce(databaseReference: String, listener: ValueEventListener) {
        mDatabase.getReference(databaseReference)
                .addListenerForSingleValueEvent(listener)
    }

    fun retrieveChildData(databaseReference: String, listener: ChildEventListener) {
        mDatabase.getReference(databaseReference)
                .addChildEventListener(listener)
    }

    fun getReference(databaseReference: String) : DatabaseReference {
        return mDatabase.getReference(databaseReference)
    }

    fun retrieveQueryDataOnce(query: Query, listener: ValueEventListener) {
        query.addListenerForSingleValueEvent(listener)
    }

    // TODO: Test if faster than retrieveDataOnce
    fun retrieveUser(userId: String, listener: ValueEventListener){
        mDatabase.getReference("users")
                .orderByKey()
                .equalTo(userId)
                .addListenerForSingleValueEvent(listener)
    }

    fun getCurrentUserId() : String? {
        return mAuthentication.currentUser?.uid
    }

    fun createGroup(group: Group) {
        val ref = createRef("groups")
        ref.setValue(group)
        group.setId(ref.key)

        val groupId = RelationMap(group.getId(), "N/A")
        group.getMembers().forEach {
            // Add group members under members reference
            if (it.getLastMessage() != null)
                insertData("members/"+group.getId()+"/"+it.getId(), it)
            else
                insertData("members/"+group.getId()+"/"+it.getId(), "N/A")

            // Add group to user
            updateData("users/"+it.getId()+"/groups", groupId)
        }
    }

    fun addUserListenerByEmail(email: String, listener: ValueEventListener) {
        mFirebaseHandler.getReference("users")
                .orderByChild("email")
                .equalTo(email)
                .addListenerForSingleValueEvent(listener)
    }

}