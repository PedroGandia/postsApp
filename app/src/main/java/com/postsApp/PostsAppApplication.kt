package com.postsApp


import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.postsApp.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

class PostsAppApplication : Application() {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        initModel()
    }

    private fun initModel() {
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        Timber.plant(DebugTree())

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    fun getActivityDispatchingAndroidInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    fun getFragmentDispatchingAndroidInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }
}
