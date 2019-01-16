package com.dmuench.healthtracker;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

// Code resourced from: http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
public class ExerciseJournal extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ExerciseDatabase exerciseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exerciseDatabase = Room.databaseBuilder(getApplicationContext(),
                ExerciseDatabase.class, "exercise_db")
                .allowMainThreadQueries()
                .build();

       if (exerciseDatabase.exerciseDao().getAll().isEmpty()) {
            Exercises newExercise = new Exercises("Push-ups", "25", "Plank your body and push yourself up and down");
            exerciseDatabase.exerciseDao().insertOneExercise(newExercise);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            // define an adapter
            mAdapter = new MyAdapter(exerciseDatabase.exerciseDao().getAll());
            mRecyclerView.setAdapter(mAdapter);
        }
    }
