package com.dv1431_chatapp

import android.content.Context
import android.graphics.Color
import android.location.Location
import android.location.LocationListener

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dv1431_chatapp.database.Message
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import com.google.maps.android.ui.IconGenerator
import com.google.android.gms.maps.model.BitmapDescriptorFactory





class MapFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // Map objects
    private lateinit var mMap: GoogleMap
    private var mClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLastLocation: Location? = null
    private var mCurrentLocationMarker: Marker? = null
    private val mFragmentManager = SupportMapFragment()

    private lateinit var mIconGenerator:  IconGenerator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIconGenerator = IconGenerator(context)
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
        val markerOptions = MarkerOptions()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(userName)))
        markerOptions.position(position)
        markerOptions.anchor(iconGenerator.anchorU, iconGenerator.anchorV)
        markerOptions.snippet(message)
        mMap.addMarker(markerOptions)?.showInfoWindow()
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            mMap = map

            // Add user locations
            addIcon(mIconGenerator, "User", "Message", LatLng(-33.9360, 151.2070))
        }
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
