package com.postsApp.ui.users

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.postsApp.adapters.UserAdapter
import com.postsApp.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class UsersFragmentModule {

    @Provides
    fun provideClientAdapter(): UserAdapter {
        return UserAdapter(ArrayList())
    }

    @Provides
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}