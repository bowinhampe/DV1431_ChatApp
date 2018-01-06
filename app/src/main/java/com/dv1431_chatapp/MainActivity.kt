package com.dv1431_chatapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.dv1431_chatapp.database.FirebaseHandler
import com.dv1431_chatapp.database.Group
import com.dv1431_chatapp.database.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.karumi.dexter.Dexter
import kotlinx.android.synthetic.main.activity_main.*
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener
import com.google.firebase.database.ChildEventListener
import kotlinx.android.synthetic.main.activity_create_group.*

class MainActivity : AppCompatActivity() {
    private lateinit var mUser: User
    private var mGroups: ArrayList<Group> = ArrayList()
    private var mGroupNames: ArrayList<String> = ArrayList()
    private var mGroupListAdapter: GroupListAdapter? = null
    var mSelectedGroupNumber = -1

    private val mFirebaseHandler = FirebaseHandler.getInstance()

    private val mGroupListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get group from user's group list
            val group = dataSnapshot.getValue<Group>(Group::class.java)
            if (group != null) {
                group.setId(dataSnapshot.key)
                mGroups.add(group)
                // RE-paint the buttons
                // TODO: should not repaint after every group add
                mGroupListAdapter!!.add(group.getName())
            } else {
                // TODO: Toast an error occurred
            }

        }

        override fun onCancelled(error: DatabaseError) {
            // TODO: database error
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mUser = intent.getSerializableExtra(User::class.java.simpleName) as User
        initiateGroupList()
        addUserGroupsEventListener()
        requestPermission()
        initiateGUIComponents()
    }

    private fun addUserGroupsEventListener() {
        mFirebaseHandler.retrieveChildData("users/"+mUser.getId()+"/groups", object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, previousChildKey: String?) {
                val groupId = dataSnapshot?.key
                if (groupId != null) {
                    if (!mUser.getGroups().containsKey(groupId)) {
                        mFirebaseHandler.retrieveDataOnce("groups/"+groupId, mGroupListener)
                    }
                } else {
                    println("NULL")
                }
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                // TODO: Remove group from list
            }
        })
    }

    private fun requestPermission(){
        val dialogMultiplePermissionsListener = DialogOnAnyDeniedMultiplePermissionsListener.Builder
                .withContext(this)
                .withTitle("GPS permission")
                .build()
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION
                        ,Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(dialogMultiplePermissionsListener)
                .check()
    }

    private fun initiateCreateGroup() {
        val intent = Intent(this, CreateGroupActivity::class.java)
        intent.putExtra(User::class.java.simpleName, mUser)

        startActivity(intent)
    }

    private fun initiateGroupList() {
        // TODO: limit listener to groups where the user has access to
        // Add listener for every group the user is in
        for (grp in mUser.getGroups()) {
            mFirebaseHandler.retrieveDataOnce("groups/"+grp.key, mGroupListener)
        }
    }

    private fun initiateGUIComponents() {
        activityMain_back_btn.setOnClickListener( { finish() })
        mGroups.forEach {
            mGroupNames.add(it.getName())
        }
        mGroupListAdapter = GroupListAdapter(this, mainActivity_grp_listView.id, mGroupNames)
        mainActivity_grp_listView.adapter = mGroupListAdapter
        mainActivity_create_grp_btn.setOnClickListener {
            initiateCreateGroup()
        }
    }

    private fun leaveGroup(groupName: String){
        println(groupName)
        var groupId = ""
        var pos = 0
        for(i in mGroups.indices){
            if(groupName.equals(mGroups[i].getName())) {
                groupId = mGroups[i].getId()
                pos = i
            }
        }

        mGroups.removeAt(pos)
        /*mGroups.forEach {
            if(groupName.equals(it.getName())) {
                groupId = it.getId()
            }
        }*/


        var fireBaseDbRefRemoval = mFirebaseHandler.getReference("users/"+mUser.getId()+"/groups/"+groupId)
        fireBaseDbRefRemoval.removeValue()

        fireBaseDbRefRemoval = mFirebaseHandler.getReference("members/"+groupId+"/"+mUser.getId())
        fireBaseDbRefRemoval.removeValue()

        mGroupListAdapter!!.remove(groupName)
    }


    fun startGroupInteractionActivity(groupPos: Int){
        // TODO: Use group "position" from listView click to create a chat window with the specified group.
        // EXAMPLE CODE
        val intent = Intent(this, GroupInteractionActivity::class.java)
        intent.putExtra(User::class.java.simpleName, mUser)
        intent.putExtra(Group::class.java.simpleName, mGroups[groupPos])
        startActivity(intent)
    }

    inner class GroupListAdapter(private val getContext: Context, private val userListID: Int, private val groups: ArrayList<String> )
        :ArrayAdapter<String>(getContext, userListID, groups)
    {
        override fun getCount(): Int {
            return groups.size
        }
        override fun add(group: String?) {
            super.add(group)
        }

        override fun remove(group: String?) {
            super.remove(group)
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val group = groups[position]
            var row = convertView

            val inflater = (getContext as Activity).layoutInflater

            row = inflater!!.inflate(R.layout.grouplist_row, parent, false)

            var groupTextView = row!!.findViewById<TextView>(R.id.label_group)
            groupTextView.text = group

            groupTextView.setOnClickListener { startGroupInteractionActivity(position) }

            var removeGrpButton = row!!.findViewById<Button>(R.id.btn_leave_grp)
            removeGrpButton.setOnClickListener{
                leaveGroup(group)
            }

            return row
        }
        override fun getViewTypeCount(): Int {
            return super.getViewTypeCount()
        }
    }


}
