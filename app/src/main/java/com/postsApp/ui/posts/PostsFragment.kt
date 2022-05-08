package com.postsApp.ui.posts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.postsApp.PostsAppApplication
import com.postsApp.R
import com.postsApp.ViewModelFactory
import com.postsApp.adapters.PostsAdapter
import com.postsApp.data.DataManager
import com.postsApp.data.network.model.Post
import com.postsApp.data.network.model.User
import com.postsApp.ui.base.BaseFragment
import com.postsApp.util.ErrorMessageResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_posts.*
import timber.log.Timber
import javax.inject.Inject

class PostsFragment : BaseFragment(), PostsAdapter.Callback {

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var postsAdapter: PostsAdapter

    private val postFragmentArgs: PostsFragmentArgs by navArgs()

    private lateinit var postsViewModel: PostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.applicationContext as PostsAppApplication).getFragmentDispatchingAndroidInjector()
            .inject(this)
        postsAdapter.setCallback(this)
        postsViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireActivity().application, dataManager)
        ).get(PostsViewModel::class.java)

        initPosts()
    }

    private fun initPosts() {
        img_toolbar_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        rv_posts?.layoutManager = linearLayoutManager
        rv_posts?.adapter = postsAdapter

        getUserData()
        getPostListFromDatabase()
    }

    private fun getUserData() {
        val userId = postFragmentArgs.userId

        postsViewModel.getUserFromDatabase(userId)
            .observeOn(AndroidSchedulers.mainThread(), true)
            .subscribe(object : Observer<User?> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(user: User) {
                    txt_name_user?.text = user.name ?: ""
                    txt_phone?.text = user.phone ?: ""
                    txt_email?.text = user.email ?: ""
                }

                override fun onError(throwable: Throwable) {
                    val messageError = ErrorMessageResponse.showMessageError(context, throwable)
                    showMessageDialog(messageError, false)
                }
            })
    }

    private fun getPostListFromDatabase() {
        val userId = postFragmentArgs.userId

        postsViewModel.getPostListFromDatabase(userId)
            .observeOn(AndroidSchedulers.mainThread(), true)
            .subscribe(object : Observer<MutableList<Post>?> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(postList: MutableList<Post>) {
                    if (postList.isEmpty()) {
                        getPostListFromServer()
                    } else {
                        nsv_empty_list?.visibility = GONE
                        nsv_posts?.visibility = VISIBLE
                        postsAdapter.addItems(postList)
                    }
                }

                override fun onError(throwable: Throwable) {
                    val messageError = ErrorMessageResponse.showMessageError(context, throwable)
                    showMessageDialog(messageError, false)
                }
            })


    }

    private fun getPostListFromServer() {
        val userId = postFragmentArgs.userId

        postsViewModel.getPostListService(userId)
            .observeOn(AndroidSchedulers.mainThread(), true)
            .subscribe(object : Observer<MutableList<Post>?> {
                override fun onComplete() {
                    progressDialog(false)
                }

                override fun onSubscribe(d: Disposable) {
                    progressDialog(true)
                    compositeDisposable.add(d)
                }

                override fun onNext(postList: MutableList<Post>) {
                    if (postList.isEmpty()) {
                        nsv_empty_list?.visibility = VISIBLE
                        nsv_posts?.visibility = INVISIBLE
                    } else {
                        nsv_empty_list?.visibility = GONE
                        nsv_posts?.visibility = VISIBLE
                        postsAdapter.addItems(postList)
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

    override fun onPostClick(idPost: Long) {}
}
