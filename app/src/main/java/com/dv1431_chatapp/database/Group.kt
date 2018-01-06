package com.dv1431_chatapp.database

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.Exclude
import java.io.Serializable


open class Group : Serializable {
    private var mId: String
    private var mName: String
    private var mMembers: ArrayList<Member>

    constructor() {
        mId = "N/A"
        mName = "N/A"
        mMembers = ArrayList()
    }

    constructor(id: String, name: String, members: ArrayList<Member> = ArrayList()) {
        mId = id
        mName = name
        mMembers = members
    }

    @Exclude
    fun getId() : String {
        return mId
    }

    @Exclude
    fun setId(id: String) {
        mId = id
    }

    fun getName() : String {
        return mName
    }

    fun setName(name: String) {
        mName = name
    }

    @Exclude
    fun addMember(member: Member) {
        mMembers.add(member)
    }

    @Exclude
    fun removeMember(member: Member) {
        mMembers.remove(member)
    }

    @Exclude
    fun getMembers() : ArrayList<Member> {
        return mMembers
    }

    @Exclude
    fun setMembers(members: ArrayList<Member>) {
        mMembers = members
    }

}