package com.purwoko.movie.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.purwoko.movie.core.db.dao.MovieDao
import com.purwoko.movie.core.db.entity.MovieEntity

@Database(
    version = 5,
    entities = [MovieEntity::class],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}