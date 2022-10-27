package com.example.animelog

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Anime (): Serializable {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("poster_image")
    var posterImageUrl: String? = null

    @SerializedName("genre")
    var genre: MutableList<String>? = null
}