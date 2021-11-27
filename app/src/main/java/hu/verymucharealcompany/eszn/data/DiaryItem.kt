package hu.verymucharealcompany.eszn.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "diaryitem")
data class DiaryItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "weight") var weight: Double,
    @ColumnInfo(name = "date") var date: Long
)


//class Converters{
//    @TypeConverter
//    fun fromTimestamp(value: Long?): Date?{
//        return value?.let{ Date(it) }
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date: Date?): Long? {
//        return date?.time?.toLong()
//    }
//
//}