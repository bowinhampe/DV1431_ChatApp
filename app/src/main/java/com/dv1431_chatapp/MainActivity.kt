package com.dv1431_chatapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId: String? = if (intent.extras != null) intent.extras.getString(LoginActivity.EXTRAS_USER_ID) else null
    }
}
