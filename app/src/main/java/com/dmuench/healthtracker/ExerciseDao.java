package com.dmuench.healthtracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM Exercise WHERE id = :id")
    Exercise getExercise (long id);

    @Query("SELECT * FROM Exercise")
    List<Exercise> getAll();

    //Resourced from: https://stackoverflow.com/questions/5191503/how-to-select-the-last-record-of-a-table-in-sql
    @Query("SELECT * FROM exercise ORDER BY id DESC LIMIT 1 ")
    Exercise getLast();

    @Insert
    void addExercise (Exercise exercise);

    @Update
    void updateExercise (Exercise exercise);

    @Delete
    void deleteExercise (Exercise exercise);
}
