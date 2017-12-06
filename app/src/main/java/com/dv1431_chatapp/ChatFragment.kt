package com.dv1431_chatapp

import android.content.Context
import android.database.DataSetObserver
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.dv1431_chatapp.R.layout.fragment_chat
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.database.FirebaseDatabase
import com.dv1431_chatapp.database.Group
import com.dv1431_chatapp.database.Message
import com.dv1431_chatapp.database.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_chat.*
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class ChatFragment:Fragment() {


    private val mData = ArrayList<ChatMessage>()
    private var mGroup: Group? = null
    private var mChatListView: ListView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments == null) {
            println("arg null")
        }
        val args = arguments
        mGroup = args.getSerializable("mGroup") as Group?
    }

    override fun onStart() {
        super.onStart()

        //initializeChat()
        initiateGUIComponents()

        chatFragment_input_chatBar.setMessageBoxHint("Enter message...")
        chatFragment_input_chatBar.setSendClickListener {
            val message = chatFragment_input_chatBar.messageText
            FirebaseDatabase.getInstance()
                    .reference
                    .push()
                    .setValue(ChatMessage(message,
                            FirebaseAuth.getInstance()
                                    .currentUser!!
                                    .displayName!!)
                    )
            // TODO : Something wrong here, possible ChatMessage class or Database message class
        }

    }


    fun initiateGUIComponents() {
        // Fetch and create List view for holding chat and its adapter
        mChatListView = chatFragment_msgWindow_listView

        val fireBaseDataBaseRef = FirebaseDatabase.getInstance().getReference("groups").child(mGroup!!.getId()).child("messages")
        val adapter = object : FirebaseListAdapter<ChatMessage>(activity, ChatMessage::class.java,
                R.layout.message, fireBaseDataBaseRef) {
            override fun populateView(v: View, model: ChatMessage, position: Int) {
                // Get references to the views of message.xml
                val messageText = v.findViewById<TextView>(R.id.message_text) as TextView
                val messageUser = v.findViewById<TextView>(R.id.message_user) as TextView

                // Set their text
                messageText.setText(model.mMsg)
                messageUser.setText(model.mUsr)
                // TODO: Doesnt really get any messages from group, check wether Id is correct when the instance is getted or smething bish
            }
        }
    }

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment
            return inflater!!.inflate(R.layout.fragment_chat, container, false)
        }

        // TODO: Rename method, update argument and hook method into UI event
        fun onButtonPressed(uri: Uri) {

        }

        public override fun onAttach(context: Context?) {
            super.onAttach(context)
        }

        public override fun onDetach() {
            super.onDetach()
        }


        companion object {

            fun newInstance(group: Group): ChatFragment {
                val fragment = ChatFragment()
                val args = Bundle()
                args.putSerializable("group", group)
                fragment.arguments = args
                return fragment
            }

        }
    }


