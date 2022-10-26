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
import com.example.animelog.current_season_api
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import okhttp3.Headers
import org.json.JSONException


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


//        var itemsAdapter = MovieRecyclerViewAdapter(MoviesList)
        var itemsAdapter = AnimeRecyclerViewAdapter(AnimeList, this)

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
                //Log.e("MainActivity", "On Failure $json")
                try {
                    // shows progress bar and adds each movie from api into list
                    progressBar.show()
//                    val movieJsonArray = json.jsonObject.getJSONArray("results")
//                    for (i in 0 until movieJsonArray.length()) {
//                        var json_obj = movieJsonArray.getJSONObject(i)
//                        var new_show = Movie()
//                        new_show.title = json_obj.getString("original_title")
//                        new_show.description = json_obj.getString("overview")
//                        new_show.movieImageUrl = json_obj.getString("poster_path")
//                        MoviesList.add(new_show)
                    val showJsonArray = json.jsonObject.getJSONArray("data")
                    for (i in 0 until showJsonArray.length()) {
                        var json_obj = showJsonArray.getJSONObject(i)
                        var new_anime = Anime()
                        new_anime.title = json_obj.getString("title")

                        var images = json_obj.getJSONObject("images")
                        var jpg = images.getJSONObject("jpg")
                        new_anime.posterImageUrl = jpg.getString("image_url")


                        AnimeList.add(new_anime)
                    }

                }
                // if theres an error, throw error message and hide progress bar
                catch (e: JSONException){
                    Log.e("MainActivity", "Encountered exception $e")
                    progressBar.hide()
                }

                // when done, updates recycleview adapter and hides progress bar to show movies
                itemsAdapter.notifyDataSetChanged()
                progressBar.hide()


            }
        })


    }
    }



