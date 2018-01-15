package com.dv1431_chatapp

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dv1431_chatapp.database.FirebaseHandler
import com.dv1431_chatapp.database.Group
import com.dv1431_chatapp.database.LastMessage
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.maps.android.ui.IconGenerator
import java.util.*
import kotlin.collections.HashMap


class MapFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // Map objects
    private lateinit var mMap: GoogleMap
    private var mClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null
    private val mFragmentManager = SupportMapFragment()

    private val mFirebaseHandler = FirebaseHandler.getInstance()

    private val mMessages = HashMap<String, LastMessage>()
    private val bmpOpts = BitmapFactory.Options()

    private lateinit var mBmpMarker: Bitmap
    private lateinit var mGroup: Group
    private val mMarkers = HashMap<String, Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bmpOpts.inScaled = false
        mBmpMarker = BitmapFactory.decodeResource(resources, R.drawable.icon_marker, bmpOpts).copy(Bitmap.Config.ARGB_8888, true)

        mGroup = arguments.getSerializable("mGroup") as Group
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val mapView = mapFragment_GoogleMapHolder
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_map, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    private fun addIcon(iconGenerator: IconGenerator, userName: String, message: String, position: LatLng) {
        val markerOptions = MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(userName[0].toString())))
                .position(position)
                .anchor(iconGenerator.anchorU, iconGenerator.anchorV)
                .title(userName)
                .snippet(message)

        mMap.addMarker(markerOptions)
    }

    private fun addMapMarker(message: LastMessage) {
        // Draws the first letter in the username on the bitmap
        val color = Paint()
        color.textSize = 36f
        color.color = Color.BLACK

        val tempBmp = mBmpMarker.copy(mBmpMarker.config, true)
        val canvas = Canvas(tempBmp)
        canvas.drawText(message.getUser()[0].toString(), 36f, 48f, color)

        var latlngArray = ArrayList<LatLng>()
        val latitude = message.getLocation()?.getLatitude()
        val longitude = message.getLocation()?.getLongitude()
        if (latitude != null && longitude != null) {
            val location = LatLng(latitude, longitude)
            // Set the marker options
            val markerOptions = MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(tempBmp))
                    .anchor(0.5f, 1f)
                    .position(location)
                    .title(message.getUser())
                    .snippet(message.getMessage())

            //Remove old marker
            var marker = mMarkers.get(message.getUserId())
            if (marker != null)
                marker.remove()

            marker = mMap.addMarker(markerOptions)
            mMarkers.put(message.getUserId(), marker)
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            mMap = map

            println("onMapReady")

            retrieveLastMessages()

            // TODO: Lägg till user location på rad 136 "MyLocation"
            var userId = mFirebaseHandler.getCurrentUserId()
            val myMarker = mMarkers[userId]
            if(myMarker != null) {
                val myLocation = myMarker!!.position
                this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 10f))
                println(myLocation.latitude)
                println(myLocation.longitude)
            }
        }
    }

    private fun retrieveLastMessages() {
        mFirebaseHandler.retrieveChildData("members/"+mGroup.getId(), object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot?, previousChildKey: String?) {
                val data = dataSnapshot?.value
                if (data !is String) {
                    dataSnapshot?.children?.forEach {
                        val message = it.getValue(LastMessage::class.java)
                        if (message != null) {
                            message.setUserId(dataSnapshot.key)
                            addMapMarker(message)
                            mMessages.put(message.getId(), message)
                        }
                    }
                }
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, previousChildKey: String?) {
                val data = dataSnapshot?.value
                if (data !is String) {
                    dataSnapshot?.children?.forEach {
                        val newMessage = it.getValue(LastMessage::class.java)
                        if (newMessage != null) {
                            newMessage.setUserId(dataSnapshot.key)
                            addMapMarker(newMessage)
                            mMessages.put(newMessage.getId(), newMessage)
                        }
                    }
                }
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                // TODO: Remove group from list
            }
        })
    }

    private fun initiateZoomOnStart(){

    }

    @Synchronized protected fun buildGoogleApiClient(){
        mClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mClient!!.connect()
    }

    override fun onLocationChanged(p0: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()

        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

    }
}// Required empty public constructor
