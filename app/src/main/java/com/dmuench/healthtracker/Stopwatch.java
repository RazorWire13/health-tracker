package com.dmuench.healthtracker;

import android.os.Handler;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Stopwatch extends AppCompatActivity {

    TextView timer;
    Button start, reset;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minutes, MilliSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        // Stopwatch Code from: https://www.c-sharpcorner.com/article/creating-stop-watch-android-application-tutorial
        timer = (TextView) findViewById(R.id.tvTimer);
        start = (Button) findViewById(R.id.btStart);
        reset = (Button) findViewById(R.id.btReset);

        handler = new Handler();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creates a Start/Stop style button (credit Evan Slaton for the idea)
                if (reset.isEnabled()) {
                    start.setText("[ Stop ]");
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    reset.setEnabled(false);
                } else {
                    start.setText("[ Start ]");
                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);
                    reset.setEnabled(true);
                }
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Seconds = 0;
                Minutes = 0;
                MilliSeconds = 0;

                timer.setText("00:00:000");
            }
        });

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);

            timer.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };

}

