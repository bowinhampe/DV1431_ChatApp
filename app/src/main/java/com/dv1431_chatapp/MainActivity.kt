package com.dv1431_chatapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.dv1431_chatapp.database.User
import com.karumi.dexter.Dexter
import kotlinx.android.synthetic.main.activity_main.*
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener

class MainActivity : AppCompatActivity() {
    lateinit var mGroupList: Array<String>
    var mSelectedGroupNumber = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val user: User = intent.getSerializableExtra(User::class.java.simpleName) as User
        requestPermission()
        initiateGroupList()
        initiateGUIComponents()
    }


    private fun requestPermission(){
        val dialogMultiplePermissionsListener = DialogOnAnyDeniedMultiplePermissionsListener.Builder
                .withContext(this)
                .withTitle("GPS permission")
                .build()
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION
                                ,Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(dialogMultiplePermissionsListener)
                .check()
    }

    private fun initiateCreateGroup(){
        val intent = Intent(this, CreateGroup::class.java)
        startActivity(intent)
    }

    private fun initiateGroupList() {
        // TODO: Hardcorded
        mGroupList = arrayOf("Test1", "Test2", "Test3")
    }

    private fun initiateGUIComponents() {
        val groupListView = mainActivity_grp_listView
        groupListView.adapter = groupListAdapter(this, mGroupList)
        groupListView.onItemClickListener = groupListItemClickListener()
        mainActivity_create_grp_btn.setOnClickListener{
            initiateCreateGroup()
        }
    }

    inner class groupListItemClickListener() : AdapterView.OnItemClickListener{
        override fun onItemClick(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            startGroupInteractionActivity(pos)
        }
    }

    fun startGroupInteractionActivity(groupPos: Int){
        // TODO: Use group "position" from listView click to create a chat window with the specified group.
        // EXAMPLE CODE
        var specifiedGroup = mGroupList.get(groupPos)
        intent.putExtra("groupName", specifiedGroup)

        val intent = Intent(this, GroupInteractionActivity::class.java)
        startActivity(intent)
    }

    inner class groupListAdapter(context: Context, groupList: Array<String>) : BaseAdapter() {

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

    inner class ListRowHolder(row: View?) {
        public val label: TextView
        init {
            this.label = row?.findViewById<TextView>(R.id.label) as TextView
        }
    }
}
