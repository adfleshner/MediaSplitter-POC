package com.flesh.mediasplitter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.layout_player_controls.*

class PlayerActivity : AppCompatActivity() {

    val TAG = "[${PlayerActivity::class.java}]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        onClickListeners()
    }

    private fun onClickListeners() {
        btnPlayerPlay.setOnClickListener {
            toggleSoundPlaying()
            
        }
    }

    fun toggleSoundPlaying(){
        //toggle animate the cd
        toggleAnimation()

    }

    fun toggleAnimation() {
        if(cvPlayerIcon.animationRunning){
            cvPlayerIcon.stopAnimation()
            return
        }
        cvPlayerIcon.startAnimation(5000)
    }

}
