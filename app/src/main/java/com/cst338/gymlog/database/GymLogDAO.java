package com.cst338.gymlog.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cst338.gymlog.database.entities.GymLog;

import java.util.List;

@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GymLog gymlog);

    @Query("SELECT * FROM " + GymLogDatabase.GYM_LOG_TABLE + " ORDER BY date DESC")
    List<GymLog> getAllRecords();
}
