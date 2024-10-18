package com.example.assignment_2.data

import com.example.assignment_2.models.Post
import com.example.assignment_2.models.User

object MockData {
    private val animeImageUrls = listOf(
        "https://cdn.pixabay.com/photo/2024/03/03/12/42/ai-generated-8610368_1280.png",
        "https://cdn.pixabay.com/photo/2024/04/16/21/11/ai-generated-8700789_1280.jpg",
        "https://cdn.pixabay.com/photo/2024/04/11/07/49/ai-generated-8689398_1280.png",
        "https://cdn.pixabay.com/photo/2024/04/08/10/27/ai-generated-8683187_1280.png",
        "https://cdn.pixabay.com/photo/2024/06/17/16/39/girl-8836068_1280.jpg"
    )

    val currentUser = User(
        id = 1,
        username = "diable201",
        profileImageUrl = animeImageUrls[1],
        bio = "Anime & Kotlin Enjoyer",
        postsCount = 10,
        posts = listOf()
    )

    val posts = List(10) { index ->
        Post(
            id = index + 1,
            user = currentUser,
            imageUrl = animeImageUrls[index % animeImageUrls.size],
            caption = "Caption for anime post $index",
            likes = index * 10
        )
    }

    val otherUsers = listOf(
        User(id = 2, username = "limitonlythesky", profileImageUrl = animeImageUrls[4]),
        User(id = 3, username = "ernurator", profileImageUrl = animeImageUrls[2]),
        User(id = 4, username = "Sens3ii", profileImageUrl = animeImageUrls[3])
    )

    init {
        currentUser.posts = posts
    }
}



