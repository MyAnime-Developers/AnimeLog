package com.example.animelog

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animelog.fragments.DetailActivity
import com.example.animelog.fragments.HomeFragment
import android.content.Context


const val ANIME_EXTRA = "ANIME_EXTRA"

class AnimeRecyclerViewAdapter(
    private val animes: MutableList<DisplayAnime>, private val context: Context
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
            val show = animes[adapterPosition]
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ANIME_EXTRA, show)
            context.startActivity(intent)





        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.anime, parent, false)
        return AnimeViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animes.get(position)
        holder.mAnimeTitle.text = anime.title
        holder.mAnimeGenre.text = ""
        holder.mAnimeGenre.text = anime.genre
        Glide.with(holder.mView)
            .load(anime.posterImageUrl)
            .centerInside()
            .into(holder.mAnimeImage)

    }


    override fun getItemCount(): Int {
        return animes.size
    }

}