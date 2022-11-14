package com.example.animelog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

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