package com.postsApp.di.module

import android.app.Application
import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.room.RoomDatabase
import com.postsApp.PostsAppApplication
import com.postsApp.R
import com.postsApp.data.AppDataManager
import com.postsApp.data.Config
import com.postsApp.data.DataManager
import com.postsApp.data.db.AppDbHelper
import com.postsApp.data.db.DbHelper
import com.postsApp.data.db.room.AppDatabase
import com.postsApp.data.network.ApiHelper
import com.postsApp.data.network.AppApiHelper
import com.postsApp.di.ApplicationContext
import com.postsApp.di.BaseUrl
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @ApplicationContext
    internal fun provideContext(postsAppApplication: PostsAppApplication): Context {
        return postsAppApplication.applicationContext
    }

    @Provides
    internal fun provideApplication(postsAppApplication: PostsAppApplication): Application {
        return postsAppApplication
    }

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    fun provideCustomTabsIntent(@ApplicationContext context: Context): CustomTabsIntent {
        return CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDefault))
                .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDarkDefault))
                .addDefaultShareMenuItem()
                .build()
    }

    @Provides
    @BaseUrl
    internal fun provideBaseUrl(): String {
        return Config.BASE_URL
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, Config.DB_NAME)
                .fallbackToDestructiveMigration()
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    internal fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }

    @Provides
    @Singleton
    internal fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }
}