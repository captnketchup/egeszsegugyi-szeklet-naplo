package hu.verymucharealcompany.eszn

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.verymucharealcompany.eszn.data.DiaryListDatabase
import io.data2viz.charts.chart.Chart
import io.data2viz.charts.chart.chart
import io.data2viz.viz.VizContainerView
import java.util.*

class GraphActivity : AppCompatActivity() {
    private lateinit var database: DiaryListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(MyChart(this))

        database = DiaryListDatabase.getDatabase(applicationContext)
    }


    fun getData(): List<WeeklyData> {
        val weeklyValues: MutableList<WeeklyData> = arrayListOf()
        var currentDate = Date().time - 7*24*60*60*1000
        repeat(7){
            weeklyValues.add(
                WeeklyData(Date(currentDate).toString(), database.diaryItemDao().getCountOfDay(Date(currentDate))))
            currentDate + 24*60*60*1000
        }
        return weeklyValues
    }

}

class MyChart(context: Context) : VizContainerView(context){
//    private val chart: Chart<WeeklyData> = chart(listOfWeek){
//
//    }
}

data class WeeklyData(val day: String, val amount: Int)

