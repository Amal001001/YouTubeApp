package com.example.youtubeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.video_item.view.*

class VideoAdapter(
    private val videoList: Array<Array<String>>,
    private val player: YouTubePlayer):
  RecyclerView.Adapter<VideoAdapter.VideoViewHolder>(){
    class VideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ytPlayer: YouTubePlayerView = itemView.ytPlayer
        val videotitle: TextView = itemView.videotitle
        var llayout: LinearLayout = itemView.llayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.video_item,
            parent,
            false
        )
        return VideoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val currentTitle = videoList[position][0]
        val currentLink = videoList[position][1]
        holder.videotitle.text = currentTitle

        holder.videotitle.setOnClickListener {
            player.loadVideo(currentLink, 0f)
        }

        holder.llayout.setOnClickListener{
          player.loadVideo(currentLink, 0f)
           // Snackbar(this,"ok",Snackbar.LENGTH_LONG).show()
        }
        //holder.ytPlayer.getPlayerUiController()

//        holder.ytPlayer.setOnClickListener {
//            player.loadVideo(currentLink, 0f)
//        }

    }

    override fun getItemCount() = videoList.size
}