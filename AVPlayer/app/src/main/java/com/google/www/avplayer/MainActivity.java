package com.google.www.avplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView backImage;
    ImageButton clickPlayOrPauseButton;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    TextView tvForMarquee;
    SeekBar playingDuration, volumeControl;
    int musicResource[] = {R.raw.m1, R.raw.m2, R.raw.m3};
    int arrayIndexOfMR = 0;


    public void clickPlayOrPause(View view) {
        if (!mediaPlayer.isPlaying()) {
            clickPlayOrPauseButton = (ImageButton) findViewById(R.id.playOrPause);
            clickPlayOrPauseButton.setBackgroundResource(R.drawable.pauseicon);
            mediaPlayer.start();
       } else {
        clickPlayOrPauseButton = (ImageButton) findViewById(R.id.playOrPause);
        clickPlayOrPauseButton.setBackgroundResource(R.drawable.playicon);
        mediaPlayer.pause();
        }
    }

    public void clickPrevious(View view) {
        try {
            if (arrayIndexOfMR == 2) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(this, musicResource[arrayIndexOfMR-1]);
                mediaPlayer.start();
                arrayIndexOfMR--;
            } else if (arrayIndexOfMR == 1) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(this, musicResource[arrayIndexOfMR -1]);
                mediaPlayer.start();
                arrayIndexOfMR--;
            }  else if (arrayIndexOfMR  <= 0) {
                mediaPlayer.stop();
                Toast.makeText(this, "Playlist ends", Toast.LENGTH_SHORT).show();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickNext(View view) {
        try {
            if (arrayIndexOfMR == 0) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(this, musicResource[arrayIndexOfMR +1 ]);
                mediaPlayer.start();
                arrayIndexOfMR++;
            } else if (arrayIndexOfMR == 1) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(this, musicResource[arrayIndexOfMR +1 ]);
                mediaPlayer.start();
                arrayIndexOfMR++;
            } else if (arrayIndexOfMR >= 2) {
                mediaPlayer.stop();
                Toast.makeText(this, "Playlist ends", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvForMarquee = (TextView) findViewById(R.id.scrollingText);
        tvForMarquee.setSelected(true);


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //get Audio service
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        mediaPlayer = MediaPlayer.create(this, R.raw.m1);

        volumeControl = (SeekBar) findViewById(R.id.seekBarVolume);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(currentVolume);

        //to control Volume seekBar
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        playingDuration = (SeekBar) findViewById(R.id.seekBarTime);
        playingDuration.setMax(mediaPlayer.getDuration());

        //for update timer seekBar in every 100ms
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                playingDuration.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 5000);


        //for update timer seekBar for update according to user choice
        playingDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        try {
            backImage = (ImageView) findViewById(R.id.bgImage);
            final int[] backImgRes = {R.drawable.bgone, R.drawable.bgtwo, R.drawable.bgthree};
            final int[] num = new int[1];
            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, 30000);

                    if (num[0] == 2) {
                        backImage.setImageResource(backImgRes[num[0]]);
                        num[0] = 0;
                    } else {
                        backImage.setImageResource(backImgRes[num[0]]);
                        num[0] = num[0] + 1;
                    }
                }
            };
            handler.post(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



