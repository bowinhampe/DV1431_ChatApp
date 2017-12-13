package com.dv1431_chatapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dv1431_chatapp.database.*
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_create_group.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

class CreateGroupActivity : AppCompatActivity() {
    private lateinit var mUserAdapter: UserListAdapter
    private lateinit var mUserList: ArrayList<String>
    private lateinit var mMembers: ArrayList<Member>
    private lateinit var mUser: User

    private lateinit var mRetrieveUserListener: ValueEventListener

    private val mFirebaseHandler = FirebaseHandler.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        initiateGUIComponents()
        mUser = intent.getSerializableExtra(User::class.java.simpleName) as User
        mMembers = ArrayList()
        mMembers.add(Member(mUser.getId(), "N/A"))

        val context = this
        mRetrieveUserListener = object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot?.value != null) {
                    dataSnapshot.children.forEach {
                        val user = it.getValue(User::class.java)
                        if (user != null) {
                            user.setId(it.key)
                            mUserList.add(user.getEmail())
                            mMembers.add(Member(user.getId(), "N/A"))
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
        mFirebaseHandler.addUserListenerByEmail(email, mRetrieveUserListener)
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
        val group = Group()
        group.setName(createGroupActivity_grpName_edtxt.text.toString())
        group.setMembers(mMembers)

        mFirebaseHandler.createGroup(group)

        finish()
    }

}