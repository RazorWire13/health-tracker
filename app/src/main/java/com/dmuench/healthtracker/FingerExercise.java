package com.dmuench.healthtracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FingerExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_exercise);
    }

    // Finger Exerciser integer incrementer
    public void integerIncrement(View view) {
        TextView displayIncrement = findViewById(R.id.integer_display);
        int intClick = Integer.parseInt(displayIncrement.getText().toString());
        intClick++;
        displayIncrement.setText(String.valueOf(intClick));
    }
}
