package com.dv1431_chatapp

import android.content.Context
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ProviderQueryResult
import kotlinx.android.synthetic.main.activity_create_group.*
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import android.support.annotation.NonNull
import android.R.attr.password
import com.google.android.gms.tasks.OnCompleteListener


class CreateGroup : AppCompatActivity() {

    private var mGroupListView: ListView? = null
    private var mGroupAdapter: UserListAdapter? = null
    lateinit var mUserList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        initiateGUIComponents()
    }

    private fun addUserToList(userEmail: String){
        // TODO: Check if user exist and use the ADD below, Else write a "Toast"
        if (mGroupAdapter!!.getPosition(userEmail) < 0)
            mGroupAdapter!!.add(userEmail)

    }
    private fun initiateGUIComponents(){
        mUserList = ArrayList<String>()
        mGroupListView = createGroupActivity_usersInGroup_listView
        mGroupAdapter = UserListAdapter(this, createGroupActivity_usersInGroup_listView.id, mUserList)
        mGroupListView!!.adapter = mGroupAdapter
        createGroupActivity_addUser_btn.setOnClickListener{
            findUser()
        }
    }

    private fun findUser() {
        val userEmail = createGroupActivity_userEmail_edtxt.text.toString()
        val auth = FirebaseAuth.getInstance()

        auth.fetchProvidersForEmail(userEmail).addOnCompleteListener(OnCompleteListener<ProviderQueryResult> { task ->
               if (task.isSuccessful) {
                   ///////// getProviders().size() will return size 1. if email ID is available.
                   if (task.result.providers!!.size == 1) {
                       addUserToList(userEmail)
                       println("user found")
                   } else
                       println("no user found")
               }
            })
    }







}