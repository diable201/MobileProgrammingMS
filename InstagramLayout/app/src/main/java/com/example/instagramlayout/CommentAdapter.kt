package com.example.instagramlayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommentAdapter(private val comments: List<Comment>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.username)
        val messageTextView: TextView = itemView.findViewById(R.id.message)
        val replyTextView: TextView = itemView.findViewById(R.id.reply)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.usernameTextView.text = comment.username
        holder.messageTextView.text = comment.message
        if (comment.reply != null) {
            holder.replyTextView.visibility = View.VISIBLE
            holder.replyTextView.text = comment.reply
        } else {
            holder.replyTextView.visibility = View.GONE
        }
    }

    override fun getItemCount() = comments.size
}

