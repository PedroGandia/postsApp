package com.postsApp.ui.base

import android.content.Context
import android.text.*
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.postsApp.R
import com.postsApp.ui.dialog.ProgressDialogFragment
import com.postsApp.util.Constants.DIALOG_IS_CANCELABLE
import com.postsApp.util.Constants.DIALOG_IS_REDIRECTABLE
import com.postsApp.util.Constants.DIALOG_IS_WITH_ANIMATION
import com.postsApp.util.Constants.DIALOG_MESSAGE
import com.postsApp.util.Constants.DIALOG_TITLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber
import kotlin.coroutines.CoroutineContext


abstract class BaseFragment : Fragment(), CoroutineScope {

    private var fragmentInteractionListener: OnFragmentInteractionListener? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            fragmentInteractionListener = context
        } else {
            Timber.e("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onResume() {
        super.onResume()
        fragmentInteractionListener?.onBaseFragmentInteraction()
    }

    override fun onDetach() {
        super.onDetach()
        fragmentInteractionListener = null
    }

    interface OnFragmentInteractionListener {
        fun onBaseFragmentInteraction()
    }

    private val progressDialog = ProgressDialogFragment()

    protected fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    protected fun displayToast(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    protected fun progressDialog(show: Boolean) {
        try {
            if (show) {
                activity?.let { progressDialog.show(it) }
            } else {
                progressDialog.dialog.dismiss()
            }
        } catch (uninitializedPropertyAccessException: UninitializedPropertyAccessException) {
        }
    }

    protected fun showMessageDialog(message: String?, isRedirect: Boolean, title: String? = null, isWithAnimation: Boolean = false) {
        progressDialog(false)
        val bundle = bundleOf(
            DIALOG_TITLE to title,
            DIALOG_MESSAGE to message,
            DIALOG_IS_REDIRECTABLE to isRedirect,
            DIALOG_IS_CANCELABLE to false,
            DIALOG_IS_WITH_ANIMATION to isWithAnimation
        )
        findNavController().navigate(R.id.message_dialog_fragment, bundle)
    }

    protected fun navigateTo(navDirection: NavDirections) {
        progressDialog(false)
        findNavController().navigate(navDirection)
    }
}
