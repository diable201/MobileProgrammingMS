package com.example.assignment_2.viewmodel

import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment_2.data.MockData
import com.example.assignment_2.models.Comment
import com.example.assignment_2.models.NotificationItem
import com.example.assignment_2.models.Post
import com.example.assignment_2.models.User
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val _users = MutableLiveData(MockData.otherUsers + MockData.currentUser)
    val users: LiveData<List<User>> get() = _users

    private val _posts = MutableLiveData(MockData.posts)
    val posts: LiveData<List<Post>> get() = _posts

    private val _notifications = MutableLiveData<List<NotificationItem>>(emptyList())
    val notifications: LiveData<List<NotificationItem>> get() = _notifications

    val currentUser: User = MockData.currentUser
    private val _currentUserPostCount = MutableLiveData(0)

    private val userLiveDataMap: MutableMap<Int, MutableLiveData<User>> = mutableMapOf()

    init {
        updateCurrentUserPostCount()

        // Observe changes in posts to update the post count dynamically
        _posts.observeForever {
            updateCurrentUserPostCount()
        }
    }

    private fun updateCurrentUserPostCount() {
        val count = _posts.value?.count { it.user.username == currentUser.username } ?: 0
        _currentUserPostCount.value = count
        Log.d("MainViewModel", "Current user has $count posts.")
    }

    fun toggleLike(postId: Int) {
        _posts.value = _posts.value?.map { post ->
            if (post.id == postId) {
                val isLiked = !post.isLiked
                val likes = if (isLiked) post.likes + 1 else post.likes - 1
                if (isLiked) {
                    addNotification(
                        user = post.user,
                        message = "${post.user.username} liked your post.",
                        targetId = post.id
                    )
                }
                post.copy(isLiked = isLiked, likes = likes)
            } else {
                post
            }
        }
    }

    fun addPost(imageData: Bitmap, caption: String) {
        val newPost = Post(id = (_posts.value?.maxOfOrNull { it.id } ?: 0) + 1,
            user = MockData.currentUser,
            imageData = imageData,
            imageUrl = null,
            caption = caption,
            likes = 0,
            isLiked = false)
        Log.d("MainViewModel", "New Post: $newPost")

        val updatedPosts = listOf(newPost) + (_posts.value ?: emptyList())

        _posts.value = updatedPosts

        Log.d("MainViewModel", "Post added. Total posts: ${updatedPosts.size}")
    }

    fun addComment(postId: Int, commentText: String) {
        val currentUser = MockData.currentUser
        val timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val newComment = Comment(
            id = Random.nextInt(), user = currentUser, text = commentText, timestamp = timestamp
        )

        _posts.value = _posts.value?.map { post ->
            if (post.id == postId) {
                addNotification(
                    user = currentUser,
                    message = "${currentUser.username} commented on your post.",
                    targetId = post.id
                )
                post.copy(comments = post.comments + newComment)
            } else {
                post
            }
        }
    }

    fun searchUsers(query: String): LiveData<List<User>> {
        val result = MediatorLiveData<List<User>>()

        fun updateResult(users: List<User>, query: String) {
            val filteredUsers = if (query.isBlank()) {
                users
            } else {
                users.filter { it.username.contains(query, ignoreCase = true) }
            }
            result.value = filteredUsers
        }

        result.addSource(_users) { users ->
            updateResult(users, query)
        }

        return result
    }

    // Helper functions for ProfilePage
    fun getUserById(userId: Int): LiveData<User> {
        return userLiveDataMap.getOrPut(userId) {
            val user = if (userId == currentUser.id) {
                currentUser
            } else {
                _users.value?.find { it.id == userId } ?: currentUser
            }
            MutableLiveData(user)
        }
    }

    fun getPostCountForUser(userId: Int): LiveData<Int> {
        val result = MutableLiveData<Int>()
        result.value = _posts.value?.count { it.user.id == userId } ?: 0
        _posts.observeForever {
            result.value = it.count { post -> post.user.id == userId }
        }
        return result
    }

    fun getPostsForUser(userId: Int): LiveData<List<Post>> {
        val result = MutableLiveData<List<Post>>()
        result.value = _posts.value?.filter { it.user.id == userId } ?: emptyList()
        _posts.observeForever {
            result.value = it.filter { post -> post.user.id == userId }
        }
        return result
    }

    private fun addNotification(
        user: User, message: String, targetId: Int? = null
    ) {
        val timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val newNotification = NotificationItem(
            id = Random.nextInt(),
            user = user,
            message = message,
            timestamp = timestamp,
            targetId = targetId
        )
        _notifications.value = listOf(newNotification) + (_notifications.value ?: emptyList())
    }
}