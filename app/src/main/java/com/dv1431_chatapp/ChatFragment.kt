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
import com.dv1431_chatapp.database.Group
import com.dv1431_chatapp.database.Message
import com.dv1431_chatapp.database.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment:Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1:String? = null
    private var mParam2:String? = null
    private val mData = ArrayList<ChatMessage>()
    private var mChatAdapter : ChatMessageAdapter? = null
    private var mChatListView : ListView? = null

    private lateinit var mGroup: Group
    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1)
            mParam2 = getArguments().getString(ARG_PARAM2)
        }
    }

     override fun onStart() {
         super.onStart()

         //initializeChat()
         initiateGUIComponents()

         chatFragment_input_chatBar.setMessageBoxHint("Enter message...")
         chatFragment_input_chatBar.setSendClickListener() {
             var chatMsg = ChatMessage(true,chatFragment_input_chatBar.messageText)
             mChatAdapter!!.add(chatMsg)

             // println("DEBUG_Clickedbutton")
         }

     }

    fun initializeChat() {
        // TODO get message history of group chat
        //FirebaseDatabase.getInstance().getReference("group").child(mGroup.getId()).setValue(user)

        val retrieveMessageListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(dataSnapShot: DataSnapshot?, p1: String?) {
                val message = dataSnapShot?.getValue(Message::class.java)

                if (message != null) {
                    var leftSided = false

                    if (message.getUser() == mUser.getId()) {
                        leftSided = true
                    }

                    //mData.add(ChatMessage(leftSided, message.getMessage()))
                    mChatAdapter?.add(ChatMessage(leftSided, message.getMessage()))
                } else {
                    // TODO:
                }

            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        FirebaseDatabase.getInstance().getReference("groups").child(mGroup.getId()).child("messages").addChildEventListener(retrieveMessageListener)
    }

    fun initiateGUIComponents(){
        // Fetch and create List view for holding chat and its adapter
        mChatListView = chatFragment_msgWindow_listView
        mChatAdapter = ChatMessageAdapter(context, fragment_chat, mData)
        mChatListView!!.adapter = mChatAdapter

        // Initiate options
        mChatListView!!.transcriptMode = AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL
        mChatAdapter!!.registerDataSetObserver(listViewDataSetObserver())
    }

    inner class listViewDataSetObserver():DataSetObserver(){
        override fun onChanged() {
            mChatListView!!.setSelection(mChatAdapter!!.count-1)
            //println(mChatAdapter!!.count-1)

            super.onChanged()
        }
    }

    override fun onCreateView(inflater:LayoutInflater?, container:ViewGroup?, savedInstanceState:Bundle?):View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_chat, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri:Uri) {

    }

    public override fun onAttach(context:Context?) {
        super.onAttach(context)
    }

    public override fun onDetach() {
        super.onDetach()
    }

    fun setGroup(group:Group) {
        mGroup = group
    }

    fun setUser(user:User) {
        mUser = user
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        // TODO: Rename and change types and number of parameters
        fun newInstance(param1:String, param2:String):ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.setArguments(args)

            return fragment
        }
    }
 }// Required empty public constructor
