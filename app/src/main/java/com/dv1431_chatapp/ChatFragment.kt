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
import kotlinx.android.synthetic.main.fragment_chat.*


class ChatFragment:Fragment() {

 // TODO: Rename and change types of parameters
    private var mParam1:String? = null
    private var mParam2:String? = null
    private val mData = ArrayList<ChatMessage>()
    private var mChatAdapter : ChatMessageAdapter? = null
    private var mChatListView : ListView? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null)
        {
        mParam1 = getArguments().getString(ARG_PARAM1)
        mParam2 = getArguments().getString(ARG_PARAM2)
        }
    }

     override fun onStart() {
         super.onStart()
         //initializeChat()
         initiateGUIComponents()
         chatFragment_input_chatBar.setMessageBoxHint("Enter message...")
         chatFragment_input_chatBar.setSendClickListener(){
             println("DEBUG_Clickedbutton")
             var chatMsg = ChatMessage(true,chatFragment_input_chatBar.messageText)
             mChatAdapter!!.add(chatMsg)
         }

     }

    /*fun initializeChat(){
        // TODO Hardcoded Data
        var chatMsg = ChatMessage(true,"Hej")
        mData.add(chatMsg)
        chatMsg = ChatMessage(false ,"Hej fag")
        mData.add(chatMsg)
        chatMsg = ChatMessage(true ,"Rip life hooj :)")
        mData.add(chatMsg)
        chatMsg = ChatMessage(false ,"./Care.not")
        mData.add(chatMsg)
    }*/
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
            println(mChatAdapter!!.count-1)
            super.onChanged()
        }
    }

    override fun onCreateView(inflater:LayoutInflater?, container:ViewGroup?,
    savedInstanceState:Bundle?):View?
    {
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
