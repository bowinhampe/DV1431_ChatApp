package com.dv1431_chatapp

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hampus.dv1431_chatapp.LoginFragment
import com.example.hampus.dv1431_chatapp.RegisterFragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*

class LoginActivity : AppCompatActivity() {

    val mManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initiateGUIComponents()
    }

    private fun initiateGUIComponents(){
        val transaction = mManager.beginTransaction()
        val loginFragment = LoginFragment()
        transaction.replace(loginActivity_mainLayout.id, loginFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
