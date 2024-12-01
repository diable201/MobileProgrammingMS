package com.example.assignment_4.di

import android.content.Context
import com.example.assignment_4.local.AnimeDatabase
import com.example.assignment_4.local.CachedTopAnimeDao
import com.example.assignment_4.local.CachedUpcomingAnimeDao
import com.example.assignment_4.local.FavoriteAnimeDao
import com.example.assignment_4.local.UserDao
import com.example.assignment_4.remote.AnimeApiService
import com.example.assignment_4.remote.RetrofitClient
import com.example.assignment_4.repository.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAnimeApiService(@ApplicationContext context: Context): AnimeApiService {
        return RetrofitClient.getApiService(context)
    }

    @Singleton
    @Provides
    fun provideAnimeDatabase(@ApplicationContext context: Context): AnimeDatabase {
        return AnimeDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideUserDao(database: AnimeDatabase): UserDao {
        return database.userDao()
    }

    @Singleton
    @Provides
    fun provideFavoriteAnimeDao(database: AnimeDatabase): FavoriteAnimeDao {
        return database.favoriteAnimeDao()
    }

    @Singleton
    @Provides
    fun provideCachedTopAnimeDao(database: AnimeDatabase): CachedTopAnimeDao {
        return database.cachedTopAnimeDao()
    }

    @Singleton
    @Provides
    fun provideCachedUpcomingAnimeDao(database: AnimeDatabase): CachedUpcomingAnimeDao {
        return database.cachedUpcomingAnimeDao()
    }

    @Singleton
    @Provides
    fun provideAnimeRepository(
        apiService: AnimeApiService,
        userDao: UserDao,
        favoriteAnimeDao: FavoriteAnimeDao,
        cachedTopAnimeDao: CachedTopAnimeDao,
        cachedUpcomingAnimeDao: CachedUpcomingAnimeDao,
        @ApplicationContext context: Context
    ): AnimeRepository {
        return AnimeRepository(
            apiService,
            userDao,
            favoriteAnimeDao,
            cachedTopAnimeDao,
            cachedUpcomingAnimeDao,
            context
        )
    }
}