package com.dmuench.healthtracker;

import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Code resourced from: http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
public class ExerciseJournal extends AppCompatActivity {
    protected ExerciseDatabase exerciseDatabase;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Stores exercises in local and server databases
    List<Exercise> serverDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_journal);
        volleyRequest();

    }

    public void addExerciseEntry(View v) {
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

    public void recycleRenderer() {
        mRecyclerView = (RecyclerView) findViewById(R.id.journal_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // Create a singleton database
        exerciseDatabase = Room.databaseBuilder(getApplicationContext(),
                ExerciseDatabase.class, "exercise_db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        // combine both servers
        serverDB.addAll(exerciseDatabase.exerciseDao().getAll());

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // define an adapter
        mAdapter = new MyAdapter(serverDB);
//        mAdapter = new MyAdapter(exerciseDatabase.exerciseDao().getAll());

        mRecyclerView.setAdapter(mAdapter);

        if (exerciseDatabase.exerciseDao().getAll().isEmpty()) {
            Exercise newExercise = new Exercise("Push-ups", "25", "Plank your body and push yourself up and down", "DTG");
            exerciseDatabase.exerciseDao().addExercise(newExercise);
        }
    }


    // fetching things from the database
    public void volleyRequest() {
        final TextView mTextView = (TextView) findViewById(R.id.text);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://health-tracker-web-dm.herokuapp.com/exercises";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Exercise>>() {
                        }.getType();
                        List<Exercise> serverResponse = gson.fromJson(response, listType);
                        serverDB = serverResponse;

                        // displays data from both local and server databases
                        recycleRenderer();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void saveToServerDB(final String title, final String quantity, final String description) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://exercise-health-app.herokuapp.com/exercises";

        //The following is going to request a string response from the url.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Journal.getServer", "added to server db");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Journal.getServer", error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", title);
                params.put("quantity", quantity);
                params.put("description", description);

                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
