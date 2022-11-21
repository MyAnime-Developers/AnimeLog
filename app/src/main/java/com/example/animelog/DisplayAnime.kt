package com.example.animelog

data class DisplayAnime(
    val title: String?,
    val description: String?,
    val posterImageUrl: String?,
    val genre: String?,
    val rating: String?,
    val voiceActors: String,
    val studio: String?,
    val source: String?,
    val anime_id: String
    ) : java.io.Serializable