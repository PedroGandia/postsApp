package com.postsApp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("data")
    @Expose
    var data: Any? = null,

    @SerializedName("success")
    @Expose
    var success: Boolean? = null,

    @SerializedName("code")
    @Expose
    var code: Int? = null,

    @SerializedName("message")
    @Expose
    var message: String? = null,

    @SerializedName("type")
    @Expose
    var type: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("detail")
    @Expose
    var detail: String? = null,

    @SerializedName("violations")
    @Expose
    var serverViolationList: MutableList<ServerViolation>? = ArrayList()
)