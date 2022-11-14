package com.example.animelog

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "posterImageUrl") val posterImageUrl: String?,
    @ColumnInfo(name = "genre") val genre: String?,
    @ColumnInfo(name = "rating") val rating: String?,
    @ColumnInfo(name = "voiceActors") val voiceActors: String?

)