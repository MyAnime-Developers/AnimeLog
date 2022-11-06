package com.example.animelog.fragments

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.animelog.ANIME_EXTRA
import com.example.animelog.Anime
import com.example.animelog.R


private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {

    private lateinit var animeTitle: TextView
    private lateinit var rating: RatingBar
    private lateinit var animeOverview: TextView
    private lateinit var animeImage: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.detail_screen)
        animeTitle = findViewById(R.id.animeTitle)
        rating = findViewById(R.id.rating)
        animeOverview = findViewById(R.id.animeOverview)
        animeImage = findViewById(R.id.animeImage)
        val anime = intent.getSerializableExtra(ANIME_EXTRA) as Anime
        animeTitle.text = anime.title
        animeOverview.text = anime.description
        Glide.with(this)
            .load(anime.posterImageUrl)
            .into(animeImage)


    }
}
