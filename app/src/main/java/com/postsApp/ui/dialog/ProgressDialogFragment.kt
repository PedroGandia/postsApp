package com.postsApp.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.postsApp.R


class ProgressDialogFragment {

    lateinit var dialog: Dialog

    fun show(context: Context): Dialog {
        dialog = Dialog(context)
        return showDialog(context)
    }

    private fun showDialog(context: Context): Dialog {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.fragment_progress_dialog, null)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(view)
        dialog.show()

        return dialog
    }
}