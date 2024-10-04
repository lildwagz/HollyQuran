package lildwagz.com.hollyquran.data

import android.content.Context
import androidx.room.Database


import androidx.room.Room
import androidx.room.RoomDatabase
import lildwagz.com.hollyquran.R
import lildwagz.com.hollyquran.model.*


@Database(entities = arrayOf(Quran::class, LastRead::class,Bookmark::class),
    views = arrayOf(Surah::class, Juz::class, Page::class),
    version = 1)

abstract class QuranDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao

    companion object{
        @Volatile private var INSTANCE : QuranDatabase ?= null

        fun getInstance(context: Context) : QuranDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context, QuranDatabase::class.java, "quran.db")
                    .createFromInputStream {
                        context.resources.openRawResource(R.raw.quran)
                    }
                    .build()
            }
//        private fun buildDatabase(context: Context)=
//            Room.databaseBuilder(context.applicationContext,
//                QuranDatabase::class.java,"quran.db")
    }
}