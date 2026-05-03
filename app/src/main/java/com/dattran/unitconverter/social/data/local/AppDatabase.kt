package com.dattran.unitconverter.social.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dattran.unitconverter.social.data.local.dao.PostsDao
import com.dattran.unitconverter.social.data.local.dao.UserDao
import com.dattran.unitconverter.social.data.local.entity.PostsEntity
import com.dattran.unitconverter.social.data.local.entity.UserEntity

// ⭐ @Database annotation
@Database(
    entities = [UserEntity::class, PostsEntity::class], // ⭐ List of entities
    version = 4, // ⭐ Database version (incremented due to schema change)
    exportSchema = false // ⭐ Don't export schema
)
//@TypeConverters(TypeConverters::class) // ⭐ Type converters (nếu có)
abstract class AppDatabase : RoomDatabase() {

    // ⭐ Abstract function cho mỗi DAO
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // ⭐ Singleton pattern
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "social_database" // ⭐ Database name
                )
                    .fallbackToDestructiveMigration() // ⭐ Delete & recreate khi version thay đổi
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}