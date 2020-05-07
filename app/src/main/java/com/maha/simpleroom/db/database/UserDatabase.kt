package com.maha.simpleroom.db.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.maha.simpleroom.db.dao.userDao
import com.maha.simpleroom.db.entity.User

@Database(entities = [User::class],version = 2)
abstract class UserDatabase : RoomDatabase() {



    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_db"
                ).allowMainThreadQueries().addMigrations(aMig1_2).build()
                INSTANCE = instance
                return instance
            }
        }


        val aMig1_2:Migration=object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user "
                        + " ADD COLUMN usertype TEXT NOT NULL DEFAULT 'customer'")
            }

        }

    }




    abstract fun userDao(): userDao
}