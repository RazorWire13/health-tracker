package com.dmuench.healthtracker;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Exercises {
    @PrimaryKey
    private long exerciseId;
    private String exerciseTitle;
    private String exerciseQuantity;
    private String exerciseDescription;
    private String exerciseTimestamp;

    public Exercises() {
    }

    public Exercises(String exerciseTitle, String exerciseQuantity, String exerciseDescription) {
        this.exerciseTitle = exerciseTitle;
        this.exerciseQuantity = exerciseQuantity;
        this.exerciseDescription = exerciseDescription;
//        this.exerciseTimestamp = new Date(),toString();
    }

    public long getExerciseId() { return exerciseId; }
    public void setExerciseId(long exerciseId) { this.exerciseId = exerciseId; }

    public String getExerciseTitle() { return exerciseTitle; }
    public void setExerciseTitle (String exerciseTitle) { this.exerciseTitle = exerciseTitle; }

    public String getExerciseQuantity() { return exerciseQuantity; }
    public void setExerciseQuantity (String exerciseQuantity) { this.exerciseQuantity = exerciseQuantity; }

    public String getExerciseDescription() { return exerciseDescription; }
    public void setExerciseDescription (String exerciseDescription) { this.exerciseDescription = exerciseDescription; }

    public String getExerciseTimestamp() { return exerciseTimestamp; }
    public void setExerciseTimestamp (String exerciseTimestamp) { this.exerciseTimestamp = exerciseTimestamp; }
}
