package com.dv1431_chatapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
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

class MainActivity : AppCompatActivity() {
    //lateinit var mGroupList: ArrayList<String>
    private lateinit var mUser: User
    private var mGroups: ArrayList<Group> = ArrayList()
    var mSelectedGroupNumber = -1

    private val mFirebaseHandler = FirebaseHandler.getInstance()

    private val mRetrieveGroupListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get group from user's group list
            val group = dataSnapshot.getValue<Group>(Group::class.java)
            if (group != null) {
                group.setId(dataSnapshot.key)
                mGroups.add(group)
                // RE-paint the buttons
                // TODO: should not repaint after every group add
                initiateGUIComponents()
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
        mFirebaseHandler.retrieveChildData("usersTest/"+mUser.getId()+"/groups", object : ChildEventListener {
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
                        mFirebaseHandler.retrieveDataOnce("groupsTest/"+groupId, mRetrieveGroupListener)
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

    /*private fun retrieveGroup(groupId: String) {
        FirebaseDatabase.getInstance().getReference("groupsTest").child(groupId).addListenerForSingleValueEvent(mRetrieveGroupListener)
    }*/

    private fun initiateGroupList() {
        // TODO: limit listener to groups where the user has access to
        for (grp in mUser.getGroups()) {
            // Add listener for every group the user is in
            //retrieveGroup(grp.key)
            mFirebaseHandler.retrieveDataOnce("groupsTest/"+grp.key, mRetrieveGroupListener)
        }
    }

    private fun initiateGUIComponents() {
        createGroupActivity_back_btn.setOnClickListener( { finish() })

        val groupListView = mainActivity_grp_listView
        val groupNames = ArrayList<String>()

        mGroups.forEach {
            groupNames.add(it.getName())
        }

        groupListView.adapter = groupListAdapter(this, groupNames)
        groupListView.onItemClickListener = groupListItemClickListener()
        mainActivity_create_grp_btn.setOnClickListener {
            initiateCreateGroup()
        }
    }

    inner class groupListItemClickListener() : AdapterView.OnItemClickListener{
        override fun onItemClick(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            startGroupInteractionActivity(pos)
        }
    }

    fun startGroupInteractionActivity(groupPos: Int){
        // TODO: Use group "position" from listView click to create a chat window with the specified group.
        // EXAMPLE CODE
        val selectedGroup = mGroups.get(groupPos)

        val intent = Intent(this, GroupInteractionActivity::class.java)
        intent.putExtra(User::class.java.simpleName, mUser)
        intent.putExtra(Group::class.java.simpleName, selectedGroup)
        startActivity(intent)
    }

    inner class groupListAdapter(context: Context, groupList: ArrayList<String>) : BaseAdapter() {

        private var groupList: ArrayList<String>
        private val mInflator: LayoutInflater

        init {
            this.groupList = groupList
            this.mInflator = LayoutInflater.from(context)
        }

        override fun getCount(): Int {
            return groupList.size
        }

        override fun getItem(position: Int): Any {
            return groupList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view: View?
            val vh: ListRowHolder
            if (convertView == null) {
                view = this.mInflator.inflate(R.layout.grouplist_row, parent, false)
                vh = ListRowHolder(view)
                view.tag = vh
            } else {
                view = convertView
                vh = view.tag as ListRowHolder
            }

            vh.label.text = groupList[position]
            return view
        }
    }

    inner class ListRowHolder(row: View?) {
        public val label: TextView
        init {
            this.label = row?.findViewById<TextView>(R.id.label) as TextView
        }
    }
}
