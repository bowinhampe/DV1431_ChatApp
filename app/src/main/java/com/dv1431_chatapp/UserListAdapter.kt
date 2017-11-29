package com.dv1431_chatapp

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UserListAdapter(private val getContext: Context, private val userListID: Int, private val users: ArrayList<String> )
    :ArrayAdapter<String>(getContext, userListID, users)
{
    override fun getCount(): Int {
        return users.size
    }
    override fun add(user: String?) {
        super.add(user)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val user = users[position]
        var row = convertView

        val inflater = (getContext as Activity).layoutInflater

        row = inflater!!.inflate(R.layout.userlist_row, parent, false)

        var userTextView = row!!.findViewById<TextView>(R.id.label_user)
        userTextView.text = user

        return row
    }
    override fun getViewTypeCount(): Int {
        return super.getViewTypeCount()
    }
}
