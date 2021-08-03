package pt.miguelndecarvalho.virtualtripmeter.ui.trip

import android.content.Context
import android.media.MediaPlayer
import android.media.effect.Effect
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.VibrationEffect.EFFECT_HEAVY_CLICK
import android.os.VibrationEffect.createOneShot
import android.os.Vibrator
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pt.miguelndecarvalho.virtualtripmeter.R

class TripFragment : Fragment() {

    private lateinit var tripViewModel: TripViewModel
    var total = 000.00
    var partial = 000.00
    private var toggleImperialUnits: Boolean = false
    private var baseSpeed: String = ""
    private var toggleAlertSounds: Boolean = false
    private var running = false
    private var pauseFlag = false

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tripViewModel =
            ViewModelProvider(this).get(TripViewModel::class.java)

        var mView = inflater.inflate(R.layout.fragment_trip, container, false)

        getSettings(mView)
        return mView
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getSettings(mView: View) {
        val user = Firebase.auth.currentUser
        val userID = user!!.uid
        val db = Firebase.firestore
        val doc = db.collection("userSettings").document(userID)

        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    toggleImperialUnits = document.getBoolean("toggleImperialUnits") == true
                    baseSpeed = document.getString("baseSpeed").toString()
                    toggleAlertSounds = document.getBoolean("toggleAlertSounds") == true
                    mView.findViewById<TextView>(R.id.currentVelocity).text = baseSpeed

                    if (toggleImperialUnits) {
                        mView.findViewById<TextView>(R.id.totalKM).text =
                            getString(R.string.trip_unitMiles)
                        mView.findViewById<TextView>(R.id.partialKM).text =
                            getString(R.string.trip_unitMiles)
                        mView.findViewById<TextView>(R.id.velocityUnity).text =
                            getString(R.string.trip_unitMilesHour)
                    }
                    setupButtons(mView)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun setupButtons(mView: View) {
        val totalSound = MediaPlayer.create(context, R.raw.totalalert);
        val partialSound = MediaPlayer.create(context, R.raw.partialalert)

        // Trips

        mView.findViewById<Button>(R.id.totalButton).setOnLongClickListener {
            val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)

            total = 0.0
            Log.d("Virtual", "Total=$total")
            mView.findViewById<Button>(R.id.totalButton).text = "%.2f".format(total)
            vibrator.vibrate(effect)

            if (toggleAlertSounds)
                totalSound.start()

            return@setOnLongClickListener true
        }

        mView.findViewById<Button>(R.id.partialButton).setOnClickListener {
            val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)

            partial = 0.0
            mView.findViewById<Button>(R.id.partialButton).text = "%.2f".format(partial)
            vibrator.vibrate(effect)

            if (toggleAlertSounds)
                partialSound.start()
        }

        // Velocity Controls
        mView.findViewById<ImageButton>(R.id.decrementButton).setOnClickListener {
            var temp = baseSpeed.toInt() - 1
            baseSpeed = temp.toString()
            mView.findViewById<TextView>(R.id.currentVelocity).text = baseSpeed
        }

        mView.findViewById<ImageButton>(R.id.decrementButton).setOnLongClickListener {
            var temp = baseSpeed.toInt() - 10
            baseSpeed = temp.toString()
            mView.findViewById<TextView>(R.id.currentVelocity).text = baseSpeed

            return@setOnLongClickListener true
        }

        mView.findViewById<ImageButton>(R.id.incrementButton).setOnClickListener {
            var temp = baseSpeed.toInt() + 1
            baseSpeed = temp.toString()
            mView.findViewById<TextView>(R.id.currentVelocity).text = baseSpeed
        }

        mView.findViewById<ImageButton>(R.id.incrementButton).setOnLongClickListener {
            var temp = baseSpeed.toInt() + 10
            baseSpeed = temp.toString()
            mView.findViewById<TextView>(R.id.currentVelocity).text = baseSpeed

            return@setOnLongClickListener true
        }

        //Controls
        mView.findViewById<ImageButton>(R.id.playpauseButton).setOnClickListener {

            if (!running)
            {
                mView.findViewById<ImageButton>(R.id.playpauseButton).setImageResource(R.drawable.pause_black_24dp)
                CoroutineScope(IO).launch {
                    startTrip(mView)
                }
            }
            else if (running)
            {
                pauseFlag = true
                mView.findViewById<ImageButton>(R.id.playpauseButton).setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
        }

        mView.findViewById<ImageButton>(R.id.stopButton).setOnClickListener {
            running = false
            total = 0.0
            partial = 0.0

            mView.findViewById<TextView>(R.id.totalButton).text = "%.2f".format(total)
            mView.findViewById<TextView>(R.id.partialButton).text = "%.2f".format(partial)
            mView.findViewById<ImageButton>(R.id.playpauseButton).setImageResource(R.drawable.ic_baseline_play_arrow_24)
        }

    }

    private suspend fun startTrip(mView: View){
        running = true

        while (running)
        {
            if(pauseFlag)
            {
                running = false
                pauseFlag = false
                break
            }

            var speedSecond = (baseSpeed.toFloat() * 0.5) / 3600
            total += speedSecond
            partial += speedSecond
            mView.findViewById<TextView>(R.id.totalButton).text = "%.2f".format(total)
            mView.findViewById<TextView>(R.id.partialButton).text = "%.2f".format(partial)
            delay(500)
        }
    }
}