package com.postsApp.data.network.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.postsApp.data.db.room.Converters

@Entity(tableName = "geos")
@TypeConverters(Converters::class)
data class Geo(
    @SerializedName("lat")
    @Expose
    @ColumnInfo(name = "lat")
    var lat: String? = null,

    @SerializedName("lng")
    @Expose
    @ColumnInfo(name = "lng")
    var lng: String? = null
)
