package hu.verymucharealcompany.eszn

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import hu.verymucharealcompany.eszn.data.DiaryListDatabase
import io.data2viz.charts.core.*
import io.data2viz.charts.chart.*
import io.data2viz.charts.chart.mark.*
import io.data2viz.charts.viz.*
import io.data2viz.geom.*
import io.data2viz.viz.VizContainerView
import io.data2viz.viz.VizContainer
import java.util.*
import kotlin.concurrent.thread

class GraphActivity : AppCompatActivity() {
    private lateinit var database: DiaryListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        thread {
//            val items = database.diaryItemDao().getAll()
//        }
//        binding = ActivityGraphBinding.inflate(layoutInflater)
        runOnUiThread{

        database = DiaryListDatabase.getDatabase(applicationContext)
        val asdf = getData()
        val chartView = MyChart(this, getData())



        setContentView(chartView)
        }



        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    }

    fun getData(): List<WeeklyData> {
        val weeklyValues: MutableList<WeeklyData> = arrayListOf()
        thread {
            var currentDate = Date().time - 7 * 24 * 60 * 60 * 1000
            var currCalendar = Calendar.getInstance()

            repeat(7) {
                val currYear = currCalendar.get(Calendar.YEAR).toString()
                val currMonth =
                    (currCalendar.get(Calendar.MONTH) + 1).toString()     //for some reason the month is always one less than it's supposed to be
                val currDay = currCalendar.get(Calendar.DAY_OF_MONTH).toString()


                weeklyValues.add(
                    WeeklyData(
                        "$currYear.$currMonth.$currDay",
                        database.diaryItemDao().getCountOfDay("$currYear.$currMonth.$currDay")
                    )
                )
                currentDate += 24 * 60 * 60 * 1000
                currCalendar.time = Date(currentDate)
            }
        }

        for (i in 0 until weeklyValues.size) {
            for (j in i+1 until weeklyValues.size) {
                val iVal = weeklyValues[i].getDate()
                val jVal = weeklyValues[j].getDate()
                if(weeklyValues[j].getDate() < weeklyValues[i].getDate()){
                    val temp = weeklyValues[i]
                    weeklyValues[i] = weeklyValues[j]
                    weeklyValues[j] = temp
                }
            }
        }


        return weeklyValues
    }


}


class MyChart(context: Context, weekData: List<WeeklyData>) : VizContainerView(context) {
    private fun organizeData(weekData: List<WeeklyData>): List<WeeklyData>{
        val weeklyValues: MutableList<WeeklyData> = weekData.toMutableList()
        for (i in 0 until weeklyValues.size) {
            for (j in i+1 until weeklyValues.size) {
                val iVal = weeklyValues[i].getDate()
                val jVal = weeklyValues[j].getDate()
                if(weeklyValues[j].getDate() < weeklyValues[i].getDate()){
                    val temp = weeklyValues[i]
                    weeklyValues[i] = weeklyValues[j]
                    weeklyValues[j] = temp
                }
            }
        }

        return weeklyValues
    }

    private val chart: Chart<WeeklyData> = chart(organizeData(weekData)) {
        val asdfasdfasd = organizeData(weekData)
        size = Size(vizSize, vizSize)
        title = "Past week"

        val xAxis = discrete({ domain.day })

        val yAxis = quantitative({ domain.amount.toDouble() }) {
            name = "Number of occurrences"
        }

        area(xAxis, yAxis)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        chart.size = Size(vizSize, vizSize * h / w)
    }
}

data class WeeklyData(val day: String, val amount: Int) {
    fun getDate(): Long {
        val dateStr = this.day.split(".")
        val dateConverted = Calendar.getInstance()
        dateConverted.set(dateStr[0].toInt(), dateStr[1].toInt()-1, dateStr[2].toInt())
        return dateConverted.timeInMillis
    }
}

const val vizSize = 500.0