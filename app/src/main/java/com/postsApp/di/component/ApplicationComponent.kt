package com.postsApp.di.component

import com.postsApp.PostsAppApplication
import com.postsApp.di.module.FragmentBindingModule
import com.postsApp.di.module.ApplicationModule
import com.postsApp.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetworkModule::class,
    ApplicationModule::class,
    FragmentBindingModule::class
])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(postsAppApplication: PostsAppApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(postsAppApplication: PostsAppApplication)

}