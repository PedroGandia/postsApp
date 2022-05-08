package com.postsApp.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.postsApp.MainViewModel
import com.postsApp.R
import com.postsApp.util.Constants.DIALOG_IS_CANCELABLE
import com.postsApp.util.Constants.DIALOG_IS_REDIRECTABLE
import com.postsApp.util.Constants.DIALOG_MESSAGE
import com.postsApp.util.Constants.DIALOG_TITLE
import kotlinx.android.synthetic.main.fragment_message_dialog.view.*

class MessageDialogFragment : DialogFragment() {

    private var mainViewModel: MainViewModel? = null

    private var rootView: View? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        initViews()
        val isCancelable = arguments?.getBoolean(DIALOG_IS_CANCELABLE)
        val alertDialog = android.app.AlertDialog.Builder(context)
            .setView(rootView)
            .create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCanceledOnTouchOutside(isCancelable!!)

        return alertDialog
    }

    private fun initViews() {
        activity?.let {
            mainViewModel = ViewModelProvider(it).get(MainViewModel::class.java)
        }

        rootView = LayoutInflater.from(context).inflate(R.layout.fragment_message_dialog, null, false)

        rootView?.btn_dialog_ok?.setOnClickListener {
            dismiss()
            val isRedirectable = arguments?.getBoolean(DIALOG_IS_REDIRECTABLE)
            if (mainViewModel != null && isRedirectable!!){
                mainViewModel!!.isNavigation.postValue(true)
            }
        }

        val title = arguments?.getString(DIALOG_TITLE)
        title?.let {
            rootView?.txt_dialog_title?.visibility = VISIBLE
            rootView?.txt_dialog_title?.text = it
        }

        val message = arguments?.getString(DIALOG_MESSAGE)
        rootView?.txt_dialog_subtitle?.text = message
        rootView?.txt_dialog_subtitle?.movementMethod = ScrollingMovementMethod()
    }
}