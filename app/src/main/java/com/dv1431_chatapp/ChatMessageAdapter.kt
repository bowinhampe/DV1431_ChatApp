package com.dv1431_chatapp

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * Created by hampus on 2017-11-24.
 */
class ChatMessageAdapter(private val getContext: Context, private val chatMessageID: Int, private val messages: ArrayList<ChatMessage> )
    :ArrayAdapter<ChatMessage>(getContext, chatMessageID, messages)
{
    override fun add(msg: ChatMessage?) {
        messages.add(msg!!)
        super.add(msg)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val chatMsg = messages[position]
        var row = convertView
        var Holder: ViewHolder
        if(row == null) {
            val inflater = (getContext as Activity).layoutInflater

            if (chatMsg.left) {
                row = inflater!!.inflate(R.layout.right_chat_element, parent, false)
            } else {
                row = inflater!!.inflate(R.layout.left_chat_element, parent, false)
            }

            Holder = ViewHolder()
            Holder.msg = row!!.findViewById<TextView>(R.id.msgr) as TextView

            row.setTag(Holder)
        }
        else{
            Holder = row.getTag() as ViewHolder
        }

        Holder.msg!!.setText(chatMsg.msg)

        return row
    }
    class ViewHolder
    {
        internal var msg : TextView? = null
    }
    override fun getViewTypeCount(): Int {
        return super.getViewTypeCount()
    }
}