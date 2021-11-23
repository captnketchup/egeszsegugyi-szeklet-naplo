package hu.verymucharealcompany.eszn.data

import androidx.room.*
import java.util.*

@Dao
interface DiaryItemDao {
    @Query("SELECT * FROM diaryitem")
    fun getAll(): List<DiaryItem>

    @Query("SELECT COUNT(*) FROM diaryitem WHERE date = :targetDate")
    fun getCountOfDay(targetDate: Date): Int

    @Insert
    fun insert(shoppingItems: DiaryItem): Long

    @Update
    fun update(shoppingItem: DiaryItem)

    @Delete
    fun deleteItem(shoppingItem: DiaryItem)

//    @Query("SELECT * FROM diaryitem WHERE date = :targetDate")
//    fun findRecordsByDate(targetDate: Date): List<DiaryItem>
}
