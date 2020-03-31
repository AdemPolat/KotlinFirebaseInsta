package com.example.kotlinfirebaseinsta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.exp

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val currenUser = auth.currentUser

        if (currenUser != null){

            val intent = Intent(applicationContext,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun signInClick(view : View){

        if (useremailText.text.toString().isNotEmpty() && useremailPassword.text.toString().isNotEmpty()){

            auth.signInWithEmailAndPassword(useremailText.text.toString(),useremailPassword.text.toString()).addOnCompleteListener {  task ->

                if (task.isSuccessful){

                    Toast.makeText(applicationContext,"Welcome: ${auth.currentUser?.email.toString()}",Toast.LENGTH_LONG)

                    val intent = Intent(applicationContext,FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception ->

                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG)

            }

        }else{
            Toast.makeText(this@MainActivity,"Boş alamları doldurunuz",Toast.LENGTH_SHORT)
        }


    }



    fun kayit(view : View){


                val intent = Intent(applicationContext,KayitMainActivity::class.java)
                startActivity(intent)
                finish()

    }

}
