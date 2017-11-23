package com.dv1431_chatapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.dv1431_chatapp.R.layout.grouplist_row
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var groupList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userId: String? = if (intent.extras != null) intent.extras.getString(LoginActivity.EXTRAS_USER_ID) else null
        initiateGroupList()
        initiateGUIComponents()
    }

    private fun initiateGroupList() {
        // TODO: Hardcorded
        groupList = arrayOf("Test1", "Test2", "Test3")
    }

    private fun initiateGUIComponents() {
        val groupListView = mainActivity_grp_listView
        groupListView.adapter = groupListAdapter(this, groupList)
        groupListView.onItemClickListener = groupListItemClickListener()

    }

    private class groupListItemClickListener() : AdapterView.OnItemClickListener{

        override fun onItemClick(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            // TODO: Implement the specific group-interaction window to be started
            println(pos)
        }
}

    private class groupListAdapter(context: Context, groupList: Array<String>) : BaseAdapter() {

        private var groupList: Array<String>
        private val mInflator: LayoutInflater

        init {
            this.groupList = groupList
            this.mInflator = LayoutInflater.from(context)
        }

        override fun getCount(): Int {
            return groupList.size
        }

        override fun getItem(position: Int): Any {
            return groupList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view: View?
            val vh: ListRowHolder
            if (convertView == null) {
                view = this.mInflator.inflate(R.layout.grouplist_row, parent, false)
                vh = ListRowHolder(view)
                view.tag = vh
            } else {
                view = convertView
                vh = view.tag as ListRowHolder
            }

            vh.label.text = groupList[position]
            return view
        }
    }

    private class ListRowHolder(row: View?) {
        public val label: TextView
        init {
            this.label = row?.findViewById<TextView>(R.id.label) as TextView
        }
    }
}
