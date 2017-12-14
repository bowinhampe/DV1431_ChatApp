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
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_chat.*
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import android.location.LocationManager
import com.dv1431_chatapp.database.*
import com.google.android.gms.maps.model.LatLng


class ChatFragment:Fragment() {

    val GPS_TIME_INTERVAL: Long = 0
    val GPS_MOVEMENT_INTERVAL: Float = 10f

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

        // Acquire a reference to the system Location Manager
        mLocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Define a listener that responds to location updates
        mLocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // Called when a new location is found by the network location provider.
                if(mOldLocation == null){
                    mCurrentLocation = location
                    mOldLocation = mCurrentLocation
                }
                else {
                    mOldLocation = mCurrentLocation
                    mCurrentLocation = location
                }

                // Updates user location in database. Had to be done here because I think this task is done asynchronously.
                mFirebaseHandler.insertData("members/"+mGroup?.getId()+"/"+mUser?.getId()+"/location", LatLng(location.latitude, location.longitude))

                println("Get_Location_Stopped")
                mLocationManager!!.removeUpdates(mLocationListener)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                println("GPS_LOCATION: status changed ")
            }

            override fun onProviderEnabled(provider: String) {
                println("GPS_LOCATION: Provider enabled ")
            }

            override fun onProviderDisabled(provider: String) {
                println("GPS_LOCATION: Provider "+ provider + " disabled ")
            }
        }
    }


    private fun getLocation(){
        // Register the listener with the Location Manager to receive location updates
        try {
            if(mLocationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                mLocationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_TIME_INTERVAL, GPS_MOVEMENT_INTERVAL, mLocationListener)
            }
            else if(mLocationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                mLocationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, GPS_TIME_INTERVAL, GPS_MOVEMENT_INTERVAL, mLocationListener)
            }
            println("Get_Location_Registered")
        }
        catch (e: SecurityException){
            println("Get_Location_Exception:" + e.toString())
        }

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


