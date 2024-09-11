package com.tinaciousdesign.interviews.stocks.di

import android.content.Context
import androidx.room.Room
import com.tinaciousdesign.interviews.stocks.BuildConfig
import com.tinaciousdesign.interviews.stocks.config.AppConfig
import com.tinaciousdesign.interviews.stocks.db.AppDatabase
import com.tinaciousdesign.interviews.stocks.db.stock.StockDao
import com.tinaciousdesign.interviews.stocks.events.EventBus
import com.tinaciousdesign.interviews.stocks.logging.Logger
import com.tinaciousdesign.interviews.stocks.networking.api.StocksApi
import com.tinaciousdesign.interviews.stocks.repositories.StocksRepository
import com.tinaciousdesign.interviews.stocks.repositories.StocksRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // region Repositories

    @Provides @Singleton
    fun provideStocksRepository(
        stocksApi: StocksApi,
        stockDao: StockDao,
    ): StocksRepository = StocksRepositoryImpl(
        stocksApi,
        stockDao,
    )

    @Provides @Singleton
    fun provideEventBus(): EventBus = EventBus()

    // endregion Repositories

    // region Networking

    @Provides @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val converterFactory = Json.asConverterFactory(
            "application/json; charset=UTF8".toMediaType()
        )

        return Retrofit.Builder()
            .baseUrl(AppConfig.stocksApiBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { Logger.tag("HttpLog").d(it) }
            .apply {
                level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
            }

    @Provides @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            // We can add other interceptors here, e.g. auth
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    // region Networking -> API

    @Provides @Singleton
    fun provideStocksApi(retrofit: Retrofit): StocksApi = retrofit.create(StocksApi::class.java)

    // endregion Networking -> API

    // endregion Networking

    // region Database

    @Provides @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "stocks",
        )
        .build()

    @Provides @Singleton
    fun provideStockDao(appDatabase: AppDatabase): StockDao = appDatabase.stocks()

    // endregion Database
}
