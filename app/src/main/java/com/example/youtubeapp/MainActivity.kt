package com.example.youtubeapp

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.video_item.view.*

class MainActivity : AppCompatActivity() {


    private val videos: Array<Array<String>> = arrayOf(
    arrayOf("Numbers Game", "CiFyTc1SwPw"),
    arrayOf("Calculator", "ZbZFMDk69IA"),
    arrayOf("Guess the Phrase", "DU1qMhyKv8g"),
    arrayOf("Username and Password", "G_XYXuC8b9M"),
    arrayOf("GUI Username and Password", "sqJWyPhZkDw"),
    arrayOf("Country Capitals", "yBkRLhoVTmc"),
    arrayOf("Database Module", "E-Kb6FgMbVw"))
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var player: YouTubePlayer

    private var currentVideo = 0
    private var timeStamp = 0f

//  lateinit var playertitle: TextView
//  private var currentTitle = 0
//  lateinit var mainvideotitle: TextView
//  private lateinit var llayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkInternet()

        //videotitle = findViewById(R.id.videotitle)
        //mainvideotitle = findViewById(R.id.mainvideotitle)

        youTubePlayerView = findViewById(R.id.ytPlayer)
        youTubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                player = youTubePlayer
                player.loadVideo(videos[currentVideo][1], timeStamp)

                initializeRV()
            }
        })

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            youTubePlayerView.enterFullScreen()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            youTubePlayerView.exitFullScreen()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentVideo", currentVideo)
        outState.putFloat("timeStamp", timeStamp)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        currentVideo = savedInstanceState.getInt("currentVideo", 0)
        timeStamp = savedInstanceState.getFloat("timeStamp", 0f)
    }

    private fun initializeRV(){
        val recyclerView: RecyclerView = findViewById(R.id.rvVideos)
        recyclerView.adapter = VideoAdapter(videos, player)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    private fun checkInternet(){
        if(!connectedToInternet()){
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Internet Connection Not Found")
                .setPositiveButton("RETRY"){_, _ -> checkInternet()}
                .show()
        }
    }

    private fun connectedToInternet(): Boolean{
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}