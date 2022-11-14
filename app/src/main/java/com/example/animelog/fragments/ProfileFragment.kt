package com.example.animelog.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animelog.*
import com.example.animelog.R

.
 *
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    var AnimeList: MutableList<DisplayAnime> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        var itemsAdapter = AnimeRecyclerViewAdapter(AnimeList, view.context)
        var ProfileRv = view.findViewById<RecyclerView>(R.id.profileRv)
        ProfileRv.adapter = itemsAdapter
        ProfileRv.layoutManager = LinearLayoutManager(view.context).also{
            val dividerItemDecoration = DividerItemDecoration(view.context, it.orientation)
            ProfileRv.addItemDecoration(dividerItemDecoration)
        }

        lifecycleScope.launch {
            (activity?.application as AnimeApplication).db.AnimeDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayAnime(
                        entity.title,
                        entity.description,
                        entity.posterImageUrl,
                        entity.genre,
                        entity.rating,
                        entity.voiceActors

                    )
                }.also { mappedList ->
                    AnimeList.clear()
                    AnimeList.addAll(mappedList)
                    itemsAdapter.notifyDataSetChanged()
                }
            }


        }
        // Inflate the layout for this fragment
        return view

    }


}