package hu.verymucharealcompany.eszn.data

import androidx.room.*
import java.util.*

@Dao
interface DiaryItemDao {
    @Query("SELECT * FROM diaryitem")
    fun getAll(): List<DiaryItem>

//    @Query("SELECT COUNT(date) FROM diaryitem WHERE date LIKE :targetDate GROUP BY date ORDER BY date ASC")
    @Query("SELECT COUNT(*) FROM diaryitem WHERE date >= :date1 AND date < :date2 ORDER BY date ASC")
    fun getCountOfDay(date1: Long, date2: Long): Int

    @Insert
    fun insert(shoppingItems: DiaryItem): Long

    @Update
    fun update(shoppingItem: DiaryItem)

    @Delete
    fun deleteItem(shoppingItem: DiaryItem)


//    @Query("SELECT * FROM diaryitem WHERE date = :targetDate")
//    fun findRecordsByDate(targetDate: Date): List<DiaryItem>
}
