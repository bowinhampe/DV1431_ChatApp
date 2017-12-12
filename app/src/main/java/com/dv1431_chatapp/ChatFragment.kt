package com.dv1431_chatapp

import android.content.Context
import android.database.DataSetObserver
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
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
import java.util.*
import android.location.LocationManager
import com.dv1431_chatapp.database.FirebaseHandler


class ChatFragment:Fragment() {

    val GPS_TIME_INTERVAL: Long = 0
    val GPS_MOVEMENT_INTERVAL: Float = 0.0f

    private var mGroup: Group? = null
    private var mUser: User? = null
    private var mChatListView: ListView? = null
    private var mCurrentLocation: Location? = null
    private var mOldLocation: Location? = null
    private var mLocationListener: LocationListener? = null
    private var mLocationManager: LocationManager? = null

    private val mFirebaseHandler = FirebaseHandler.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments == null) {
            println("arg null")
        }
        val args = arguments
        mGroup = args.getSerializable("mGroup") as Group?
        mUser = args.getSerializable("mUser") as User?
    }


    private fun getLocation(){
        // TODO: THIS NEEDS TO WORK, somehow LOCATIONS doesnt update.
        // Register the listener with the Location Manager to receive location updates
        try {
            mLocationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, GPS_TIME_INTERVAL, GPS_MOVEMENT_INTERVAL, mLocationListener)
        }
        catch (e: SecurityException){
            println("Get_Location_Exception:" + e.toString())
        }

        /*if(mOldLocation == null || mCurrentLocation == null){
            println("null af")
        }
        else {
            if (mOldLocation!!.latitude != mCurrentLocation!!.latitude) {
                locationManager.removeUpdates(locationListener)
            }
        }*/

    }

    override fun onStart() {
        super.onStart()

        initiateGUIComponents()

        chatFragment_input_chatBar.setMessageBoxHint("Enter message...")
        chatFragment_input_chatBar.setSendClickListener {
            val message = Message()
            message.setUser(mUser!!.getUsername())
            message.setMessage(chatFragment_input_chatBar.messageText)

            mFirebaseHandler.createRef("messages/"+mGroup!!.getId())
                    .setValue(message)

            getLocation()
        }

    }


    fun initiateGUIComponents() {
        // Fetch and create List view for holding chat and its adapter
        mChatListView = chatFragment_msgWindow_listView

        val fireBaseDataBaseRef = mFirebaseHandler.getReference("messages/"+mGroup!!.getId())
        val fireBaseAdapter = object : FirebaseListAdapter<Message>(activity, Message::class.java,
                R.layout.message, fireBaseDataBaseRef) {
            override fun populateView(v: View, model: Message, position: Int) {
                // Get references to the views of message.xml
                val messageText = v.findViewById<TextView>(R.id.message_text) as TextView
                val messageUser = v.findViewById<TextView>(R.id.message_user) as TextView

                // Set their text
                messageText.setText(model.getMessage())
                messageUser.setText(model.getUser())

                // TODO: SET messageUser to real username somehow (above this line)
            }
        }
        mChatListView!!.adapter = fireBaseAdapter
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


