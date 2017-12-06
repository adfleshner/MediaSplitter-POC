package com.flesh.mediasplitter.models

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.util.Log
import java.util.ArrayList

/**
 * Initial Logic around the MediaSplitter idea
 * Created by aaronfleshner on 12/5/17.
 */
data class MPAwesome(var context: Context, var media: Uri?, var periods: ArrayList<MPAPeriod>?) {

    val TAG = "[${MPAwesome::class.java.simpleName}]"
    var player: MediaPlayer = MediaPlayer.create(context, media)
    var stopHandler = Handler()
    var stopChecker : Runnable? =null

    fun playPeriod(index: Int) {
        val period = getMPAPeriod(index)
        if (media != null) {
            if (period != null) {
                player.seekTo(period.start.toInt())
                stopChecker = Runnable{
                    //do something
                    Log.d(TAG, "Current Time ${player.currentPosition} : Period End: ${period.end} ")
                    if (player.isPlaying && player.currentPosition > period.end) {
                        stopIfPlaying()
                    } else {
                        if(stopChecker!=null) {
                            Log.d(TAG, "Not Yet ${player.currentPosition}")
                            stopHandler.postDelayed(stopChecker, 1000)
                        }
                    }
                }
                stopHandler.postDelayed(stopChecker, 1000)
            }
            Log.d(TAG, "Starting")
            player.start()
        }
    }

    fun addPeriod(start: Long, end: Long): Boolean {
        if (periods == null) {
            periods = ArrayList<MPAPeriod>()
        }
        return periods?.add(MPAPeriod("Period${periods?.size!! + 1}", start, end)) ?: false
    }


    fun getMPAPeriod(index: Int): MPAPeriod? = periods?.get(index)


    fun stopIfPlaying() {
        if (player.isPlaying) {
            Log.d(TAG, "Stopping")
            player.stop()
        }

    }
}



