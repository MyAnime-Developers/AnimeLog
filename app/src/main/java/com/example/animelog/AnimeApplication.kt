package com.example.animelog

import android.app.Application

class AnimeApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}