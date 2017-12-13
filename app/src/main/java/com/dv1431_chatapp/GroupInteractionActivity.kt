package com.dv1431_chatapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dv1431_chatapp.database.FirebaseHandler
import com.dv1431_chatapp.database.Group
import com.dv1431_chatapp.database.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_group_interaction.*

class GroupInteractionActivity : AppCompatActivity() {

    // Variable used to check wether map or chat is the active choice for the mode-button
    var mFragmentMode = false
    val mFragmentManager = supportFragmentManager
    private lateinit var mUser: User
    private lateinit var mGroup: Group

    private val mFirebaseHandler = FirebaseHandler.getInstance()

    /*private val mRetrieveGroupMembersListener = object : ValueEventListener {
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
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_interaction)

        mUser = intent.getSerializableExtra(User::class.java.simpleName) as User
        mGroup = intent.getSerializableExtra(Group::class.java.simpleName) as Group

        //mFirebaseHandler.retrieveDataOnce("members/"+mGroup.getId(), mRetrieveGroupMembersListener)

        initializeGUIComponents()
        initiateChatFragment()
    }

    private fun initializeGUIComponents(){
        createGroupActivity_back_btn.setOnClickListener( { finish() })

        groupInteraction_activity_groupName_txtView.text = mGroup.getName()
        groupInteraction_activity_changeMode_btn.setOnClickListener{
            if(!mFragmentMode){
                mFragmentMode = true
                groupInteraction_activity_changeMode_btn.setBackgroundDrawable( getResources().getDrawable(R.drawable.icons_chat))
                initiateMapFragment()
            } else {
                mFragmentMode = false
                groupInteraction_activity_changeMode_btn.setBackgroundDrawable( getResources().getDrawable(R.drawable.icons_globe))
                initiateChatFragment()
            }
        }
    }

    private fun initiateMapFragment() {
        val transaction = mFragmentManager.beginTransaction()
        val fragment = MapFragment()

        transaction.replace(R.id.groupInteraction_activity_mainFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initiateChatFragment(){
        //val fragment = ChatFragment.newInstance(mGroup)
        val bundle = Bundle()
        bundle.putSerializable("mGroup", mGroup)
        bundle.putSerializable("mUser", mUser)
        val fragment = ChatFragment()
        fragment.arguments = bundle
        val transaction = mFragmentManager.beginTransaction()
        transaction.replace(R.id.groupInteraction_activity_mainFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
