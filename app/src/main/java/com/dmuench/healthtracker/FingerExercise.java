package com.dmuench.healthtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FingerExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_exercise);
    }

    // Finger Exerciser
    int intClick = 0;

    public void integerIncrement(View view) {
        intClick++;
        incrementDisplay(intClick);
    }

    public void incrementDisplay(int number) {
        TextView displayIncrement = (TextView)findViewById(R.id.integer_display);
        displayIncrement.setText("clicks: "+ number);
    }
}
