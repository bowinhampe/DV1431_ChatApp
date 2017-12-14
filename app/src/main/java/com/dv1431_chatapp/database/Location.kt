package com.dv1431_chatapp.database

import com.google.android.gms.maps.model.LatLng

/**
 * Created by dane on 12/14/17.
 */
class Location {
    private var mLatitude: Double?
    private var mLongitude: Double?

    constructor() {
        mLatitude = null
        mLongitude = null
    }

    constructor(latitude: Double, longitude: Double) {
        mLatitude = latitude
        mLongitude = longitude
    }

    fun getLatitude() : Double? {
        return  mLatitude
    }

    fun setLatitude(latitude: Double) {
        mLatitude = latitude
    }

    fun getLongitude() : Double? {
        return mLongitude
    }

    fun setLongitude(longitude: Double) {
        mLongitude = longitude
    }

    /*fun asLatLng() : LatLng {
        return LatLng(mLatitude, mLongitude)
    }*/
}