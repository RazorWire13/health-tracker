package com.dmuench.healthtracker;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ExerciseJournal extends AppCompatActivity {

    private static final String DATABASE_NAME = "exercise_db";
    private ExerciseDatabase exerciseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_journal);

        exerciseDatabase = Room.databaseBuilder(getApplicationContext(),
                ExerciseDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
//                .fallbackToDesctructiveMigration()
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Exercises exercise =new Exercises();
                exercise.setExerciseId(1);
                exercise.setExerciseTitle("push-up");
                exerciseDatabase.exerciseDao().insertOneExercise(exercise);
            }
        }) .start();

    }

}
