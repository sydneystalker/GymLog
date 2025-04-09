package com.cst338.gymlog.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.cst338.gymlog.database.GymLogDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Name: Sydney Stalker
 * Date: 4/2/25
 * Description: Room entity representing an individual exercise log by a user.
 */
@Entity(tableName = GymLogDatabase.GYM_LOG_TABLE)
public class GymLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String exercise;
    private double weight;
    private int reps;
    private LocalDateTime date;
    private int userId;

    /**
     * Constructs a new {@code GymLog} object with the given details and sets the timestamp to now.
     *
     * @param exercise The name of the exercise.
     * @param weight   The amount of weight used.
     * @param reps     The number of repetitions performed.
     * @param userId   The ID of the user logging the workout.
     */
    public GymLog(String exercise, double weight, int reps, int userId) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.userId = userId;
        date = LocalDateTime.now();
    }

    /**
     * Returns a string representation of the log for display.
     *
     * @return Formatted string including exercise, weight, reps, and date.
     */
    @NonNull
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
        return exercise + '\n' +
                "weight:" + weight + '\n' +
                "reps: " + reps + '\n' +
                "date: " + date.format(formatter) + '\n' +
                "=-=-=-=-=-=-=-=\n";
    }

    /**
     * Compares this GymLog to another for equality based on all fields.
     *
     * @param o The object to compare.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GymLog gymLog = (GymLog) o;
        return id == gymLog.id && Double.compare(weight, gymLog.weight) == 0 && reps == gymLog.reps && userId == gymLog.userId && Objects.equals(exercise, gymLog.exercise) && Objects.equals(date, gymLog.date);
    }

    /**
     * Generates a hash code for this GymLog.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, exercise, weight, reps, date, userId);
    }

    //Auto Generated Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
