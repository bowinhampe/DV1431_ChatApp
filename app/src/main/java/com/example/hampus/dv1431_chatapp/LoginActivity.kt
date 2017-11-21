package com.example.hampus.dv1431_chatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeGUIComponents()
    }

    private fun initializeGUIComponents() {
        val btnLogin = findViewById<Button>(R.id.loginActivity_login_btn)
        btnLogin.setOnClickListener {
            // Start the main app activity
            login()
        }
        val mBtnRegister = findViewById<Button>(R.id.loginActivity_register_btn);
        mBtnRegister.setOnClickListener {
            // Start a register Fragment
            register()
        }
    }

    private fun login() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun register(){

    }
}
