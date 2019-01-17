package com.dmuench.healthtracker;

import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;

import java.util.Date;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Code resourced from: http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
public class ExerciseJournal extends AppCompatActivity {
    protected ExerciseDatabase exerciseDatabase;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_journal);

        mRecyclerView = (RecyclerView) findViewById(R.id.journal_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // Create a singleton database
        exerciseDatabase = Room.databaseBuilder(getApplicationContext(),
                ExerciseDatabase.class, "exercise_db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // define an adapter
        mAdapter = new MyAdapter(exerciseDatabase.exerciseDao().getAll());
        mRecyclerView.setAdapter(mAdapter);

        if (exerciseDatabase.exerciseDao().getAll().isEmpty()) {
            Exercise newExercise = new Exercise("Push-ups", "25", "Plank your body and push yourself up and down", "DTG");
            exerciseDatabase.exerciseDao().addExercise(newExercise);
        }
    }
        public void addExerciseEntry (View v) {
            EditText title = findViewById(R.id.editText4);
            EditText description = findViewById(R.id.editText6);
            EditText quantity = findViewById(R.id.editText5);
            String timestamp = new Date().toString();

            String titleEntry = title.getText().toString();
            String descriptionEntry = description.getText().toString();
            String quantityEntry = quantity.getText().toString();

            Exercise newExercise = new Exercise(titleEntry, quantityEntry, descriptionEntry, timestamp);
            exerciseDatabase.exerciseDao().addExercise(newExercise);

            //got this from: https://stackoverflow.com/questions/3053761/reload-activity-in-android
            finish();
            startActivity(getIntent());
        }
}
