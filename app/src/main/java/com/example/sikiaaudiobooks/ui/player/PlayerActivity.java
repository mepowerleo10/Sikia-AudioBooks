package com.example.sikiaaudiobooks.ui.player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sikiaaudiobooks.R;

public class PlayerActivity extends AppCompatActivity {
    Button playPauseBtn;
    MediaPlayer mediaPlayer;
    boolean playing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playPauseBtn = findViewById(R.id.mediaplayer_button_play_pause);
        playPauseBtn.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayPauseStates();
            }
        });

        initializeMediaPlayer();
    }

    private void initializeMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.alicewonderland);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        changePlayPauseStates();
    }

    private void changePlayPauseStates() {
        if (mediaPlayer.isPlaying()) {
            playPauseBtn.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
            mediaPlayer.pause();
        } else {
            playPauseBtn.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            mediaPlayer.start();
        }
    }

    public void closeWindow(View view) {
        finish();
    }
}