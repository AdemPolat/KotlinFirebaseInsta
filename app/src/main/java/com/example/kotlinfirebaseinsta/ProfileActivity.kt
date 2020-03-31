package com.example.kotlinfirebaseinsta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.recycler_view_row.*

class ProfileActivity : AppCompatActivity() {

    private  lateinit var auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore

    var useremailFromFB : ArrayList<String> = ArrayList()
    var userCommentFromFB : ArrayList<String> = ArrayList()
    var userImageFromFB : ArrayList<String> = ArrayList()
    var userTimeFromDB: ArrayList<String> = ArrayList()


    var adapter : FeedRecyclerAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)




        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        profileAd.text =("Kullanıcı Adı   \n" + auth.currentUser?.email.toString())

        getDataFromFirestore()



            var layoutManager = LinearLayoutManager(this)
            recyclerView2.layoutManager = layoutManager



            adapter = FeedRecyclerAdapter(
                useremailFromFB,
                userCommentFromFB,
                userImageFromFB,
                userTimeFromDB
            )

            recyclerView2.adapter = adapter




    }

    fun imgProfile(view: View){

        val intent = Intent(applicationContext,FeedActivity::class.java)
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

    override fun onBackPressed() {

        val intent = Intent(applicationContext,FeedActivity::class.java)
        startActivity(intent)
        finish()

        super.onBackPressed()
    }
    fun getDataFromFirestore(){

        db.collection("Posts").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("useremail","${auth.currentUser?.email}").addSnapshotListener { snapshot, exception ->
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

        val builder = AlertDialog.Builder(this)
        builder.setTitle("App background color") // Display a message on alert dialog
        builder.setMessage("Are you want to set the app background color to RED?")

        builder.setPositiveButton("YES"){dialog, which ->
            Toast.makeText(applicationContext,"Ok, we change the app background.",Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No"){dialog,which ->
            Toast.makeText(applicationContext,"You are not agree.",Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    fun deleteFromFirestore(){



    }



}
