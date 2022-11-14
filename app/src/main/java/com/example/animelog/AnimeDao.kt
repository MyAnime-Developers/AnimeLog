package com.example.animelog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime_table")
    fun getAll(): Flow<List<AnimeEntity>>

    @Insert
    fun insertAll(anime: List<AnimeEntity>)

    @Insert
    fun insert(anime: AnimeEntity)

    @Query("DELETE FROM anime_table")
    fun deleteAll()

    @Query("DELETE FROM anime_table WHERE title = :animeTitle")
    fun deleteByanimeTitle(animeTitle: String)

    @Query("DELETE FROM anime_table WHERE id NOT IN (SELECT MIN(id) FROM anime_table GROUP BY title)")
    fun deleteDuplicates()

    @Query("SELECT EXISTS (SELECT 1 FROM anime_table WHERE title = :title)")
    fun exists(title: String): Boolean
}