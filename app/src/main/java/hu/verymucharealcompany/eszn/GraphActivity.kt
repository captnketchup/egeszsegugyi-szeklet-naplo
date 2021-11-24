package hu.verymucharealcompany.eszn

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import hu.verymucharealcompany.eszn.data.DiaryListDatabase
import io.data2viz.charts.chart.Chart
import io.data2viz.charts.chart.chart
import io.data2viz.charts.chart.discrete
import io.data2viz.charts.chart.mark.area
import io.data2viz.charts.chart.quantitative
import io.data2viz.geom.Size
import io.data2viz.viz.VizContainerView
import java.util.*
import kotlin.concurrent.thread

class GraphActivity : AppCompatActivity() {
    private lateinit var database: DiaryListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityGraphBinding.inflate(layoutInflater)
        database = DiaryListDatabase.getDatabase(applicationContext)
        setContentView(MyChart(this, getData()))

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
                val currMonth = (currCalendar.get(Calendar.MONTH)+1).toString()
                val currDay = currCalendar.get(Calendar.DAY_OF_MONTH).toString()


                weeklyValues.add(WeeklyData(
                        "$currYear.$currMonth.$currDay",
                        database.diaryItemDao().getCountOfDay("$currYear.$currMonth.$currDay")
                    )
                )
                currentDate += 24 * 60 * 60 * 1000
                currCalendar.time = Date(currentDate)
            }
        }
        return weeklyValues
    }


}

class MyChart(context: Context, weekData: List<WeeklyData>) : VizContainerView(context) {
    private val chart: Chart<WeeklyData> = chart(weekData) {
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

data class WeeklyData(val day: String, val amount: Int)

const val vizSize = 500.0