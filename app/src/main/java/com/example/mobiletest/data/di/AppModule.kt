package com.example.mobiletest.data.di

import android.app.Application
import androidx.room.Room
import com.example.mobiletest.data.MainDb
import com.example.mobiletest.data.api.MainApi
import com.example.mobiletest.data.repository.CategoryRepository
import com.example.mobiletest.data.repository.CategoryRepositoryImpl
import com.example.mobiletest.data.repository.DishesRepository
import com.example.mobiletest.data.repository.DishesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.mobiletest.utils.Contstants.BASE_URL

//DI для получения нужны объектов БД, API и репозиториев
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMainDb(app: Application): MainDb {
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "data.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMainApi(): MainApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(MainApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(api: MainApi, db: MainDb): CategoryRepository {
        return CategoryRepositoryImpl(api, db.categoryDao)
    }

    @Provides
    @Singleton
    fun provideDishesRepository(api: MainApi, db: MainDb): DishesRepository {
        return DishesRepositoryImpl(api, db.dishesDao)
    }
}