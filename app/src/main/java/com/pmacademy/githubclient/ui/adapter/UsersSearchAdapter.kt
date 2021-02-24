package com.pmacademy.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.UsersSearchResponce
import com.pmacademy.githubclient.databinding.TestItemBinding

class UsersSearchAdapter : RecyclerView.Adapter<PostsViewHolder>()  {
    lateinit var binding: TestItemBinding


    private val items = mutableListOf<UsersSearchResponce>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
       return PostsViewHolder(
           LayoutInflater.from(parent.context).inflate(
               R.layout.test_item, parent, false
           )
       )
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val article = items[position]
        holder.tv_test1?.text = article.name
    }
    fun updateAdapter(newList: List<UsersSearchResponce>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

}

 class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
     var tv_test1: TextView? = null

     init {
         tv_test1 = itemView?.findViewById(R.id.tv_test1)
     }


 }