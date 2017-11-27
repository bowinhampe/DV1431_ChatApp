package com.dv1431_chatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*
import android.widget.Toast
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    // For logging
    private val TAG = RegisterActivity::class.java.simpleName as String

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initiateGUIComponents()
        mAuth = FirebaseAuth.getInstance()
    }

    private fun initiateGUIComponents(){
        registerActivity_register_btn.setOnClickListener {
            // Start a register Fragment
            register()
        }
    }

    private fun register() {
        val email = registerActivity_email_edtxt.text.toString()
        val password = registerActivity_pw_edtxt.text.toString()
        val username = registerActivity_usrname_edtxt.text.toString()

        //validateCredentials()

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        val fbUser = mAuth.currentUser
                        if (fbUser != null) {
                            val user = User(fbUser.uid, email, username)
                            addUserToDatabase(user)
                            val intent = Intent(this, MainActivity::class.java)
                            //intent.putExtra(MainActivity.EXTRAS_USER_ID, user.getId())
                            intent.putExtra(User::class.java.simpleName, user)
                            startActivity(intent)
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure")
                            Toast.makeText(this, "An error occurred. Please try again.",
                                    Toast.LENGTH_LONG).show()
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun addUserToDatabase(user: User) {
        val dbUsersRef = FirebaseDatabase.getInstance().getReference("users")
        dbUsersRef.child(user.getId()).setValue(user)
    }

}
