package com.dv1431_chatapp

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_group_interaction.*

class GroupInteractionActivity : AppCompatActivity() {

    // Variable used to check wether map or chat is the active choice for the mode-button
    var mFragmentMode = false
    var groupName = "Empty"
    val mFragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_interaction)

        // Todo: Catch groupname better
        if (intent.extras != null){
            groupName = intent.extras.getString("groupName")
        }

        initializeGUIComponents()
        initiateChatFragment()
    }

    private fun initializeGUIComponents(){
        groupInteraction_activity_groupName_txtView.text = groupName
        groupInteraction_activity_changeMode_btn.setOnClickListener{
            if(!mFragmentMode){
                mFragmentMode = true
                groupInteraction_activity_changeMode_btn.setBackgroundColor(Color.BLUE)
                initiateChatFragment()
            }
            else{
                mFragmentMode = false
                groupInteraction_activity_changeMode_btn.setBackgroundColor(Color.GREEN)
                initiateMapFragment()
            }
        }
    }
    private fun initiateMapFragment(){

    }
    private fun initiateChatFragment(){
        val transaction = mFragmentManager.beginTransaction()
        val fragment = ChatFragment()
        transaction.replace(R.id.groupInteraction_activity_mainFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
