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
        database = DiaryListDatabase.getDatabase(applicationContext)

        val data = getData()

        val chartView = MyChart(this, data)

        runOnUiThread {

            setContentView(chartView)
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    }

    fun getData(): List<WeeklyData> {
        val weeklyValues: MutableList<WeeklyData> = arrayListOf()
//        var currentDate = Date().time - 7 * 24 * 60 * 60 * 1000
        var currCalendar = Calendar.getInstance()
        currCalendar.set(Calendar.HOUR, 0)
        currCalendar.set(Calendar.MINUTE, 0)
        currCalendar.set(Calendar.SECOND, 0)
        currCalendar.set(Calendar.MILLISECOND, 0)

        currCalendar.add(Calendar.DAY_OF_MONTH, -7)

        repeat(7) {
            val prevDay = currCalendar.clone() as Calendar
            currCalendar.add(Calendar.DAY_OF_MONTH, 1)
            weeklyValues.add(
                WeeklyData(
                    currCalendar.timeInMillis,
                    database.diaryItemDao().getCountOfDay(prevDay.timeInMillis, currCalendar.timeInMillis)
                )
            )
        }

        weeklyValues.sortBy { it.day }
        println("asdd")
        return weeklyValues
    }


}


class MyChart(context: Context, weekData: List<WeeklyData>) : VizContainerView(context) {
    private val chart: Chart<WeeklyData> = chart(weekData) {
        size = Size(vizSize, vizSize)
        title = "Past week"

        val xAxis = discrete({ dayToString(domain.day) })

        val yAxis = quantitative({ domain.amount.toDouble() }) {
            name = "Number of occurrences"
        }

        area(xAxis, yAxis)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        chart.size = Size(vizSize, vizSize * h / w)
    }

    private fun dayToString(day: Long): String {
        val cal = Calendar.getInstance()
        cal.time = Date(day)
        return "${cal.get(Calendar.YEAR)}.${cal.get(Calendar.MONTH)+1}.${cal.get(Calendar.DAY_OF_MONTH)}"
    }
}

data class WeeklyData(val day: Long, val amount: Int)

const val vizSize = 500.0