package com.postsApp.data.network.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.postsApp.data.db.room.Converters

@Entity(tableName = "addresses")
@TypeConverters(Converters::class)
data class Address(
    @SerializedName("street")
    @Expose
    @ColumnInfo(name = "street")
    var street: String? = null,

    @SerializedName("suite")
    @Expose
    @ColumnInfo(name = "suite")
    var suite: String? = null,

    @SerializedName("city")
    @Expose
    @ColumnInfo(name = "city")
    var city: String? = null,

    @SerializedName("zipcode")
    @Expose
    @ColumnInfo(name = "zipcode")
    var zipcode: String? = null,

    @SerializedName("geo")
    @Expose
    @ColumnInfo(name = "geo")
    var geo: Geo? = null,

    @SerializedName("phone")
    @Expose
    @ColumnInfo(name = "phone")
    var phone: String? = null,

    @SerializedName("website")
    @Expose
    @ColumnInfo(name = "website")
    var website: String? = null,

    @SerializedName("company")
    @Expose
    @ColumnInfo(name = "company")
    var company: Company? = null
)
