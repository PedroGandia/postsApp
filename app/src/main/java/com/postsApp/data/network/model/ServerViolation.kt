package com.postsApp.data.network.model

import com.google.gson.annotations.Expose

data class ServerViolation(
    @Expose
    var propertyPath: String? = "",

    @Expose
    var message: String? = ""
)