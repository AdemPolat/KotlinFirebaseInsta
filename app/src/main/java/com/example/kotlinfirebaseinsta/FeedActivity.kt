package com.example.kotlinfirebaseinsta

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.recycler_view_row.*

class FeedActivity : AppCompatActivity() {

    private  lateinit var auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore

    var useremailFromFB : ArrayList<String> = ArrayList()
    var userCommentFromFB : ArrayList<String> = ArrayList()
    var userImageFromFB : ArrayList<String> = ArrayList()
    var userTimeFromDB: ArrayList<String> = ArrayList()


    var adapter : FeedRecyclerAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        textHesap.text =auth.currentUser?.email.toString()

        getDataFromFirestore()

        var layoutManager = LinearLayoutManager(this)
        recyclerView2.layoutManager = layoutManager

        adapter = FeedRecyclerAdapter(useremailFromFB,userCommentFromFB,userImageFromFB,userTimeFromDB)
        recyclerView2.adapter = adapter

    }

    fun imgProfile(view: View){

        val intent = Intent(applicationContext,ProfileActivity::class.java)
        startActivity(intent)
        finish()

    }

    fun imgAddPost(view: View){

        val intent = Intent(applicationContext,UploadActivity::class.java)
        startActivity(intent)
        finish()

    }

    fun imgSignOut(view: View){

        auth.signOut()
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    fun getDataFromFirestore(){

        db.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
            if (exception !=null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG)

            }else{
                if (snapshot != null){
                    if (!snapshot.isEmpty){

                        userImageFromFB.clear()
                        userCommentFromFB.clear()
                        useremailFromFB.clear()
                        userTimeFromDB.clear()




                        val documents = snapshot.documents
                        for (document in documents){
                            val comment = document.get("comment") as String
                            val userEmail = document.get("useremail") as String
                            val downloadUrl = document.get("downloadurl") as String
                            val timestamp = document.get("date") as Timestamp
                            val date = timestamp.toDate()



                            useremailFromFB.add(userEmail)
                            userCommentFromFB.add("- "+comment)
                            userImageFromFB.add(downloadUrl)
                            userTimeFromDB.add(date.toString())

                            adapter!!.notifyDataSetChanged()

                        }
                    }
                }
            }
        }
    }

    fun deletePost (view: View){

        db.collection("Posts").document("email").delete().addOnSuccessListener { void ->

            Toast.makeText(this,"Başarıyla temizlendi",Toast.LENGTH_SHORT)

        }.addOnFailureListener {

            Toast.makeText(this,"Temizlenmedi",Toast.LENGTH_SHORT)
        }



        val builder = AlertDialog.Builder(this)
        builder.setTitle("UYARI") // Display a message on alert dialog
        builder.setMessage("Gönderiyi silmek ister misiniz?")

        builder.setPositiveButton("EVET"){dialog, which ->
            Toast.makeText(applicationContext,"Gönderiyi sildiniz.",Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("HAYIR"){dialog,which ->
            Toast.makeText(applicationContext,"",Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
