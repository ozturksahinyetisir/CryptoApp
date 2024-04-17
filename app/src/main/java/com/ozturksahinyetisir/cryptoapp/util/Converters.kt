package com.ozturksahinyetisir.cryptoapp.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ozturksahinyetisir.cryptoapp.data.model.Platform
import com.ozturksahinyetisir.cryptoapp.data.model.Quote

class TagsConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromTags(tags: List<String>?): String = gson.toJson(tags ?: emptyList<String>())

    @TypeConverter
    fun toTags(tagsString: String?): List<String> =
        if (tagsString == null) emptyList() else gson.fromJson(tagsString, object : TypeToken<List<String>>() {}.type)
}

class PlatformConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPlatform(platform: Platform?): String? = gson.toJson(platform)

    @TypeConverter
    fun toPlatform(platformString: String?): Platform? =
        if (platformString == null) null else gson.fromJson(platformString, Platform::class.java)
}

class QuoteConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromQuote(quote: Quote?): String? = gson.toJson(quote)

    @TypeConverter
    fun toQuote(quoteString: String?): Quote? =
        if (quoteString == null) null else gson.fromJson(quoteString, Quote::class.java)
}