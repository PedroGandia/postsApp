package com.postsApp.ui.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.postsApp.data.DataManager
import com.postsApp.data.network.model.User
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class UsersViewModel(application: Application, private val dataManager: DataManager) :
    AndroidViewModel(application) {

    fun getUserListFromDatabase(): Observable<MutableList<User>?> {
        return Observable.defer { Observable.just(dataManager.selectUserList()) }
            .map { it as MutableList<User>? }
            .subscribeOn(Schedulers.computation())
    }

    fun getUserListService(): Observable<MutableList<User>?> {
        return dataManager.getUserList()
            .map {
                dataManager.deleteUserList()
                dataManager.insertUserList(it)
                it
            }
            .subscribeOn(Schedulers.io())
    }
}