package org.goldenage.com.goldenage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.SeekBar
import android.widget.Toast
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.plus_dialog.*

class PlusDialog(_context : Context, _controller : LifeEventController?) : Dialog(_context)
{
    var controller : LifeEventController? = null

    init {
        controller = _controller
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.plus_dialog)

        button_cancel.setOnClickListener {
            dismiss()
        }

        button_add.setOnClickListener {
            addEvent()
        }

        seekBar.progress = 100
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView_seekbar.text = (progress - 100).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    fun addEvent()
    {
        var age = editText_Age.text.toString().toInt()
        var month = editText_Month.text.toString().toInt()
        var desc = editText_desc.text.toString()

        if (age < 0 || age > 200)
        {
            Toast.makeText(context, "나이는 0살 ~ 100살 사이를 입력해주세요",Toast.LENGTH_LONG).show()
            return
        }

        if (month < 1 || month > 12)
        {
            Toast.makeText(context, "1월과 12월 사이를 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }

        controller!!.addLifeEvent(LifeEvent(age, month, desc, seekBar.progress - 100))

        dismiss()
    }
}