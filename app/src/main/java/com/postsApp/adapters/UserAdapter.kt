package com.postsApp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.postsApp.R
import com.postsApp.data.network.model.User
import com.postsApp.ui.users.UsersFragment
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.*

class UserAdapter(private val userList: MutableList<User>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val userListAux: ArrayList<User> = ArrayList<User>()

    interface Callback {
        fun onUserClick(idUser: Long)
        fun onIsEmptyList(isEmptyList: Boolean)
    }

    private var callback: Callback? = null

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun addItems(userList: MutableList<User>?) {
        this.userList?.clear()
        this.userListAux.clear()
        userList?.let { this.userList?.addAll(it) }
        UsersFragment.userListCloneable.let { this.userListAux.addAll(it) }
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): User? {
        return if (position != RecyclerView.NO_POSITION) {
            userList?.get(position)
        } else
            null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val userViewHolder =
            UserViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_user,
                    parent,
                    false
                )
            )

        userViewHolder.itemView.txt_view_posts.setOnClickListener {
            val user: User? = getItem(userViewHolder.adapterPosition)
            user?.let { callback?.onUserClick(it.id) }
        }

        return userViewHolder
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserViewHolder).onBind(userList?.get(position))
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(user: User?) {

            itemView.txt_name_user.text = user?.name ?: ""
            itemView.txt_phone.text = user?.phone ?: ""
            itemView.txt_email.text = user?.email ?: ""
        }
    }

    fun filter(nameFilter: String) {
        var nameUser = nameFilter
        nameUser = nameUser.toLowerCase(Locale.getDefault())
        this.userList?.clear()
        if (nameUser.isEmpty()) {
            this.userList?.addAll(userListAux)
        } else {
            for (user in userListAux) {
                if (user.name != null && user.name!!.toLowerCase(Locale.getDefault()).contains(nameUser)
                ) {
                    this.userList?.add(user)
                }
            }
        }
        this.userList?.isEmpty()?.let { callback?.onIsEmptyList(it) }
        notifyDataSetChanged()
    }
}