package com.example.animelog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.animelog.fragments.HomeFragment
import com.example.animelog.fragments.ProfileFragment
import com.example.animelog.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.Headers
import com.google.gson.Gson
import org.json.JSONException

private const val popular_shows_api = "https://api.themoviedb.org/3/tv/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1"
const val current_season_api = "https://api.jikan.moe/v4/seasons/now"
class MainActivity : AppCompatActivity() {
    // creates mutable list of movies

//    var AnimeList: MutableList<Anime> = ArrayList()
//
         override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
//
//        // assigns views to variables and set up recyclerview
//        val Rv = findViewById<RecyclerView>(R.id.Rv)
//
//
////        var itemsAdapter = MovieRecyclerViewAdapter(MoviesList)
//        var itemsAdapter = AnimeRecyclerViewAdapter(AnimeList, this)
//
//        Rv.adapter = itemsAdapter
//        Rv.layoutManager = LinearLayoutManager(this)
//
//        var progressBar = findViewById<ContentLoadingProgressBar>(R.id.progress)
//
//        // sets up client and gson
//        val client = AsyncHttpClient()
//        var gson = Gson()
//
//
//        // attempts to retrieve api data
//        client.get(current_season_api, object: JsonHttpResponseHandler(){
//            // if fails
//            override fun onFailure(
//                statusCode: Int,
//                headers: Headers?,
//                response: String?,
//                throwable: Throwable?
//            ) {
//                Log.e("MainActivity", "On Failure $statusCode")
//            }
//
//            // if succeeds
//            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
//                //Log.e("MainActivity", "On Failure $json")
//                try {
//                    // shows progress bar and adds each movie from api into list
//                    progressBar.show()
////                    val movieJsonArray = json.jsonObject.getJSONArray("results")
////                    for (i in 0 until movieJsonArray.length()) {
////                        var json_obj = movieJsonArray.getJSONObject(i)
////                        var new_show = Movie()
////                        new_show.title = json_obj.getString("original_title")
////                        new_show.description = json_obj.getString("overview")
////                        new_show.movieImageUrl = json_obj.getString("poster_path")
////                        MoviesList.add(new_show)
//                    val showJsonArray = json.jsonObject.getJSONArray("data")
//                    for (i in 0 until showJsonArray.length()) {
//                        var json_obj = showJsonArray.getJSONObject(i)
//                        var new_anime = Anime()
//                        new_anime.title = json_obj.getString("title")
//
//                        var images = json_obj.getJSONObject("images")
//                        var jpg = images.getJSONObject("jpg")
//                        new_anime.posterImageUrl = jpg.getString("image_url")
//
//
//                        AnimeList.add(new_anime)
//                    }
//
//                }
//                // if theres an error, throw error message and hide progress bar
//                catch (e: JSONException){
//                      Log.e("MainActivity", "Encountered exception $e")
//                    progressBar.hide()
//                }
//
//                // when done, updates recycleview adapter and hides progress bar to show movies
//                itemsAdapter.notifyDataSetChanged()
//                progressBar.hide()
//
    val fragmentManager: FragmentManager = supportFragmentManager

    findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->
        var fragmentToShow: Fragment? = null
        when (item.itemId) {
            R.id.actionHome -> {
                fragmentToShow = HomeFragment()
            }
            R.id.actionProfile -> {
                fragmentToShow = ProfileFragment()
            }
            R.id.actionSearch -> {
                fragmentToShow = SearchFragment()
            }
        }
        if(fragmentToShow!=null){
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
        }
        true


    }
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.actionHome
//
//            }
//        })
//
//
//    }
}

}