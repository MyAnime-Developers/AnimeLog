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
const val ACTOR_EXTRA = "ACTOR_EXTRA"
class VoiceActorAdapter(private val context: Context, private val voiceActors: List<VoiceActor>) :
    RecyclerView.Adapter<VoiceActorAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val characterName = itemView.findViewById<TextView>(R.id.characterNametv)
        private val actorName = itemView.findViewById<TextView>(R.id.actorNameTv)
        private val characterImage = itemView.findViewById<ImageView>(R.id.characterIv)
        private val actorImage = itemView.findViewById<ImageView>(R.id.actorIv)
        private val language = itemView.findViewById<TextView>(R.id.languageTv)
        private val role = itemView.findViewById<TextView>(R.id.roleTv)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(voiceActor: VoiceActor) {
            characterName.text = voiceActor.character_name
            actorName.text = voiceActor.actor_name
            language.text = voiceActor.language
            role.text = voiceActor.role

           Glide.with(context).load(voiceActor.characterImageUrl).into(characterImage)
            Glide.with(context).load(voiceActor.actorImageUrl).into(actorImage)


        }

        override fun onClick(p0: View?) {
            val movie = voiceActors[adapterPosition]
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ACTOR_EXTRA, movie)
            context.startActivity(intent)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.voice_actor,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = voiceActors[position]
        holder.bind(movie)
    }

    override fun getItemCount() = voiceActors.size


}