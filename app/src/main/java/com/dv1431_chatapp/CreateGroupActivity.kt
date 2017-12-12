package com.dv1431_chatapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dv1431_chatapp.database.ChatGroup
import com.dv1431_chatapp.database.FirebaseHandler
import com.dv1431_chatapp.database.RelationMap
import com.dv1431_chatapp.database.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_create_group.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

class CreateGroupActivity : AppCompatActivity() {
    private lateinit var mUserAdapter: UserListAdapter
    private lateinit var mUserList: ArrayList<String>
    private lateinit var mUserIds: RelationMap
    private lateinit var mUser: User

    private lateinit var mRetrieveUserIdListener: ValueEventListener

    private val mFirebaseHandler = FirebaseHandler.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        initiateGUIComponents()
        mUser = intent.getSerializableExtra(User::class.java.simpleName) as User
        mUserIds = RelationMap()
        mUserIds.put(mUser.getId(), true)

        val context = this
        mRetrieveUserIdListener = object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot?.value != null) {
                    dataSnapshot.children.forEach {
                        mUserIds.put(it.key, true)
                        if (it.value is HashMap<*, *>) {
                            val hashMap = it.value as HashMap<String, String>
                            val email = hashMap.getValue("email")
                            if (!mUserList.contains(email))
                                mUserList.add(email)
                            else
                                Toast.makeText(context, "User already in group",
                                        Toast.LENGTH_LONG).show()
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
        val email = createGroupActivity_userEmail_edtxt.text.toString()

        // Query user email and if exists retrieve user id
        /*val userQueryRef = mFirebaseHandler.getReference("usersTest")
                .orderByChild("email")
                .equalTo(email)
        mFirebaseHandler.retrieveQueryDataOnce(userQueryRef, mRetrieveUserIdListener)*/
        mFirebaseHandler.retrieveUserEmail(email, mRetrieveUserIdListener)
    }

    private fun initiateGUIComponents(){
        createGroupActivity_back_btn.setOnClickListener( { finish() })

        mUserList = ArrayList()
        mUserAdapter = UserListAdapter(this, createGroupActivity_usersInGroup_listView.id, mUserList)
        createGroupActivity_usersInGroup_listView.adapter = mUserAdapter
        createGroupActivity_addUser_btn.setOnClickListener{
            addUser()
        }
        createGroupActivity_createGrp_btn.setOnClickListener {
            createGroup()
        }
        createGroupActivity_back_btn.setOnClickListener{
            finish()
        }
    }

    private fun createGroup() {
        val group = ChatGroup()
        group.setName(createGroupActivity_grpName_edtxt.text.toString())
        group.setMembers(mUserIds)

        mFirebaseHandler.createGroup(group)

        /*val group = ChatGroup()
        group.setName(createGroupActivity_grpName_edtxt.text.toString())
        group.setUsers(mUserIds)

        val ref = mFirebaseHandler.createRef("groupsTest")
        ref.setValue(group)

        val key = ref.key

        val groupId = RelationMap(key, true)
        mUserIds.forEach {
            mFirebaseHandler.updateData("usersTest/"+it.key+"/groups", groupId)
        }*/

        finish()
    }

}