package com.postsApp.data.db.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.postsApp.data.network.model.*


class Converters {

    @TypeConverter
    fun stringToAddress(data: String?): Address? {
        if (data == null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<Address?>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun addressToString(data: Address?): String? {
        val gson = Gson()
        return gson.toJson(data)
    }

    @TypeConverter
    fun stringToGeo(data: String?): Geo? {
        if (data == null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<Geo?>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun geoToString(data: Geo?): String? {
        val gson = Gson()
        return gson.toJson(data)
    }

    @TypeConverter
    fun stringToCompany(data: String?): Company? {
        if (data == null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<Company?>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun companyToString(data: Company?): String? {
        val gson = Gson()
        return gson.toJson(data)
    }
}