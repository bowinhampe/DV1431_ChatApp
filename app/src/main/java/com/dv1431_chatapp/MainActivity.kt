package com.dv1431_chatapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    companion object {
        val EXTRAS_USER_ID = "userId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val userId: String? = if (intent.extras != null) intent.extras.getString(EXTRAS_USER_ID) else null
        val user: User = intent.getSerializableExtra(User::class.java.simpleName) as User
    }
}
