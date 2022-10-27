package com.example.animelog

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animelog.fragments.HomeFragment


class AnimeRecyclerViewAdapter(
    private val shows: List<Anime>, private val context: HomeFragment
)  : RecyclerView.Adapter<AnimeRecyclerViewAdapter.AnimeViewHolder>()
{
    inner class AnimeViewHolder(val mView: View) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        var mItem: Anime? = null
        val mAnimeTitle: TextView
        val mAnimeGenre: TextView
        val mAnimeImage: ImageView

        init {
            mAnimeTitle = itemView.findViewById<TextView>(R.id.title)
            mAnimeGenre = itemView.findViewById<TextView>(R.id.genre)
            mAnimeImage = itemView.findViewById<ImageView>(R.id.image)

            mView.setOnClickListener(this)


        }

        override fun onClick(p0: View?) {


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.anime, parent, false)
        return AnimeViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = shows[position]
        holder.mAnimeTitle.text = anime.title
        holder.mAnimeGenre.text = ""
        for (genre in anime.genre.orEmpty()){
            holder.mAnimeGenre.text = holder.mAnimeGenre.text.toString() + genre + "     "
        }
        Glide.with(holder.mView)
            .load(anime.posterImageUrl)
            .centerInside()
            .into(holder.mAnimeImage)

    }


    override fun getItemCount(): Int {
        return shows.size
    }

}