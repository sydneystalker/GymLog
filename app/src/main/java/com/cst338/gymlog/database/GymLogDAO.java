package com.cst338.gymlog.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cst338.gymlog.database.entities.GymLog;

import java.util.List;

/**
 * Name: Sydney Stalker
 * Date: 4/2/25
 * Description: GymLogDAO provides data access methods for interacting with the GymLog table
 * in the Room database. It includes insert and query operations for GymLog entries.
 */
@Dao
public interface GymLogDAO {

    /**
     * Insert
     * Inserts a GymLog record into the database.
     * If a record with the same primary key exists, it will be replaced.
     *
     * @param gymlog The GymLog entry to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GymLog gymlog);

    /**
     * Get All Records
     * Retrieves all GymLog records in the database, ordered by date (most recent first).
     *
     * @return A list of all GymLog records.
     */
    @Query("SELECT * FROM " + GymLogDatabase.GYM_LOG_TABLE + " ORDER BY date DESC")
    List<GymLog> getAllRecords();

    /**
     * Get Records by User ID
     * Retrieves all GymLog records for a specific user, ordered by date (most recent first).
     *
     * @param loggedInUserId The ID of the user whose records should be fetched.
     * @return A list of GymLog records belonging to the specified user.
     */
    @Query("SELECT * FROM " + GymLogDatabase.GYM_LOG_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    List<GymLog> getRecordsetUserId(int loggedInUserId);
}
