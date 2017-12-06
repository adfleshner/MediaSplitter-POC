package com.flesh.mediasplitter

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.flesh.mediasplitter.models.MPAPeriod
import com.flesh.mediasplitter.models.MPAwesome
import android.media.MediaMetadataRetriever
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var mpa : MPAwesome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        val uri = Uri.parse("android.resource://com.flesh.mediasplitter/" + R.raw.disturbed_sound_of_silence)
        val periods = ArrayList<MPAPeriod>()
        val metaRetriever = MediaMetadataRetriever()
        metaRetriever.setDataSource(context, uri)
        val duration = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        Log.v("time", duration)
        val dur = java.lang.Long.parseLong(duration)
        periods.add(MPAPeriod("Period0", 10_000, 20_000))
        mpa = MPAwesome(context , uri, periods)
        mpa.addPeriod(30_000,40_000)
        button0.setOnClickListener {
            mpa.playPeriod(0)
        }
        button1.setOnClickListener {
            mpa.playPeriod(1)
        }
    }


    override fun onStop() {
        super.onStop()
        mpa.stopIfPlaying()
    }
}
