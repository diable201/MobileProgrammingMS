package com.example.finalproject.repository

import android.util.Log
import com.example.finalproject.dao.CartDao
import com.example.finalproject.dao.ReviewDao
import com.example.finalproject.di.AppDatabase
import com.example.finalproject.model.CartItem
import com.example.finalproject.model.Category
import com.example.finalproject.model.Order
import com.example.finalproject.model.Product
import com.example.finalproject.model.Review
import com.example.finalproject.model.User
import com.example.finalproject.network.ApiService
import com.example.finalproject.network.WebSocketClient
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDatabase,
    private val webSocketClient: WebSocketClient,
    private val cartDao: CartDao,
    private val reviewDao: ReviewDao,
    private val orderManager: OrderManager,
) {
    private val userDao = db.userDao()
    private val productDao = db.productDao()
    private val categoryDao = db.categoryDao()
    private val orderDao = db.orderDao()

    suspend fun registerUser(user: User) = userDao.insertUser(user)

    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)

    suspend fun getCategories(): List<Category> {
        val categories = apiService.getCategories()
        categoryDao.insertCategories(categories)
        return categories
    }

    suspend fun getProducts(): List<Product> {
        return try {
            val products = apiService.getProducts()
            productDao.insertProducts(products)
            products
        } catch (e: Exception) {
            Log.e("Repository", "Network error: ${e.message}")
            productDao.getProducts()
        }
    }

    suspend fun getProductsByCategory(categoryName: String): List<Product> {
        return productDao.getProductsByCategory(categoryName)
    }

    suspend fun getProductById(productId: Int): Product? {
        return productDao.getProductById(productId)
    }


    suspend fun getOrdersByUser(userId: Int): List<Order> {
        return orderDao.getOrdersByUser(userId)
    }

    suspend fun getReviewsByProduct(productId: Int): List<Review> {
        return reviewDao.getReviewsByProduct(productId)
    }

    suspend fun insertReview(review: Review) {
        reviewDao.insertReview(review)
    }

    suspend fun getCartItems(cartId: Int): List<CartItem> {
        return cartDao.getCartItems(cartId)
    }

    suspend fun getCartItemByProductId(cartId: Int, productId: Int): CartItem? {
        return cartDao.getCartItems(cartId).find { it.productId == productId }
    }

    suspend fun insertCartItem(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }

    suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem)
    }

    suspend fun deleteCartItem(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    suspend fun clearCart(cartId: Int) {
        cartDao.getCartItems(cartId).forEach {
            cartDao.deleteCartItem(it)
        }
    }

    fun insertOrder(order: Order) {
        orderManager.addOrder(order)
    }

    fun getOrdersByUserId(userId: Int): List<Order> {
        return orderManager.getOrders().filter { it.userId == userId }
    }

}
