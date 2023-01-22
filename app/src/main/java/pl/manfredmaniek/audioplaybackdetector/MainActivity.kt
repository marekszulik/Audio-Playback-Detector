package pl.manfredmaniek.audioplaybackdetector

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startForegroundService(Intent(this, DetectorService::class.java))
    }
}
