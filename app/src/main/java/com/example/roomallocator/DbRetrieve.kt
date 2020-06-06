package com.example.roomallocator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.data_retrieve.*

class DbRetrieve(val mCtx:Context,val layoutResId: Int,val rooms:List<DbEntry>):ArrayAdapter<DbEntry>(mCtx,layoutResId,rooms)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
  val view:View=layoutInflater.inflate(layoutResId,null)
        val txt1:TextView=view.findViewById(R.id.txt1)
        val name_txt:TextView=view.findViewById(R.id.name_txt)
        val email_txt:TextView=view.findViewById(R.id.email_txt)
        val phone_txt:TextView=view.findViewById((R.id.phone_txt))
        val reg_txt:TextView=view.findViewById(R.id.reg_txt)
        val room=rooms[position]
        txt1.text=room.roomtype1+" "+room.roomtype2
        name_txt.text=room.name
        email_txt.text=room.email
        reg_txt.text=room.regno
        phone_txt.text=room.phone

        return view
    }
}