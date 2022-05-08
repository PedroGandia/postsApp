package com.postsApp.di.module

import com.google.gson.GsonBuilder
import com.postsApp.BuildConfig
import com.postsApp.data.Config.BASE_URL
import com.postsApp.data.network.*
import com.postsApp.di.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

    @Provides
    @Singleton
    internal fun provideDisableCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                    .header("cache-control", "no-cache")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            disableCacheInterceptor: Interceptor,
            authenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .authenticator(authenticator)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(disableCacheInterceptor)
                .build()
    }

    @Provides
   @Singleton
   internal fun provideGsonConverterFactory(): GsonConverterFactory {
       val gsonBuilder = GsonBuilder()
       gsonBuilder.excludeFieldsWithoutExposeAnnotation()
       return GsonConverterFactory.create(gsonBuilder.create())
   }

    @Provides
    @Singleton
    internal fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(
        @BaseUrl baseUrl: String, gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideRecipeService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideRecipeServiceAuth(): ApiServiceAuth {
        val gsonBuilder
                = GsonBuilder()
        gsonBuilder.excludeFieldsWithoutExposeAnnotation()

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiServiceAuth::class.java)
    }
}