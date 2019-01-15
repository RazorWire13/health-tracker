package com.dmuench.healthtracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insertOneExercise (Exercises exercises);
    @Insert
    void insertMultipleExercises (List<Exercises> exerciseList);
    @Query("SELECT * FROM Exercises WHERE exerciseId = :exerciseId")

    Exercises fetchOneExercisebyExerciseId (long exerciseId);
    @Update
    void updateExercises (Exercises exercises);
    @Delete
    void deleteExercise (Exercises exercises);
}
