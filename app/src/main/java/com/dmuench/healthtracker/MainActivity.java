package com.dmuench.healthtracker;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channelId";
    private int notificationId = 1;

    CarouselView carouselView;

    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    // Intent for Finger Exerciser activity view
    public void goToFingerExercise(View view) {
        Intent fingerExerciseIntent = new Intent(this, FingerExercise.class);
        startActivity(fingerExerciseIntent);
    }

    // Intent for Stopwatch activity view
    public void goToStopwatch(View view) {
        Intent stopwatchIntent = new Intent(this, Stopwatch.class);
        startActivity(stopwatchIntent);
    }

    // Resourced from: stackoverflow.com/questions/9406523/android-want-app-to-perform-tasks-every-second
    public void notifyWaterBreak(View view) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("Water Break")
                        .setContentText("Take a break - Drink water")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("DRINK WATER"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(notificationId++, mBuilder.build());
            }
        }, 5000, 5000);
    }
}

