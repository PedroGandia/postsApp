package com.postsApp.ui.posts

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.postsApp.adapters.*
import com.postsApp.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class PostsFragmentModule {

    @Provides
    fun providePostAdapter(): PostsAdapter {
        return PostsAdapter(ArrayList())
    }

    @Provides
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}