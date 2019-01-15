package com.dmuench.healthtracker;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Exercises.class}, version = 1, exportSchema = false)
public abstract class ExerciseDatabase extends RoomDatabase {
    public abstract ExerciseDao exerciseDao() ;
}
