package com.postsApp.data.network.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.postsApp.data.db.room.Converters

@Entity(tableName = "users")
@TypeConverters(Converters::class)
data class User(
    @PrimaryKey(autoGenerate = false)
    @Expose
    var id: Long,

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    var name: String? = null,

    @SerializedName("username")
    @Expose
    @ColumnInfo(name = "username")
    var username: String? = null,

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    var email: String? = null,

    @SerializedName("phone")
    @Expose
    @ColumnInfo(name = "phone")
    var phone: String? = null,

    @SerializedName("address")
    @Expose
    @ColumnInfo(name = "address")
    var address: Address? = null
)
