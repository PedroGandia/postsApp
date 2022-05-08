package com.postsApp.di.module

import com.postsApp.ui.posts.PostsFragment
import com.postsApp.ui.posts.PostsFragmentModule
import com.postsApp.ui.users.UsersFragment
import com.postsApp.ui.users.UsersFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector(modules = [(UsersFragmentModule::class)])
    abstract fun bindUsersModule(): UsersFragment

    @ContributesAndroidInjector(modules = [(PostsFragmentModule::class)])
    abstract fun bindPostsModule(): PostsFragment
}