package com.example.finalproject.di

import android.content.Context
import androidx.room.Room
import com.example.finalproject.dao.CartDao
import com.example.finalproject.dao.ReviewDao
import com.example.finalproject.network.ApiService
import com.example.finalproject.network.WebSocketClient
import com.example.finalproject.repository.OrderManager
import com.example.finalproject.repository.Repository
import com.example.finalproject.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "pc_shopping_db")
            .fallbackToDestructiveMigration().build()

    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Provides
    fun provideProductDao(db: AppDatabase) = db.productDao()

    @Provides
    fun provideCategoryDao(db: AppDatabase) = db.categoryDao()

    @Provides
    fun provideOrderDao(db: AppDatabase) = db.orderDao()

    @Provides
    fun provideReviewDao(db: AppDatabase) = db.reviewDao()

    @Provides
    fun provideCartDao(db: AppDatabase) = db.cartDao()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build()).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Provides
    @Singleton
    fun provideWebSocketClient(coroutineScope: CoroutineScope): WebSocketClient =
        WebSocketClient(coroutineScope)

    @Provides
    @Singleton
    fun provideRepository(
        apiService: ApiService,
        db: AppDatabase,
        webSocketClient: WebSocketClient,
        cartDao: CartDao,
        reviewDao: ReviewDao,
        orderManager: OrderManager
    ) = Repository(apiService, db, webSocketClient, cartDao, reviewDao, orderManager)
}