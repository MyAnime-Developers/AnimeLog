package com.example.animelog

data class DisplayAnime(
    val title: String?,
    val description: String?,
    val posterImageUrl: String?,
    val genre: String?,
    val rating: String?,
    val voiceActors: String?,
    ) : java.io.Serializable