package com.example.xinmin.listviewtest;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private String audioPath = "/data/data/com.example.xinmin.listviewtest/files/mp3.mp3";

    private MediaPlayer mMediaPlayer;

    private Button mBackButton;

    private Button mPlayButton;
    private Button mPauseButton;
    private Button mStopButton;

    private TextView mCurrentTime;
    private TextView mTotalTime;

    private SeekBar mSeekBar;

    private MyMediaListener mMyMediaListener;

    private boolean isMediaReady = false;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                if(!isMediaReady)
                    return;

                int pos = mMediaPlayer.getCurrentPosition();
                int time = mMediaPlayer.getDuration();
                int max = mSeekBar.getMax();

                mSeekBar.setProgress(pos*max/time);
                mCurrentTime.setText(generateTime(mMediaPlayer.getCurrentPosition()));
                break;
            default:
                break;
        }
      }
    };


    @Override
    protected void onDestroy() {
        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        isMediaReady = false;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);

        mPlayButton = (Button)findViewById(R.id.button_play);
        mPlayButton.setOnClickListener(this);
        mPlayButton.setEnabled(false);
        mPauseButton = (Button)findViewById(R.id.button_pause);
        mPauseButton.setOnClickListener(this);
        mStopButton = (Button) findViewById(R.id.button_stop);
        mStopButton.setOnClickListener(this);

        mBackButton = (Button)findViewById(R.id.button_back);
        mBackButton.setOnClickListener(this);

        mCurrentTime = (TextView)findViewById(R.id.currentTime);
        mTotalTime = (TextView)findViewById(R.id.totalTime);

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(this);


        mMyMediaListener = new MyMediaListener();

        File file = Environment.getExternalStorageDirectory();
        Log.d("test", file.getPath().toString());

        try {
            mMediaPlayer = MediaPlayer.create(this, R.raw.mp3);
//            mMediaPlayer.setDataSource(audioPath);
//            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(mMyMediaListener);
            mMediaPlayer.setOnCompletionListener(mMyMediaListener);
            mMediaPlayer.setOnErrorListener(mMyMediaListener);
            mMediaPlayer.setOnInfoListener(mMyMediaListener);
            mMediaPlayer.setOnSeekCompleteListener(mMyMediaListener);


        } catch (Exception e) {
            e.printStackTrace();
        }

        final int milliseconds = 100;
        new Thread(){
            @Override
            public void run(){
                while(true){
                    try {
                        sleep(milliseconds);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mHandler.sendEmptyMessage(0);
                }
            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_play:
                mMediaPlayer.start();
//                mSeekBar.setMax(mMediaPlayer.getDuration());
//                mTotalTime.setText(generateTime(mMediaPlayer.getDuration()));

                break;
            case R.id.button_pause:
                if(mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
                break;
            case R.id.button_stop:
                if(mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.reset();
                }
                mSeekBar.setProgress(0);
                mCurrentTime.setText("00:00");
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private class MyMediaListener implements MediaPlayer.OnPreparedListener,
                                MediaPlayer.OnInfoListener,
                                MediaPlayer.OnSeekCompleteListener,
                                MediaPlayer.OnCompletionListener,
                                MediaPlayer.OnErrorListener{

        @Override
        public void onPrepared(MediaPlayer mp) {
            isMediaReady = true;
            mPlayButton.setEnabled(true);
            mTotalTime.setText(generateTime(mp.getDuration()));
            mSeekBar.setMax(mMediaPlayer.getDuration());
        }

        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            return false;
        }

        @Override
        public void onSeekComplete(MediaPlayer mp) {
//            mp.seekTo();
        }

        @Override
        public void onCompletion(MediaPlayer mp) {

        }

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return false;
        }
    }


    public static String generateTime(long time) {
        int totalSeconds = (int)(time / 1000L);
        int seconds = totalSeconds % 60;
        int minutes = totalSeconds / 60 % 60;
        int hours = totalSeconds / 3600;
        return hours > 0?String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)}):String.format("%02d:%02d", new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)});
    }

}
