package pl.manfredmaniek.audioplaybackdetector

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val NOTIFICATION_CHANNEL_ID = "6YzJedB1"
private const val DEFAULT_VOLUME = "100"
private const val SELECTED_INPUT = Input.OPTICAL1_INPUT_ID

private val TAG = "[${DetectorService::class.java.name}]"

class DetectorService : Service() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val api: ReceiverService

    lateinit var audioManager: AudioManager

    private var alreadyHandled = false

    init {
        val okhttp: OkHttpClient = OkHttpClient.Builder().callTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
        val client: Retrofit =
            Retrofit.Builder().client(okhttp).baseUrl("http://${BuildConfig.RECEIVER_IP}/").build()
        api = client.create(ReceiverService::class.java)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification())

        audioManager = getSystemService(AudioManager::class.java) as AudioManager

        scope.launch {
            detectMusicPlaying()
        }

        return START_STICKY
    }

    private fun createNotification(): Notification {
        createNotificationChannel()

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Detecting audio")
            .build()
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Main", importance).apply {
            description = "For the ongoing notification"
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private suspend fun detectMusicPlaying() {
        while (true) {
            val isMusicActive = audioManager.isMusicActive

            Log.i(TAG, "Is audio playing? $isMusicActive, has been handled: $alreadyHandled")

            if (isMusicActive) {
                if (!alreadyHandled) {
                    alreadyHandled = true
                    turnReceiverOn()
                }
            } else {
                alreadyHandled = false
            }

            delay(2000)
        }
    }

    private suspend fun turnReceiverOn() {
        safeApiCall { api.turnOn() }
        safeApiCall { api.selectInput(SELECTED_INPUT) }
        safeApiCall { api.setVolume(DEFAULT_VOLUME) }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
