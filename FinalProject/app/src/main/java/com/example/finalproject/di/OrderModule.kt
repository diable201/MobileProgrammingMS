package com.example.finalproject.di

import com.example.finalproject.repository.OrderManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderModule {

    @Provides
    @Singleton
    fun provideOrderManager(): OrderManager {
        return OrderManager()
    }
}