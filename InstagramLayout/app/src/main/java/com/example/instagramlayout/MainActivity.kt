package com.example.instagramlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<TextView>(R.id.profileUsername)
        username.text = "diable201"

        val postDescription = findViewById<TextView>(R.id.postDescription)
        postDescription.text = "Hey everybody, I just started learning Kotlin!"

        val comments = listOf(
            Comment(
                username = "Sens3ii",
                message = "Wow! Kotlin looks so cool. I want to try it myself!",
                reply = "diable201: You'll love it, trust me!"
            ),
            Comment(
                username = "limitonlythesky",
                message = "This is amazing! Kotlin's syntax seems so clean."
            ),
            Comment(
                username = "weeebdev",
                message = "Kotlin is the future of mobile development!"
            )
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CommentAdapter(comments)
    }
}
