package com.dv1431_chatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.dv1431_chatapp.database.DatabaseHandler
import com.dv1431_chatapp.database.OnCompleteListener
import com.dv1431_chatapp.database.OnDataChangeListener
import com.dv1431_chatapp.database.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot

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
        val email = loginActivity_usrname_edtxt.text.toString()
        val password = loginActivity_pw_edtxt.text.toString()

        val context = this

        DatabaseHandler().login(email, password, object : OnCompleteListener {
            override fun onStart() {
                //TODO: Make this work!
                mProgressBar.visibility = View.VISIBLE
            }

            override fun onSuccess(task: Task<AuthResult>) {
                retrieveUserFromDatabase(task.result.user.uid)
            }

            override fun onFail(task: Task<AuthResult>) {
                Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun retrieveUserFromDatabase(userId: String) {
        val context = this

        DatabaseHandler().retrieveDataOnce("users/"+userId, object : OnDataChangeListener {
            override fun onStart() {
            }

            override fun onChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue<User>(User::class.java)//.getValue(User::class.java)
                if (user != null) {
                    user.setId(dataSnapshot.key)
                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra(User::class.java.simpleName, user)
                    mProgressBar.visibility = View.GONE
                    startActivity(intent)
                } else {
                    Log.w(TAG, "retrieveUserFromDatabase:failure")
                    Toast.makeText(context, "An error occurred. PLease try again.",
                            Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //DO SOME THING WHEN GET DATA FAILED HERE
            }
        })
    }

    private fun register(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
