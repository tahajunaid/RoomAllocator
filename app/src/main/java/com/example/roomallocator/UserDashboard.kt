package com.example.roomallocator

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import java.util.Arrays
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dashboard.*


class UserDashboard : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var ref:DatabaseReference
    lateinit var roomlist:MutableList<DbEntry>
    lateinit var roomListView:ListView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
       adView.loadAd(adRequest)

        roomlist= mutableListOf()
        roomListView=findViewById(R.id.rooms_list_view)
        ref=FirebaseDatabase.getInstance().getReference()
        auth = FirebaseAuth.getInstance()
        btn_sign_out.setOnClickListener {
            auth.signOut()
            Toast.makeText(
                baseContext, "Signed Out.",
                Toast.LENGTH_LONG
            ).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        btn_ad_room.setOnClickListener{
            startActivity(Intent(this, DashB::class.java))
            finish()
        }
        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    roomlist.clear()
                    for (i in p0.children){
                        val room=i.getValue((DbEntry::class.java))
                        roomlist.add(room!!)
                    }
                    val retrieveData=DbRetrieve(applicationContext,R.layout.data_retrieve,roomlist )
                    roomListView.adapter=retrieveData
                }
            }

        })

    }
    public override fun onPause() {
        adView.pause()
        super.onPause()
    }

    // Called when returning to the activity
    public override fun onResume() {
        super.onResume()
        adView.resume()
    }

    // Called before the activity is destroyed
    public override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }




}