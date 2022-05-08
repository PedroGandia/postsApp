package com.postsApp.data.network.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.postsApp.data.db.room.Converters

@Entity(tableName = "companies")
@TypeConverters(Converters::class)
data class Company(
    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    var name: String? = null,

    @SerializedName("catchPhrase")
    @Expose
    @ColumnInfo(name = "catchPhrase")
    var catchPhrase: String? = null,

    @SerializedName("bs")
    @Expose
    @ColumnInfo(name = "bs")
    var bs: String? = null
)
