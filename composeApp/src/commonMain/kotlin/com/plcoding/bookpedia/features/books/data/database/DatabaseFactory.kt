package com.plcoding.bookpedia.features.books.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<FavoriteBookDatabase>
}