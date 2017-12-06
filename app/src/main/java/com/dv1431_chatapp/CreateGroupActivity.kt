package com.dv1431_chatapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dv1431_chatapp.database.ChatGroup
import com.dv1431_chatapp.database.IdMap
import com.dv1431_chatapp.database.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_create_group.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

class CreateGroupActivity : AppCompatActivity() {
    private lateinit var mUserAdapter: UserListAdapter
    private lateinit var mUserList: ArrayList<String>
    private lateinit var mUserIds: IdMap
    private lateinit var mUser: User

    private lateinit var mRetrieveUserIdListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        initiateGUIComponents()
        mUser = intent.getSerializableExtra(User::class.java.simpleName) as User
        mUserIds = IdMap()
        mUserIds.put(mUser.getId(), "N/A")

        val context = this
        mRetrieveUserIdListener = object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                if (snapshot?.value != null) {
                    snapshot.children.forEach {
                        mUserIds.put(it.key, "N/A")
                        if (it.value is HashMap<*, *>) {
                            val hashMap = it.value as HashMap<String, String>
                            mUserList.add(hashMap.getValue("email"))
                        }
                    }
                } else {
                    Toast.makeText(context, "User not found.",
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun addUser() {
        val userEmail = createGroupActivity_userEmail_edtxt.text.toString()

        // Query user email and if exists retrieve user id
        val userQueryRef = FirebaseDatabase.getInstance().getReference("users").orderByChild("email").equalTo(userEmail)
        userQueryRef.addListenerForSingleValueEvent(mRetrieveUserIdListener)
    }

    private fun initiateGUIComponents(){
        mUserList = ArrayList()
        mUserAdapter = UserListAdapter(this, createGroupActivity_usersInGroup_listView.id, mUserList)
        createGroupActivity_usersInGroup_listView.adapter = mUserAdapter
        createGroupActivity_addUser_btn.setOnClickListener{
            addUser()
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

        // Add groupId to assigned users
        val groupId = IdMap(newGroupRef.key, "N/A")
        val usersRef = FirebaseDatabase.getInstance().getReference("users")
        mUserIds.forEach {
            usersRef.child(it.key).child("groups").updateChildren(groupId)
        }
    }

}