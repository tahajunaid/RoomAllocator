package com.example.roomallocator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dash.*


class DashB : AppCompatActivity() {
    var ref=FirebaseDatabase.getInstance().getReference()
    var ref2=FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dash)
        var current_user_email = FirebaseAuth.getInstance().getCurrentUser()?.email
        p_email.setText(current_user_email)

        p_email.setKeyListener(null);
        p_email.setCursorVisible(false);
        p_email.setPressed(false);
        p_email.setFocusable(false);

        btn_add_room.setOnClickListener{
            Toast.makeText(
                baseContext, "Please Wait...Your room is being added",
                Toast.LENGTH_LONG
            ).show()
            dbentry()
        }
        btn_back.setOnClickListener{
            startActivity(Intent(this, UserDashboard::class.java))
            finish()
        }

    }
    private fun dbentry(){
        val name =p_name.text.toString().trim()
        val phone =p_phone.text.toString().trim()
        val email=p_email.text.toString().trim()
        val regno=p_reg.text.toString().trim()
        val id1: Int = rg_1.checkedRadioButtonId
        val id2: Int = rg_2.checkedRadioButtonId
        var s1:String=""
        var s2:String=""
        var flag=1
        if(name.isEmpty()){
            p_name.error="Mandatory Field"
        flag=0}



        if(regno.isEmpty())
        { p_reg.error="Mandatory Field"
        flag=0}

        if (id1!=-1){
            val radio1: RadioButton = findViewById(id1)
            s1=" ${radio1.text}"
        }

        if (id2!=-1){
            val radio2: RadioButton= findViewById(id2)
            s2=" ${radio2.text}"
        }

        if(s1.isEmpty()||s2.isEmpty())
            flag=0

        ref2.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                    Toast.makeText(
                        baseContext,
                        "You have already created a room.Contact Admin to modify/delete your room",
                        Toast.LENGTH_LONG
                    ).show()
                }
                    else{
                    if(flag==1) {
                        val user = ref.push().key.toString()

                        val entries = DbEntry(user, name, email, phone, regno, s1, s2)

                        ref.child(user).setValue(entries).addOnCompleteListener {
                            Toast.makeText(
                                baseContext, "New Room Added !",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            baseContext, "Incomplete Mandatory Fields!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    }
                }

        })



    }


}




