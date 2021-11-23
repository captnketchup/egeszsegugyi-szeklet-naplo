package hu.verymucharealcompany.eszn.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DiaryItem::class], version = 1)
//@TypeConverters(value = [Converters::class])
abstract class DiaryListDatabase : RoomDatabase() {
    abstract fun diaryItemDao(): DiaryItemDao

    companion object {
        fun getDatabase(applicationContext: Context): DiaryListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                DiaryListDatabase::class.java,
                "diary-list"
            ).build();
        }
    }
}