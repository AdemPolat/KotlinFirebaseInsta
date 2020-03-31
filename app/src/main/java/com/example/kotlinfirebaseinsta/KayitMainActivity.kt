package com.example.kotlinfirebaseinsta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class KayitMainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kayit_main)

        auth = FirebaseAuth.getInstance()

        val currenUser = auth.currentUser

        if (currenUser != null){

            val intent = Intent(applicationContext,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun giris(view : View){


        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    fun signUpClicked(view : View){

        val email = useremailText.text.toString()
        val password = useremailPassword.text.toString()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->

            if(task.isSuccessful) {
                Toast.makeText(this, "Başarıyla kaydoldunuz.", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            if (exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(), Toast.LENGTH_LONG)
            }
        }

    }
}
