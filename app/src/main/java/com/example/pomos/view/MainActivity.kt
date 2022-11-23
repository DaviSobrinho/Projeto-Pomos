package com.example.pomos.view

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.view.View.OnLongClickListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pomos.R
import com.example.pomos.database.AppDatabase
import com.example.pomos.databinding.ActivityMainBinding
import com.example.pomos.model.TimerService
import com.example.pomos.viewmodel.MainActivityRecyclerViewAdapter
import com.google.android.material.button.MaterialButton
import kotlin.math.roundToInt


private var boolFoco: Boolean = true
private lateinit var descansoLimit: String
private lateinit var binding: ActivityMainBinding
private var timerStarted = false
private lateinit var serviceIntent: Intent
private var time = 1500.0
private var timeLimit = 1500.0
private lateinit var timeLimitHolder : String
private lateinit var mp : MediaPlayer
var volumeMuted = false

class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        val countdown = R.raw.countdown
        mp = MediaPlayer.create(applicationContext, countdown)
        setContentView(view)
        iniciaDialogMaterialButton(binding.activityMainMaterialbutton1,AddTarefaDialog())
        iniciaDialogImageButton(binding.activityMainImageview2, AddTarefaDialog())
        iniciaDialogMaterialButton(binding.activityMainMaterialbutton4, ChooseTarefaDialog())

        configuraRecyclerView()

        configuraSetas(binding.activityMainImageview3,true,binding.activityMainTextView1)
        configuraSetas(binding.activityMainImageview4,false,binding.activityMainTextView1)

        binding.activityMainMaterialbutton2.setOnClickListener{
            binding.activityMainMaterialbutton2.startAnimation(AnimationUtils.loadAnimation
                (applicationContext,androidx.appcompat.R.anim.abc_popup_enter))
            if(mp.isPlaying){
                mp.stop()
                mp.reset()
                val countdown = R.raw.countdown
                mp = MediaPlayer.create(applicationContext, countdown)
            }
            startStopTimer()
        }
        binding.activityMainMaterialbutton3.setOnClickListener{
            binding.activityMainMaterialbutton3.startAnimation(AnimationUtils.loadAnimation
                (applicationContext,androidx.appcompat.R.anim.abc_popup_enter))
            if(mp.isPlaying){
                mp.stop()
                mp.reset()
                val countdown = R.raw.countdown
                mp = MediaPlayer.create(applicationContext, countdown)
            }
            resetTimer()
        }
        serviceIntent = Intent(applicationContext,TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
        configuraSetasCiclos(binding.activityMainTextView2,true,binding.activityMainImageview5)
        configuraSetasCiclos(binding.activityMainTextView2,false,binding.activityMainImageview6)
        binding.activityMainImageview9.setOnClickListener{
            binding.activityMainImageview9.startAnimation(AnimationUtils.loadAnimation(applicationContext,androidx.appcompat.R.anim.abc_popup_enter))
        }
    }


    private fun startStopTimer() {
        if (timerStarted){
            stopTimer()
        }else{
            startTimer()
        }
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIMER_EXTRA, time)
        startService(serviceIntent)
        binding.activityMainMaterialbutton2.text = "Pausar"
        timerStarted = true
        binding.activityMainImageview5.drawable.setTint(getColor(R.color.light_yellow_2))
        binding.activityMainImageview6.drawable.setTint(getColor(R.color.light_yellow_2))
        binding.activityMainImageview3.drawable.setTint(getColor(R.color.light_green_grey))
        binding.activityMainImageview4.drawable.setTint(getColor(R.color.light_green_grey))
    }

    private fun stopTimer() {
        serviceIntent.putExtra(TimerService.TIMER_EXTRA, time)
        stopService(serviceIntent)
        binding.activityMainMaterialbutton2.text = "Iniciar"
        timerStarted = false
        binding.activityMainImageview5.drawable.setTint(getColor(R.color.dark_grey))
        binding.activityMainImageview6.drawable.setTint(getColor(R.color.dark_grey))
        binding.activityMainImageview3.drawable.setTint(getColor(R.color.dark_grey))
        binding.activityMainImageview4.drawable.setTint(getColor(R.color.dark_grey))
        timeLimit = time
    }

    private fun resetTimer() {
        stopTimer()
        try{
            if (boolFoco){
                timeLimit = timeLimitHolder.toDouble()
                time = timeLimit
                binding.activityMainTextView1.text = getTimeStringFromDouble(timeLimit)
            }else{
                timeLimit = descansoLimit.toDouble()
                time = timeLimit
                binding.activityMainTextView1.text = getTimeStringFromDouble(timeLimit)

            }
        }catch (exception : Exception){
            time = 1500.0
            timeLimit = time
            binding.activityMainTextView1.text = getTimeStringFromDouble(time)
        }
    }

    private val updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIMER_EXTRA,1500.0)
            binding.activityMainTextView1.text = getTimeStringFromDouble(time)
            if (time == 3.0 && timerStarted){
                if(mp.isPlaying){
                    mp.stop()
                    mp.reset()
                }
                val countdown = R.raw.countdown
                mp = MediaPlayer.create(applicationContext, countdown)
                mp.start()
            }
            if (time == 2.0 && timerStarted || time == 1.0 && timerStarted || time == 3.0 && timerStarted){
                binding.activityMainTextView1.setTextColor(getColor(R.color.holo_red_dark))
            }else{
                binding.activityMainTextView1.setTextColor(getColor(R.color.light_green))
            }

            if (time < 1 ){
                time = 0.0
                binding.activityMainTextView1.text = getTimeStringFromDouble(time)
                try{
                    val text = binding.activityMainTextView2.text.toString()
                    if(text.lastIndex == 10 && text[8].digitToInt() < text[10].digitToInt()){
                        if (boolFoco) {
                            boolFoco = false
                            binding.activityMainTextView3.text = "DESCANSO"
                            binding.activityMainTextView3.setTextColor(getColor(R.color.teal_200))
                            resetTimer()
                            startTimer()
                            return
                        }else{
                            boolFoco = true
                            binding.activityMainTextView3.text = "FOCO"
                            binding.activityMainTextView3.setTextColor(getColor(R.color.red))
                            resetTimer()
                            startTimer()
                            binding.activityMainTextView2.text = text.replaceRange(8,9,(text[8].digitToInt()+1).toString())
                            return
                        }
                        }else{
                            if (text.lastIndex == 11 && text[8].digitToInt() < 10){
                                if (boolFoco) {
                                    boolFoco = false
                                    binding.activityMainTextView3.setTextColor(getColor(R.color.teal_200))
                                    binding.activityMainTextView3.text = "DESCANSO"
                                    resetTimer()
                                    startTimer()
                                    return
                                }else{
                                    boolFoco = true
                                    binding.activityMainTextView3.text = "FOCO"
                                    binding.activityMainTextView3.setTextColor(getColor(R.color.red))
                                    resetTimer()
                                    startTimer()
                                    binding.activityMainTextView2.text = text.replaceRange(8,9,(text[8].digitToInt()+1).toString())
                                    return
                                }
                            }else{
                                if (text.lastIndex == 12 ){
                                    if (boolFoco) {
                                        boolFoco = false
                                        binding.activityMainTextView3.setTextColor(getColor(R.color.teal_200))
                                        binding.activityMainTextView3.text = "DESCANSO"
                                        resetTimer()
                                        startTimer()
                                        return
                                    }else{
                                        boolFoco = true
                                        binding.activityMainTextView3.text = "FOCO"
                                        binding.activityMainTextView3.setTextColor(getColor(R.color.red))
                                        resetTimer()
                                        binding.activityMainTextView2.text = text.replaceRange(8,10,"1")
                                        return
                                    }
                                }else{
                                    if (text[8] == text[10]){
                                        if (boolFoco) {
                                            boolFoco = false
                                            binding.activityMainTextView3.setTextColor(getColor(R.color.teal_200))
                                            binding.activityMainTextView3.text = "DESCANSO"
                                            resetTimer()
                                            startTimer()
                                            return
                                        }else{
                                            boolFoco = true
                                            binding.activityMainTextView3.text = "FOCO"
                                            binding.activityMainTextView3.setTextColor(getColor(R.color.red))
                                            resetTimer()
                                            binding.activityMainTextView2.text = text.replaceRange(8,9,"1")
                                            return
                                        }
                                    }
                                }
                            }
                        }

                    }catch (exception : Exception){

                    }
                    if(timerStarted){
                        stopTimer()
                    }
                    if ( time > 3599){
                        time = 3599.0
                        binding.activityMainTextView1.text = getTimeStringFromDouble(time)
                    }
                }
        }
    }

    private fun raiseTimer(){
        if (!timerStarted){
            timeLimit++
            time = timeLimit

            if (timeLimit > 3599){
                timeLimit = 3599.0
                time = 3599.0
            }
            binding.activityMainTextView1.text = getTimeStringFromDouble(timeLimit)
        }
    }
    private fun decreaseTimer() {
        if (!timerStarted){
            timeLimit--
            time = timeLimit

            if (timeLimit < 0){
                timeLimit = 0.0
                time = 0.0
            }
            binding.activityMainTextView1.text = getTimeStringFromDouble(timeLimit)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val minutes = resultInt % 3600 / 60
        val seconds = resultInt % 3600 % 60
        return makeTimeString(minutes,seconds)
    }

    private fun makeTimeString(minutes: Int, seconds: Int): String = String.format("%02d:%02d", minutes, seconds)


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        configuraRecyclerView()
    }
    fun configuraRecyclerView(){
        val adapter = MainActivityRecyclerViewAdapter(context = this)
        val db = AppDatabase.instancia(this)
        adapter.refresh(db.funDao().queryAllTarefa())
        val recyclerView = findViewById<RecyclerView>(R.id.activity_main_recyclerview_1 )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    fun iniciaDialogMaterialButton(
        materialButton: MaterialButton,
        dialogFragment: DialogFragment
    ){
        materialButton.setOnClickListener(){
            materialButton.startAnimation(AnimationUtils.loadAnimation
                (applicationContext,androidx.appcompat.R.anim.abc_tooltip_enter))
            if(this.supportFragmentManager.fragments.isEmpty()){
                dialogFragment.show(supportFragmentManager, "CustomFragment")
            }
        }
    }
    fun trocaTarefa(nome: String, ciclos : Int, foco : String, descanso : String){
        stopTimer()
        binding.activityMainMaterialbutton4.text = nome
        val focot = foco.replace(":","")
        timeLimit = ((focot.substring(0,2).toDouble() * 60)+(focot.substring(2,4).toDouble()))
        time = timeLimit
        binding.activityMainTextView1.text = getTimeStringFromDouble(timeLimit)
        val descansot = descanso.replace(":","")
        descansoLimit = ((descansot.substring(0,2).toDouble() * 60)+(descansot.substring(2,4).toDouble())).toString()
        val ciclo = ("Ciclos: 1/$ciclos")
        binding.activityMainTextView2.text = ciclo
        timeLimitHolder = timeLimit.toString()
        binding.activityMainTextView3.text = "FOCO"
        binding.activityMainTextView3.setTextColor(getColor(R.color.red))
    }
    fun iniciaDialogImageButton(
        imageView: ImageView,
        dialogFragment: DialogFragment
    ){
        imageView.setOnClickListener(){
            binding.activityMainMaterialbutton1.startAnimation(AnimationUtils.loadAnimation
                (applicationContext,androidx.appcompat.R.anim.abc_popup_enter))
            dialogFragment.show(supportFragmentManager, "CustomFragment")
        }
    }
    private fun configuraSetasCiclos(textView: TextView, boolean: Boolean, imageView: ImageView){
        imageView.setOnClickListener{
            imageView.startAnimation(AnimationUtils.loadAnimation
                (applicationContext,androidx.appcompat.R.anim.abc_popup_enter))
            val text = textView.text.toString()
            if (boolean){
                if(text.lastIndex == 10 && text[8].digitToInt() < text[10].digitToInt()){
                    textView.text = text.replaceRange(8,9,(text[8].digitToInt()+1).toString())
                }
                if (text.lastIndex == 11 && text[8].digitToInt() < 10){
                    textView.text = text.replaceRange(8,9,(text[8].digitToInt()+1).toString())
                }
            }else{
                if (text.lastIndex == 12 && text[8]+text[9].toString() == "10"){
                    textView.text = text.replaceRange(8,10,"9")
                }
                if(text.lastIndex == 10 && text[8].digitToInt() > 1 || text.lastIndex == 11 && text[8].digitToInt() > 1) {
                    textView.text = text.replaceRange(8,9,(text[8].digitToInt()-1).toString())
                }
            }

        }
    }
    private fun configuraBotaoMute(imageView: ImageView){
        imageView.setOnClickListener{
            if (volumeMuted){
                imageView.setImageResource(R.drawable.volume_on_foreground)
                imageView.startAnimation(AnimationUtils.loadAnimation
                    (applicationContext,androidx.appcompat.R.anim.abc_popup_enter))
                mp.setVolume(1F,1F)
                volumeMuted = false
            }else{
                imageView.setImageResource(R.drawable.volume_off_foreground)
                imageView.startAnimation(AnimationUtils.loadAnimation
                    (applicationContext,androidx.appcompat.R.anim.abc_popup_enter))
                mp.setVolume(0F,0F)
                volumeMuted = true
            }
        }
    }
    private fun configuraSetas(imageView: ImageView, boolean: Boolean, textView: TextView){
        imageView.setOnClickListener{
            imageView.startAnimation(AnimationUtils.loadAnimation
                (applicationContext,androidx.appcompat.R.anim.abc_popup_enter))
            if(boolean){
                raiseTimer()
            }else{
                decreaseTimer()
            }
        }
        imageView.setOnLongClickListener(object :
            OnLongClickListener {
            private val mHandler = Handler()
            private val incrementRunnable: Runnable = object : Runnable {
                override fun run() {
                    var speed = 10
                    mHandler.removeCallbacks(this)
                    if (imageView.isPressed()) {
                        if (!timerStarted){
                            if(boolean){
                                if (timeLimit > 3598){
                                    timeLimit = 3599.0
                                    time = 3599.0
                                    return
                                }
                                timeLimit += 1
                            }else{
                                if (timeLimit < 1){
                                    timeLimit = 0.0
                                    time = 0.0
                                    return
                                }
                                timeLimit -= 1
                            }
                            time = timeLimit
                            textView.text = getTimeStringFromDouble(timeLimit)
                        }
                        mHandler.postDelayed(this, speed.toLong())
                    }
                }
            }
            override fun onLongClick(view: View): Boolean {
                mHandler.postDelayed(incrementRunnable, 0)
                return true
            }
        })
    }
}
