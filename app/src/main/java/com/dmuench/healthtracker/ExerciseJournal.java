package com.dmuench.healthtracker;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
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
    protected final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 0;

    private FusedLocationProviderClient mFusedLocationClient;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Stores exercises in local and server databases
    List<Exercise> serverDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_journal);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        volleyRequest();
        getExerciseLocation();
    }

    public void addExerciseEntry(View v) {
        EditText title = findViewById(R.id.editText4);
        EditText description = findViewById(R.id.editText6);
        EditText quantity = findViewById(R.id.editText5);
        String timestamp = new Date().toString();

        String titleEntry = title.getText().toString();
        String descriptionEntry = description.getText().toString();
        String quantityEntry = quantity.getText().toString();

        // add exercise to local database
        Exercise newExercise = new Exercise(titleEntry, quantityEntry, descriptionEntry, timestamp);
        exerciseDatabase.exerciseDao().addExercise(newExercise);

        // add exercise to served database
        saveToServerDB(titleEntry, quantityEntry, descriptionEntry);

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
            Exercise newExercise = new Exercise("[initializer]", "[initializer]", "[initializer]", "DTG");
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
        String url = "https://health-tracker-web-dm.herokuapp.com/exercises";

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

    public void getExerciseLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                // MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location
                            if (location != null) {
                                // Logic to handle location object
                                // put in the GEOCODER CODE HERE
                            }
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    Log.e("LocationAccess", "PERMISSION GRANTED");
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }

}
