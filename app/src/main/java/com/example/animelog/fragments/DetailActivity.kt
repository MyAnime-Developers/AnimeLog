package com.example.animelog.fragments

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.animelog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "DetailActivity"
const val ENTRY_EXTRA = "WATCHLIST_EXTRA"
const val REMOVE_EXTRA = "WATCHLIST_REMOVE_EXTRA"

private var existence: Boolean = false

class DetailActivity : AppCompatActivity() {

    private lateinit var animeTitle: TextView
    private lateinit var rating: RatingBar
    private lateinit var animeOverview: TextView
    private lateinit var animeImage: ImageView
    private lateinit var animeGenre: TextView
    private lateinit var addButton: Button
    private lateinit var removeButton: Button
    private lateinit var ratingText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.detail_screen)
        addButton = findViewById(R.id.addButton)
        removeButton = findViewById(R.id.removeButton)

        animeTitle = findViewById(R.id.animeTitle)
        rating = findViewById(R.id.rating)
        animeOverview = findViewById(R.id.animeOverview)
        animeImage = findViewById(R.id.animeImage)
        animeGenre = findViewById(R.id.genre)

        ratingText = findViewById(R.id.ratingText)





        val anime = intent.getSerializableExtra(ANIME_EXTRA) as DisplayAnime
        animeTitle.text = anime.title
        animeOverview.text = anime.description
        Glide.with(this)
            .load(anime.posterImageUrl)
            .into(animeImage)

        animeGenre.text = "Genres: " + anime.genre


            var fl = anime.rating?.toFloat()
            if (fl != null) {
                rating.rating = fl
                ratingText.text = "Rating: " + fl.toString()
            }

            else {
                rating.rating = 0.0F
                ratingText.text = "Rating: Not available"
            }

            Log.e("DetailActivity", "$fl")

        addButton.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO){
                (application as AnimeApplication).db.AnimeDao().insert(
                    AnimeEntity(
                        title = anime.title,
                        description = anime.description,
                        genre = anime.genre,
                        posterImageUrl = anime.posterImageUrl,
                        rating = anime.rating,
                        voiceActors = anime.voiceActors

                    )
                )

                (application as AnimeApplication).db.AnimeDao().deleteDuplicates()
            }

            val toast = Toast.makeText(this, "Added to watch list", Toast.LENGTH_SHORT)
            toast.show()
        }

        removeButton.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO){
                anime.title?.let { it1 ->
                    (application as AnimeApplication).db.AnimeDao().deleteByanimeTitle(
                        it1
                    )
                }
                (application as AnimeApplication).db.AnimeDao().deleteDuplicates()
            }

            val toast = Toast.makeText(this, "Removed from watch list", Toast.LENGTH_SHORT)
            toast.show()
        }











    }
}
