package org.goldenage.com.goldenage

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emirim.lifecurveitshow.R
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.chart_activity_main.*


class ChartMainActivity : AppCompatActivity() {

    var controller : LifeEventController? = null
    var backPressedTime : Long = 0
    var itemTouchHelper : ItemTouchHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chart_activity_main)

        var sharedPerferences = PreferenceManager.getDefaultSharedPreferences(this)

        controller = LifeEventController(sharedPerferences)
        controller!!.loadLifeEvents()

        graphSettting()
        recyclerViewSetting()

        button_setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        var dm = applicationContext.resources.displayMetrics
        var width = dm.widthPixels
        var height = dm.heightPixels

        var dialog = PlusDialog(this, controller)
        var pm = dialog.window?.attributes
        pm?.copyFrom(dialog.window?.attributes)
        pm?.width = width / 2
        pm?.height = height / 2

        button_plus.setOnClickListener {
            dialog.show()
        }

        dialog.setOnDismissListener {
            recyclerView.adapter?.notifyDataSetChanged()
            refreshGraph()

            if (controller!!.getLifeEvents()!!.count() > 1)
            {
                itemTouchHelper!!.attachToRecyclerView(recyclerView)
            }
        }
    }

    fun recyclerViewSetting()
    {
        // 밀어서 삭제
        itemTouchHelper = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT) {
                    override fun onMove(recyclerView: RecyclerView,
                                        viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
                    ): Boolean {
                        val fromPos = viewHolder.adapterPosition
                        val toPos = target.adapterPosition

                        // move item in `fromPos` to `toPos` in adapter.
                        return true// true if moved, false otherwise
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        controller!!.removeEvent(position)
                        recyclerView.adapter?.notifyItemRemoved(position)

                        refreshGraph()

                        if (controller!!.getLifeEvents()!!.count() <= 1)
                        {
                            itemTouchHelper!!.attachToRecyclerView(null)
                        }
                    }
                })

        itemTouchHelper!!.attachToRecyclerView(recyclerView)

        // 구분선
        var dividerItemDeco = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        getDrawable(R.drawable.split_line)?.let { dividerItemDeco.setDrawable(it) }

        recyclerView.adapter = ItemRecyclerViewAdapter(controller)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(dividerItemDeco)
    }

    fun refreshGraph()
    {
        chart.xAxis.axisMinimum = controller!!.getMin().toFloat()
        chart.xAxis.axisMaximum = controller!!.getMax().toFloat()

        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    fun graphSettting()
    {
        chart.post {
            val paint = chart.getRenderer().getPaintRender()
            val height = chart.height

            val linGrad = LinearGradient(0f, 0f, 0f, height.toFloat(),
                    resources.getColor(android.R.color.holo_green_dark),
                    resources.getColor(android.R.color.holo_red_light),
                    Shader.TileMode.REPEAT)
            paint.shader = linGrad
        }

        var dataset = LineDataSet(controller!!.getLifeEventEntrys()!!, null)
        dataset.lineWidth = 5f
        dataset.circleRadius = 6f
        dataset.setCircleColor(Color.parseColor("#FFA1B4DC"))
        dataset.setCircleColorHole(android.R.color.holo_green_dark)
        dataset.color = Color.parseColor("#FFA184DC")
        dataset.setDrawCircleHole(true)
        dataset.setDrawCircles(true)
        dataset.setDrawHorizontalHighlightIndicator(false)
        dataset.setDrawHighlightIndicators(false)
        dataset.setDrawValues(false)
        dataset.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        dataset.cubicIntensity

        var lineData = LineData(dataset)
        chart.data = lineData

        var xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.textColor = Color.WHITE
        xAxis.textSize = 14f
        xAxis.setDrawLabels(false)
        xAxis.setDrawGridLines(true)
        xAxis.setDrawAxisLine(false)
        xAxis.yOffset = -10f
        //xAxis.enableGridDashedLine(8f, 24f, 0f)

        var yLAxis = chart.axisLeft
        yLAxis.isEnabled = false
        yLAxis.textColor = Color.BLACK
        yLAxis.axisMinimum = -100f
        yLAxis.axisMaximum = 100f

        var yRAxis = chart.axisRight
        yRAxis.setDrawLabels(false)
        yRAxis.setDrawAxisLine(false)
        yRAxis.setDrawGridLines(true)

        var marker = MyMarkerView(this, R.layout.test_layout)
        marker.chartView = chart
        chart.marker = marker

        chart.setVisibleXRangeMaximum(110f)
        //chart.moveViewToX(10f)
        chart.isDoubleTapToZoomEnabled = true
        chart.setDrawGridBackground(false)
        chart.description.isEnabled = false
        chart.setScaleEnabled(false)
        chart.extraBottomOffset = -100f
        chart.legend.isEnabled =false
        //chart.animateY(2000, Easing.EasingOption.EaseInCubic)
        chart.invalidate()
    }

    override fun onBackPressed() {
        var time = System.currentTimeMillis()
        var intervalTime = time - backPressedTime

        if (0 <= intervalTime && 2000 >= intervalTime)
        {
            finishAffinity()
            System.runFinalization()
            System.exit(0)
        }
        else
        {
            backPressedTime = time;
        }
    }
}
