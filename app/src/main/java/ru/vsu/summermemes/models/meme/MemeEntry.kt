package ru.vsu.summermemes.models.meme

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MemeEntry(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("isFavorite")
    val isFavorite: Boolean,
    @SerializedName("createdDate")
    val createdDate: Long,
    @SerializedName("photoUtl")
    val photoUrl: String
) : Serializable