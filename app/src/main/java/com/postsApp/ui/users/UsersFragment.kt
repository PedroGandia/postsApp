package com.postsApp.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.postsApp.R
import com.postsApp.PostsAppApplication
import com.postsApp.ViewModelFactory
import com.postsApp.adapters.UserAdapter
import com.postsApp.data.DataManager
import com.postsApp.data.network.model.User
import com.postsApp.ui.base.BaseFragment
import com.postsApp.util.ErrorMessageResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_users.*
import timber.log.Timber
import javax.inject.Inject

class UsersFragment : BaseFragment(), UserAdapter.Callback {

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var userAdapter: UserAdapter

    private lateinit var usersViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.applicationContext as PostsAppApplication).getFragmentDispatchingAndroidInjector()
            .inject(this)
        userAdapter.setCallback(this)

        usersViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireActivity().application, dataManager)
        ).get(UsersViewModel::class.java)

        initUsers()
    }

    private fun initUsers() {

        img_search_cancel.setOnClickListener {
            edt_search.setText("")
            img_search_cancel.visibility = INVISIBLE
        }

        edt_search.afterTextChanged {
            if (it.isEmpty()) {
                img_search_cancel.visibility = INVISIBLE
            } else {
                img_search_cancel.visibility = VISIBLE
            }
            userAdapter.filter(it)
        }

        rl_users.setOnRefreshListener {
            usersViewModel.getUserListService()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MutableList<User>?> {
                    override fun onComplete() {
                        if (rl_users != null) rl_users.isRefreshing = false
                    }

                    override fun onSubscribe(d: Disposable) {
                        if (rl_users != null) rl_users.isRefreshing = true
                        compositeDisposable.add(d)
                    }

                    override fun onNext(userList: MutableList<User>) {
                        userListCloneable.clear()
                        userListCloneable.addAll(userList as ArrayList<User>)
                        userAdapter.addItems(userListCloneable)
                    }

                    override fun onError(throwable: Throwable) {
                        if (rl_users != null) rl_users.isRefreshing = false
                    }
                })
        }

        rv_users.layoutManager = linearLayoutManager
        rv_users.adapter = userAdapter

        getUserListFromDatabase()
    }

    private fun getUserListFromDatabase() {

            usersViewModel.getUserListFromDatabase()
            .observeOn(AndroidSchedulers.mainThread(), true)
            .subscribe(object : Observer<MutableList<User>?> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(userList: MutableList<User>) {
                    if (userList.isEmpty()) {
                        getUserListFromServer()
                    }else{
                        nsv_empty_list?.visibility = GONE
                        rl_users?.visibility = VISIBLE
                        userListCloneable.clear()
                        userListCloneable.addAll(userList as ArrayList<User>)
                        userAdapter.addItems(userListCloneable)
                    }
                }

                override fun onError(throwable: Throwable) {
                    val messageError = ErrorMessageResponse.showMessageError(context, throwable)
                    showMessageDialog(messageError, false)
                }
            })
    }

    private fun getUserListFromServer() {
            usersViewModel.getUserListService()
            .observeOn(AndroidSchedulers.mainThread(), true)
            .subscribe(object : Observer<MutableList<User>?> {
                override fun onComplete() {
                    progressDialog(false)
                }

                override fun onSubscribe(d: Disposable) {
                    progressDialog(true)
                    compositeDisposable.add(d)
                }

                override fun onNext(userList: MutableList<User>) {
                    if (userList.isEmpty()) {
                        nsv_empty_list?.visibility = VISIBLE
                        rl_users?.visibility = INVISIBLE
                    } else {
                        nsv_empty_list?.visibility = GONE
                        rl_users?.visibility = VISIBLE
                        userListCloneable.clear()
                        userListCloneable.addAll(userList as ArrayList<User>)
                        userAdapter.addItems(userListCloneable)
                    }
                }

                override fun onError(throwable: Throwable) {
                    val messageError = ErrorMessageResponse.showMessageError(context, throwable)
                    showMessageDialog(messageError, false)
                }
            })
    }

    override fun onDestroy() {
        try {
            compositeDisposable.dispose()
        } catch (e: RuntimeException) {
            Timber.e(e)
        }

        super.onDestroy()
    }

    companion object {
        var userListCloneable: ArrayList<User> = ArrayList()
    }

    override fun onUserClick(idUser: Long) {
        navigateTo(
            UsersFragmentDirections.actUsersToPosts(
                userId = idUser
            )
        )
    }

    override fun onIsEmptyList(isEmptyList: Boolean) {
        if (isEmptyList) {
            nsv_empty_list?.visibility = VISIBLE
            rl_users?.visibility = INVISIBLE
        } else {
            nsv_empty_list?.visibility = GONE
            rl_users?.visibility = VISIBLE
        }
    }
}
