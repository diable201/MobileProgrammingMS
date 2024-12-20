package com.example.finalproject.network

import com.example.finalproject.model.Category
import com.example.finalproject.model.Product
import com.example.finalproject.model.Review
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/categories")
    suspend fun getCategories(): List<Category>

    @GET("/products")
    suspend fun getProducts(): List<Product>

    @GET("/reviews/{productId}")
    suspend fun getReviews(@Path("productId") productId: Int): List<Review>

    @POST("/reviews")
    suspend fun addReview(@Body review: Review): Review
}