package com.dv1431_chatapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_create_group.*

class CreateGroup : AppCompatActivity() {

    private var mGroupListView: ListView? = null
    private var mGroupAdapter: UserListAdapter? = null
    lateinit var mUserList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        initiateGUIComponents()
    }

    private fun addUserToList(){
        // TODO: Check if user exist and use the ADD below, Else write a "Toast"
        mGroupAdapter!!.add("TEST-USER@COOLIO")
    }
    private fun initiateGUIComponents(){
        mUserList = ArrayList<String>()
        mGroupListView = createGroupActivity_usersInGroup_listView
        mGroupAdapter = UserListAdapter(this, createGroupActivity_usersInGroup_listView.id, mUserList)
        mGroupListView!!.adapter = mGroupAdapter
        createGroupActivity_addUser_btn.setOnClickListener{
            addUserToList()
        }
    }


}