package com.dv1431_chatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*
import android.widget.Toast
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
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
            register()
        }
    }

    private fun register() {
        val email = registerActivity_email_edtxt.text.toString()
        val password = registerActivity_pw_edtxt.text.toString()
        val username = registerActivity_usrname_edtxt.text.toString()

        //validateCredentials()

        val context = this

        Database().register(email, password, username, object : OnCompleteListener{
            override fun onStart() {

            }

            override fun onSuccess(task: Task<AuthResult>) {
                val user = User(task.result.user.uid, email, username)
                addUserToDatabase(user)
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra(User::class.java.simpleName, user)
                startActivity(intent)
            }

            override fun onFail(task: Task<AuthResult>) {
                Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun addUserToDatabase(user: User) {
        val usersRef = FirebaseDatabase.getInstance().getReference("users")
        usersRef.child(user.getId()).setValue(user)
    }

}
