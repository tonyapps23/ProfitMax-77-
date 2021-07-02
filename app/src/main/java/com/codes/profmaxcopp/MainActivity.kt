package com.codes.profmaxcopp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import com.facebook.applinks.AppLinkData
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.messaging.FirebaseMessaging
import com.codes.profmaxcopp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var player : SimpleExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        facebook()
        val logger = AppEventsLogger.newLogger(this)
        logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION)
        FacebookSdk.setIsDebugEnabled(true)
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS)
        player = SimpleExoPlayer.Builder(this).build()
        videoPlayer()
        binding.button.setOnClickListener {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@addOnCompleteListener
                }
                val token2 = task.result
                Log.d("Token", "Token: + $token2" )
            }
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onResume() {
        super.onResume()
        binding.videoView.player = player
        player.pause()

    }

    override fun onStop() {
        super.onStop()
        binding.videoView.player = player
        player.pause()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.player = player
        player.pause()
    }

    fun facebook(){
        FacebookSdk.setAutoInitEnabled(true)
        FacebookSdk.fullyInitialize()
        AppLinkData.fetchDeferredAppLinkData(
            this
        ) {
            if (it != null) {
                val bundle: Bundle? = it.getArgumentBundle()
                Log.i("DEBUG_FACEBOOK_SDK", bundle.toString())
            } else {
                Log.i("DEBUG_FACEBOOK_SDK", "AppLinkData is Null")
            }
        }
    }

    private fun videoPlayer() {
        val mediaItem: MediaItem = MediaItem.fromUri("http://video.pr0gram1.xyz/video/maxiru/maxiru.m3u8")
        binding.videoView.player = player
        binding.videoView.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
        player.setMediaItem(mediaItem)
        player.prepare()



    }
}