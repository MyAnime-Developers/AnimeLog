package com.example.animelog

import androidx.versionedparcelable.VersionedParcelize
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@VersionedParcelize

class Anime (): Serializable {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("poster_image")
    var posterImageUrl: String? = null

    @SerializedName("genre")
    var genre: MutableList<String>? = null

    @SerializedName("rating")
    var rating: Float? = null

    @SerializedName("voice_Actors")
    var voice_Actors: MutableList<String>? = null


}