package com.dv1431_chatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import android.util.Log
import android.widget.Toast


class LoginActivity : AppCompatActivity() {

    // For logging
    private val TAG = LoginActivity::class.java.simpleName as String

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initiateGUIComponents()

        mAuth = FirebaseAuth.getInstance()
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
    }

    private fun login() {
        val email = loginActivity_usrname_edtxt.text.toString()
        val password = loginActivity_pw_edtxt.text.toString()

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val userId = mAuth.currentUser?.uid
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra(MainActivity.EXTRAS_USER_ID, userId)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_LONG).show()
                    }
        }
    }

    private fun register(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
