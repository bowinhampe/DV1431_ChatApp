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



class MapFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // Map objects
    private lateinit var mMap: GoogleMap
    private var mClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLastLocation: Location? = null
    private var mCurrentLocationMarker: Marker? = null
    private val mFragmentManager = SupportMapFragment()

    private val mFirebaseHandler = FirebaseHandler.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun addIcon(bmp: Bitmap, userName: String, message: String, position: LatLng) {
        // Draws the first letter in the username on the bitmap
        val color = Paint()
        color.textSize = 36f
        color.color = Color.BLACK

        val canvas = Canvas(bmp)
        canvas.drawText(userName[0].toString(), 36f, 48f, color)

        // Set the marker options
        val markerOptions = MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bmp))
                .anchor(0.5f, 1f)
                .position(position)
                .title(userName)
                .snippet(message)
        mMap.addMarker(markerOptions)
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            mMap = map

            // Load marker icon
            val bmpOpts = BitmapFactory.Options()
            bmpOpts.inScaled = false

            val bmp = BitmapFactory.decodeResource(resources, R.drawable.icon_marker, bmpOpts).copy(Bitmap.Config.ARGB_8888, true)

            addIcon(bmp, "User", "Message", LatLng(0.0, 0.0))

            // Using IconGenerator
            /*val iconGenerator = IconGenerator(context)
            iconGenerator.setBackground(ContextCompat.getDrawable(context, R.drawable.icon_marker))
            addIcon(iconGenerator, "User", "Message", LatLng(0.0, 0.0))*/
        }
    }

    /*private fun retrieveUserLocations() {
        mFirebaseHandler.retrieveChildData("members/"+mGroup?.getId()+"/"+mUser?.getId()+"/location", object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, previousChildKey: String?) {
                val groupId = dataSnapshot?.key
                if (groupId != null) {
                    if (!mUser.getGroups().containsKey(groupId)) {
                        mFirebaseHandler.retrieveDataOnce("groups/"+groupId, mGroupListener)
                    }
                } else {
                    println("NULL")
                }
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                // TODO: Remove group from list
            }
        })
    }*/

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
