package com.example.animelog

import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray

@Parcelize
data class VoiceActor(
    val character_name: String,
    val actor_name: String,
    val actorImageUrl: String,
    val characterImageUrl: String,
    val role: String,
    val language: String

) : Parcelable {

    companion object{
        fun fromJsonArray(VoiceActorJsonArray: JSONArray): List<VoiceActor> {
            val voiceActors = mutableListOf<VoiceActor>()
            for(i in 0 until VoiceActorJsonArray.length()){
                val voiceActorJson = VoiceActorJsonArray.getJSONObject(i)
                val character = voiceActorJson.getJSONObject("character")
                val character_images = character.getJSONObject("images")
                val character_jpg = character_images.getJSONObject("jpg")
                val actors = voiceActorJson.getJSONArray("voice_actors")
                val role = voiceActorJson.getString("role")
                var name = ""
                val blank_image = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
                var jpg_url = blank_image
                var lang = ""
                if (actors.length()>0){
                   var actor = actors.getJSONObject(0)
                    var person = actor.getJSONObject("person")
                    name = person.getString("name")
                    var images = person.getJSONObject("images")
                   var jpg = images.getJSONObject("jpg")
                    jpg_url = jpg.getString("image_url")
                    lang = actor.getString("language")

                }
//
                voiceActors.add(
                    VoiceActor(
                        character.getString("name"),
                        name,
                        jpg_url,
                        character_jpg.getString("image_url"),
                        role,
                        lang

                    )
                )
            }
            return voiceActors

        }
    }
}
