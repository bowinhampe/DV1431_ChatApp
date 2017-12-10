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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    // For logging
    private val TAG = LoginActivity::class.java.simpleName as String


    private val mFirebaseHandler = FirebaseHandler.getInstance()

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

        mFirebaseHandler.login(email, password, OnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithEmail:success")
                val userId = mFirebaseHandler.getAuth().currentUser?.uid
                if (userId != null) retrieveUserFromDatabase(userId)
            } else {
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun retrieveUserFromDatabase(userId: String) {
        val context = this

        mFirebaseHandler.retrieveDataOnce("usersTest/"+userId, object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                // TODO: Log and toast error
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val user = dataSnapshot?.getValue<User>(User::class.java)
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
        })
    }

    private fun register(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
