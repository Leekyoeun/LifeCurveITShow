package com.emirim.lifecurveitshow.Kotlin.chart

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emirim.lifecurveitshow.R
import org.goldenage.com.goldenage.LifeEventController

class ItemRecyclerViewAdapter(_controller : LifeEventController?) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    var controller: LifeEventController? = null

    init {
        controller = _controller
    }

    override fun getItemCount(): Int {
        if (controller == null)
            return 0

        return controller!!.getLifeEvents()!!.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var view = holder as CustomViewHolder
        var list = controller!!.getLifeEvents()!!

        view.textview_desc!!.text = list[position].desc
        view.textview_age!!.text = list[position].age.toString() + "살"
        view.textview_month!!.text = list[position].month.toString() + "월"

        var color : Color? = null
        if (list[position].satisfaction > 50)
        {
            view.textview_satisfaction!!.setTextColor(Color.GREEN)
        }
        else if (list[position].satisfaction < -50)
        {
            view.textview_satisfaction!!.setTextColor(Color.RED)
        }
        view.textview_satisfaction!!.text = list[position].satisfaction.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_layout, parent, false)
        return CustomViewHolder(view)
    }

    class CustomViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var textview_age: TextView? = null
        var textview_month: TextView? = null
        var textview_desc: TextView? = null
        var textview_satisfaction: TextView? = null

        init {
            textview_age = view!!.findViewById(R.id.textView_Age)
            textview_desc = view!!.findViewById(R.id.textView_Desc)
            textview_month = view!!.findViewById(R.id.textView_Month)
            textview_satisfaction = view!!.findViewById(R.id.textView_Satisfaction)
        }
    }
}