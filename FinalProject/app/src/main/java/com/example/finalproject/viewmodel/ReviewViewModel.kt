package com.example.finalproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Review
import com.example.finalproject.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    fun fetchReviews(productId: Int) {
        viewModelScope.launch {
            try {
                _reviews.value = repository.getReviewsByProduct(productId)
            } catch (e: Exception) {
                Log.e("ReviewViewModel", "Error fetching reviews", e)
            }
        }
    }

    fun addReview(review: Review, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.insertReview(review)
                fetchReviews(review.productId)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Failed to add review")
            }
        }
    }
}