package com.example.animelog.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
//import androidx.lifecycle.lifecycleScope
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.animelog.Anime
import com.example.animelog.AnimeRecyclerViewAdapter
import com.example.animelog.DisplayAnime
import com.example.animelog.R
//import com.example.animelog.current_season_api
import com.google.gson.Gson
import okhttp3.Headers
import org.json.JSONException

val current_season_api = "https://api.jikan.moe/v4/seasons/now"
var pages: MutableList<Int> = ArrayList()



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

        var AnimeList: MutableList<DisplayAnime> = ArrayList()
        var itemsAdapter = AnimeRecyclerViewAdapter(AnimeList, requireActivity())
        Rv.adapter = itemsAdapter
        Rv.layoutManager = LinearLayoutManager(requireContext()).also{
            val dividerItemDecoration = DividerItemDecoration(view.context, it.orientation)
            Rv.addItemDecoration(dividerItemDecoration)
        }

        var next_btn = view.findViewById<Button>(R.id.next_btn)
        var previous_btn = view.findViewById<Button>(R.id.previous_btn)

        var progressBar = view.findViewById<ContentLoadingProgressBar>(R.id.progress)

        // sets up client and gson
        val client = AsyncHttpClient()
        var gson = Gson()

        next_btn.setVisibility(View.GONE)
        previous_btn.setVisibility(View.GONE)

        // attempts to retrieve api data
        fun call_api(url: String, page: Int) {
            client.get(url+"?page=$page", object : JsonHttpResponseHandler() {
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
                        val pagination = json.jsonObject.getJSONObject("pagination")
                        val has_next_page = pagination.getBoolean("has_next_page")

                        val showJsonArray = json.jsonObject.getJSONArray("data")
                        for (i in 0 until showJsonArray.length()) {
                            var json_obj = showJsonArray.getJSONObject(i)


                            var title = json_obj.getString("title")

                            var images = json_obj.getJSONObject("images")
                            var jpg = images.getJSONObject("jpg")

                            var posterImageUrl = jpg.getString("image_url")

                            var genres = json_obj.getJSONArray("genres")
                            var genresDA = ""
                            for (i in 0 until genres.length()) {
                                var genre = genres.getJSONObject(i)
                                var gen_name = genre.getString("name")
                                genresDA += gen_name + "  "
                            }


                        var description = json_obj.getString("synopsis")
                        if (description == "null"){
                            description = "Description not available"
                        }

                        var rating = json_obj.getString("score")

                        if (rating == "null"){
                            rating = null
                        }
                        var studios = json_obj.getJSONArray("studios")
                        var studio_list = ""
                            for (i in 0 until studios.length()) {
                                var studio = studios.getJSONObject(i)
                                var studio_name = studio.getString("name")
                                studio_list += studio_name + "  "
                            }
                        var source = json_obj.getString("source")
                        var id = json_obj.getString("mal_id")



                        var new_da = DisplayAnime(title, description, posterImageUrl, genresDA, rating, "", studio_list, source, id)
                        if (new_da.genre?.contains("Hentai") == false) {
                            AnimeList.add(new_da)
                        }

                        }

                        if (has_next_page){
                            next_btn.setVisibility(View.VISIBLE)
                        }

                        if (page != 1){
                            previous_btn.setVisibility(View.VISIBLE)
                        }

                    }
                    // if theres an error, throw error message and hide progress bar
                    catch (e: JSONException) {
                        Log.e("MainActivity", "Encountered exception $e")
                        progressBar.hide()
                    }

                    // when done, updates recycleview adapter and hides progress bar to show anime
                    itemsAdapter.notifyDataSetChanged()
                    progressBar.hide()




                }
            })
    }

        call_api(current_season_api, 1)

        var page_num = 1

        next_btn.setOnClickListener(){
            next_btn.setVisibility(View.GONE)
            previous_btn.setVisibility(View.GONE)
            AnimeList.clear()
            page_num += 1
            call_api(current_season_api, page_num)
            itemsAdapter.notifyDataSetChanged()
        }

        previous_btn.setOnClickListener(){
            next_btn.setVisibility(View.GONE)
            previous_btn.setVisibility(View.GONE)
            AnimeList.clear()
            page_num -= 1
            call_api(current_season_api, page_num)
            itemsAdapter.notifyDataSetChanged()
        }



    }
    }




