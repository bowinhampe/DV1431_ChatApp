package com.dv1431_chatapp

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ChatMessageAdapter(private val getContext: Context, private val chatMessageID: Int, private val messages: ArrayList<ChatMessage> )
    :ArrayAdapter<ChatMessage>(getContext, chatMessageID, messages)
{
    override fun getCount(): Int {
        return messages.size
    }
    override fun add(msg: ChatMessage?) {
        super.add(msg)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val chatMsg = messages[position]
        var row = convertView

        val inflater = (getContext as Activity).layoutInflater

        if (chatMsg.left) {
            row = inflater!!.inflate(R.layout.right_chat_element, parent, false)
        } else {
            row = inflater!!.inflate(R.layout.left_chat_element, parent, false)
        }

        var chatText = row!!.findViewById<TextView>(R.id.msgr)
        chatText.text = chatMsg.msg

        println("DEBUG_GetViewCall")
        return row
    }
    override fun getViewTypeCount(): Int {
        return super.getViewTypeCount()
    }
}