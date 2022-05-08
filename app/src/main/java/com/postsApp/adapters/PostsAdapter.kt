package com.postsApp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.postsApp.R
import com.postsApp.data.network.model.Post
import com.postsApp.util.Constants.INVALID_ID
import kotlinx.android.synthetic.main.item_post.view.*

class PostsAdapter(private val postList: MutableList<Post>?) :
    RecyclerView.Adapter<PostsAdapter.BaseViewHolder<*>>() {

    interface Callback {
        fun onPostClick(idPost: Long)
    }

    private var callback: Callback? = null

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun addItems(postList: MutableList<Post>?) {
        this.postList?.clear()
        postList?.let { this.postList?.addAll(it) }
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): Post? {
        return if (position != RecyclerView.NO_POSITION) {
            this.postList?.get(position)
        } else
            null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        val postViewHolder = PostViewHolder(view)
        postViewHolder.itemView.setOnClickListener {
            val post: Post? = getItem(postViewHolder.adapterPosition)
            post?.let {
                callback?.onPostClick(it.id ?: INVALID_ID)
            }
        }

        return postViewHolder
    }

    override fun getItemCount(): Int {
        return this.postList?.size ?: 0
    }

    override fun onBindViewHolder(baseViewHolder: BaseViewHolder<*>, position: Int) {
        val post = this.postList?.get(position)
        (baseViewHolder as PostViewHolder).bind(post as Post, position)
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T, position: Int)
    }

    inner class PostViewHolder(itemView: View) : BaseViewHolder<Post>(itemView) {
        override fun bind(item: Post, position: Int) {
            itemView.txt_title.text = item.title
            itemView.txt_body.text = item.body

            itemView.vie_line.isVisible = position > 0
        }
    }
}