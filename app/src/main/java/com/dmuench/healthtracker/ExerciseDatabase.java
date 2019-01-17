package com.dmuench.healthtracker;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Exercise.class}, version = 1, exportSchema = false)
public abstract class ExerciseDatabase extends RoomDatabase {
    public abstract ExerciseDao exerciseDao() ;
}
