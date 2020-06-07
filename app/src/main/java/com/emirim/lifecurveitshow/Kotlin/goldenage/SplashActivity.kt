package org.goldenage.com.goldenage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.emirim.lifecurveitshow.R

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        RegistActivityChangeTimer()
    }

    private fun RegistActivityChangeTimer()
    {
        var thread = Thread {
            run {
                Thread.sleep(1000)
                Handler(Looper.getMainLooper()).post {
                    var activity = Intent(this, ChartMainActivity::class.java)
                    startActivity(activity)
                }
            }
        }
        thread.start()
    }
}
