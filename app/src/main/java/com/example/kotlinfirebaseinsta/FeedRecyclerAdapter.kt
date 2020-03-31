package com.example.kotlinfirebaseinsta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.lang.NullPointerException

class FeedRecyclerAdapter(private val userEmailArray:ArrayList<String>,private val userCommentArray: ArrayList<String>,private val userImageArray: ArrayList<String>,private  val userTimeArray:ArrayList<String>) : RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_row,parent,false)
        return PostHolder(view)


    }

    override fun getItemCount(): Int {
        return  userEmailArray.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.recyclerText?.text = userEmailArray[position]
        holder.recyclerCommentText?.text = userCommentArray[position]
        holder.recyclerTimeView?.text = userTimeArray[position]
        Picasso.get().load(userImageArray[position]).into(holder.recyclerImageView)


    }

    class PostHolder (view: View): RecyclerView.ViewHolder(view){

        var recyclerText: TextView? = null
        var recyclerCommentText: TextView? = null
        var recyclerImageView: ImageView? = null
        var recyclerTimeView : TextView? = null

        init {
            recyclerText = view.findViewById(R.id.recyclerIdText)
            recyclerCommentText = view.findViewById(R.id.recyclerCommentText)
            recyclerImageView = view.findViewById(R.id.recyclerImageView)
            recyclerTimeView = view.findViewById(R.id.recyclerTimeView)
        }


    }
}