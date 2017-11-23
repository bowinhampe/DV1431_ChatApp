package com.dv1431_chatapp

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_group_interaction.*

class GroupInteractionActivity : AppCompatActivity() {

    // Variable used to check wether map or chat is the active choice for the mode-button
    var mFragmentMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_interaction)
        initializeGUIComponents()
        initiateChatFragment()
    }

    private fun initializeGUIComponents(){
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

    }
}
