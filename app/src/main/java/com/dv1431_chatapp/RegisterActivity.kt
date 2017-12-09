package com.dv1431_chatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_register.*
import android.widget.Toast
import com.dv1431_chatapp.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    // For logging
    private val TAG = RegisterActivity::class.java.simpleName as String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initiateGUIComponents()
    }

    private fun initiateGUIComponents() {
        createGroupActivity_back_btn.setOnClickListener( { finish() })

        registerActivity_register_btn.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val email = registerActivity_email_edtxt.text.toString()
        val password = registerActivity_pw_edtxt.text.toString()
        val username = registerActivity_usrname_edtxt.text.toString()

        //validateCredentials()

        val context = this

        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            val user = User(userId, email, username)
                            addUserToDatabase(user)
                            val intent = Intent(context, MainActivity::class.java)
                            intent.putExtra(User::class.java.simpleName, user)
                            startActivity(intent)
                        }
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun addUserToDatabase(user: User) {
        FirebaseDatabase.getInstance().getReference("usersTest").child(user.getId()).setValue(user)
    }

}
