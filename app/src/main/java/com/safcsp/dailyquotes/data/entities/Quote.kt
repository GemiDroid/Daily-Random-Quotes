package com.safcsp.dailyquotes.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quote")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val Id: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("en")
    val content: String
)
