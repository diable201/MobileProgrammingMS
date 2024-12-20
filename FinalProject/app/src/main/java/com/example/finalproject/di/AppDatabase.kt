package com.example.finalproject.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalproject.dao.CartDao
import com.example.finalproject.dao.CategoryDao
import com.example.finalproject.dao.OrderDao
import com.example.finalproject.dao.ProductDao
import com.example.finalproject.dao.ReviewDao
import com.example.finalproject.dao.UserDao
import com.example.finalproject.model.CartItem
import com.example.finalproject.model.Category
import com.example.finalproject.model.Order
import com.example.finalproject.model.OrderItem
import com.example.finalproject.model.Payment
import com.example.finalproject.model.Product
import com.example.finalproject.model.Review
import com.example.finalproject.model.ShoppingCart
import com.example.finalproject.model.User
import com.example.finalproject.model.UserAddress

@Database(
    entities = [User::class, Product::class, Category::class, Order::class, OrderItem::class, ShoppingCart::class, CartItem::class, Review::class, UserAddress::class, Payment::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun orderDao(): OrderDao
    abstract fun reviewDao(): ReviewDao
    abstract fun cartDao(): CartDao
}