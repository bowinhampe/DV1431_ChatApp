package com.dv1431_chatapp

import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

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
    }

    private fun login() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun register(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
