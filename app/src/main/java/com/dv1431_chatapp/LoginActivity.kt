package com.dv1431_chatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.dv1431_chatapp.database.FirebaseHandler
import com.dv1431_chatapp.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    // For logging
    private val TAG = LoginActivity::class.java.simpleName as String

    private lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initiateGUIComponents()
    }

    private fun initiateGUIComponents(){
        loginActivity_login_btn.setOnClickListener {
            // Start the main app activity
            login()
        }
        loginActivity_register_btn.setOnClickListener {
            // Start a register Fragment
            register()
        }

        mProgressBar = ProgressBar(this)
        mProgressBar.isIndeterminate = true
    }

    private fun login() {
        mProgressBar.visibility = View.VISIBLE

        val email = loginActivity_usrname_edtxt.text.toString()
        val password = loginActivity_pw_edtxt.text.toString()

        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        val userId = auth.currentUser?.uid
                        if (userId != null) retrieveUserFromDatabase(userId)
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun retrieveUserFromDatabase(userId: String) {
        val userRef = FirebaseDatabase.getInstance().getReference("usersTest").child(userId)

        val context = this
        val retrieveUserListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue<User>(User::class.java)
                if (user != null) {
                    user.setId(dataSnapshot.key)
                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra(User::class.java.simpleName, user)
                    mProgressBar.visibility = View.GONE
                    startActivity(intent)
                } else {
                    Log.w(TAG, "retrieveUserFromDatabase:failure")
                    mProgressBar.visibility = View.GONE
                    Toast.makeText(context, "An error occurred. Please try again.",
                            Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // TODO: Log and toast error
            }
        }

        userRef.addListenerForSingleValueEvent(retrieveUserListener)
    }

    private fun register(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
