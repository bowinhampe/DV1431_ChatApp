package com.dv1431_chatapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.dv1431_chatapp.database.ChatGroup
import com.dv1431_chatapp.database.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_group.*

class CreateGroupActivity : AppCompatActivity() {

    private var mGroupListView: ListView? = null
    private var mGroupAdapter: UserListAdapter? = null
    private lateinit var mUserList: ArrayList<String>
    private lateinit var mUserIds: ArrayList<String>
    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        initiateGUIComponents()
        mUserIds = ArrayList()
        mUser = intent.getSerializableExtra(User::class.java.simpleName) as User
        mUserIds.add(mUser.getId())
    }

    // conny tar en edtxt string, skickar upp o kollar om den finns, och då lägger han den i mUserList, sedan skapar ni grp med
    //grp name och alla users i listen, för dom är guaranteed finnas

    private fun addUser(userId: String) {
        mUserIds.add(userId)
        // TODO: Check if user exist and use the ADD below, Else write a "Toast"
        mGroupAdapter!!.add(userId)
    }

    private fun initiateGUIComponents(){
        mUserList = ArrayList<String>()
        mGroupListView = createGroupActivity_usersInGroup_listView
        mGroupAdapter = UserListAdapter(this, createGroupActivity_usersInGroup_listView.id, mUserList)
        mGroupListView!!.adapter = mGroupAdapter
        createGroupActivity_addUser_btn.setOnClickListener{
            addUser("ozZ2ZOPZQMVUj23vkprvv7sIHjz1")
        }
        createGroupActivity_createGrp_btn.setOnClickListener {
            createGroup()
        }
    }

    private fun createGroup() {
        val group = ChatGroup()
        group.setName(createGroupActivity_grpName_edtxt.text.toString())
        group.setUsers(mUserIds)

        // Generate a unique reference as group id
        val groupsRef = FirebaseDatabase.getInstance().getReference("groups")
        val newGroupRef = groupsRef.push()

        // Add group to the generated group id
        newGroupRef.setValue(group)

        val groupId = HashMap<String, Any>()
        groupId.put(newGroupRef.key, "N/A")

        // Add groupId to assigned users
        val usersRef = FirebaseDatabase.getInstance().getReference("users")
        mUserIds.forEach {
            usersRef.child(it).child("groups").updateChildren(groupId)
        }
    }

}