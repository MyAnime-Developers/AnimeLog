package com.example.animelog

import android.content.Intent
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.animelog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONException
import org.w3c.dom.Text


private const val TAG = "DetailActivity"
const val ENTRY_EXTRA = "WATCHLIST_EXTRA"
const val REMOVE_EXTRA = "WATCHLIST_REMOVE_EXTRA"

private var existence: Boolean = false
private val voiceActors = mutableListOf<VoiceActor>()
private lateinit var rvVoiceActors: RecyclerView

class DetailActivity : AppCompatActivity() {
    private lateinit var animeTitle: TextView
    private lateinit var rating: RatingBar
    private lateinit var animeOverview: TextView
    private lateinit var animeImage: ImageView
    private lateinit var animeGenre: TextView
    private lateinit var addButton: Button
    private lateinit var removeButton: Button
    private lateinit var ratingText: TextView
    private lateinit var studios: TextView
    private lateinit var source: TextView
    private lateinit var mp: MediaPlayer



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
        studios = findViewById(R.id.tvStudios)
        source = findViewById(R.id.tvSource)







        val anime = intent.getSerializableExtra(ANIME_EXTRA) as DisplayAnime
        animeTitle.text = anime.title
        animeOverview.text = anime.description
        Glide.with(this)
            .load(anime.posterImageUrl)
            .into(animeImage)

        animeGenre.text = "Genres: " + anime.genre
        studios.text = "Studio(s): " + anime.studio
        source.text = "Source: " + anime.source




            var fl = anime.rating?.toFloat()
            if (fl != null) {
                rating.rating = fl
                ratingText.text = "Rating: " + fl.toString()
            }

            else {
                rating.rating = 0.0F
                ratingText.text = "Rating: Not available"
            }



        addButton.setOnClickListener{
            mp = MediaPlayer.create(this, R.raw.ding)
            mp.start()
            lifecycleScope.launch(Dispatchers.IO){
                (application as AnimeApplication).db.AnimeDao().insert(
                    AnimeEntity(
                        title = anime.title,
                        description = anime.description,
                        genre = anime.genre,
                        posterImageUrl = anime.posterImageUrl,
                        rating = anime.rating,
                        voiceActors = anime.voiceActors,
                        studio = anime.studio,
                        source = anime.source,
                        anime_id = anime.anime_id

                    )
                )

                (application as AnimeApplication).db.AnimeDao().deleteDuplicates()
            }

            val toast = Toast.makeText(this, "Added to watch list", Toast.LENGTH_SHORT)
            toast.show()
        }


        removeButton.setOnClickListener{
            mp = MediaPlayer.create(this, R.raw.pop)
            mp.start()
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
//

        val VoiceAdapter = VoiceActorAdapter(this, voiceActors)
        rvVoiceActors = findViewById(R.id.rvVoiceActors)
        rvVoiceActors.adapter = VoiceAdapter
        rvVoiceActors.layoutManager = LinearLayoutManager(this)
        val client = AsyncHttpClient()
        var animeId = anime.anime_id

        voiceActors.clear()

        val voice_actor_api = "https://api.jikan.moe/v4/anime/$animeId/characters"
        client.get(voice_actor_api, object : JsonHttpResponseHandler() {
            // if fails
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("DetailActivity", "On Failure $statusCode")
            }

            // if succeeds
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                try {
                    val showJsonArray = json.jsonObject.getJSONArray("data")
                    voiceActors.addAll(VoiceActor.fromJsonArray(showJsonArray))


                }
                // if theres an error, throw error message and hide progress bar
                catch (e: JSONException) {
                    Log.e("MainActivity", "Encountered exception $e")

                }

                // when done, updates recycleview adapter and hides progress bar to show anime
                VoiceAdapter.notifyDataSetChanged()





            }
        })













    }
}
