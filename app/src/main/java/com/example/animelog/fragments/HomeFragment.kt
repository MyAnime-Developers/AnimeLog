package com.example.animelog.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.animelog.Anime
import com.example.animelog.AnimeRecyclerViewAdapter
import com.example.animelog.R
//import com.example.animelog.current_season_api
import com.google.gson.Gson
import okhttp3.Headers
import org.json.JSONException

val current_season_api = "https://api.jikan.moe/v4/seasons/now"
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // assigns views to variables and set up recyclerview
        val Rv = view.findViewById<RecyclerView>(R.id.Rv)


        var AnimeList: MutableList<Anime> = ArrayList()
        var itemsAdapter = AnimeRecyclerViewAdapter(AnimeList, requireActivity())
        Rv.adapter = itemsAdapter
        Rv.layoutManager = LinearLayoutManager(requireContext())

        var progressBar = view.findViewById<ContentLoadingProgressBar>(R.id.progress)

        // sets up client and gson
        val client = AsyncHttpClient()
        var gson = Gson()


        // attempts to retrieve api data
        client.get(current_season_api, object: JsonHttpResponseHandler(){
            // if fails
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("MainActivity", "On Failure $statusCode")
            }

            // if succeeds
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                try {
                    progressBar.show()
                    val showJsonArray = json.jsonObject.getJSONArray("data")
                    for (i in 0 until showJsonArray.length()) {
                        var json_obj = showJsonArray.getJSONObject(i)
                        var new_anime = Anime()
                        new_anime.title = json_obj.getString("title")

                        var images = json_obj.getJSONObject("images")
                        var jpg = images.getJSONObject("jpg")
                        new_anime.posterImageUrl = jpg.getString("image_url")

                        var genres = json_obj.getJSONArray("genres")
                        var genreList: MutableList<String> = ArrayList()
                        for (i in 0 until genres.length()) {
                            var genre = genres.getJSONObject(i)
                            var gen_name = genre.getString("name")
                            genreList.add(gen_name)
                        }

                        new_anime.description = json_obj.getString("synopsis")

                        new_anime.genre = genreList



                        AnimeList.add(new_anime)
                    }



                }
                // if theres an error, throw error message and hide progress bar
                catch (e: JSONException){
                    Log.e("MainActivity", "Encountered exception $e")
                    progressBar.hide()
                }

                // when done, updates recycleview adapter and hides progress bar to show anime
                itemsAdapter.notifyDataSetChanged()
                progressBar.hide()


            }
        })


    }
    }



