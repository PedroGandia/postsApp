package com.postsApp.util

import android.content.Context
import com.google.gson.Gson
import com.postsApp.R
import com.postsApp.data.network.model.MessageResponse
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.util.*

class ErrorMessageResponse {
    companion object {
        fun showMessageError(context: Context?, throwable: Throwable): String {
            val messageThrowable = throwable.message ?: ""
            var messageError = context?.getString(R.string.title_error_message, messageThrowable)
                ?: messageThrowable
            return try {
                val responseBody =
                    ((throwable as HttpException).response().errorBody() as ResponseBody).string()

                val gson = Gson()
                val messageResponse = gson.fromJson(responseBody, MessageResponse::class.java)
                val serverViolationList = messageResponse?.serverViolationList ?: ArrayList()
                val message = messageResponse?.message ?: ""
                val detail = messageResponse?.detail ?: ""

                when {
                    serverViolationList.isNotEmpty() -> {
                        messageError = ""
                        serverViolationList.forEach {
                            messageError += "${it.message?.removeSuffix(".")}: ${it.propertyPath}.\n"
                        }
                    }
                    message.isNotEmpty() -> {
                        messageError = message
                    }
                    detail.isNotEmpty() -> {
                        messageError = detail
                    }
                }

                messageError
            } catch (e: Exception) {
                messageError
            }

        }
    }
}