package com.cst338.gymlog.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cst338.gymlog.database.entities.User;

import java.util.List;

/**
 * Name: Sydney Stalker
 * Date: 4/2/25
 * Description: DAO interface for handling database operations on User entities.
 */
@Dao
public interface UserDAO {
    /**
     * Inserts one or more User objects into the database.
     * If a conflict occurs (e.g. same primary key), the existing entry will be replaced.
     *
     * @param user One or more User objects to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    /**
     * Deletes a single User from the database.
     *
     * @param user The User to delete.
     */
    @Delete
    void delete(User user);

    /**
     * Retrieves a LiveData list of all users, ordered alphabetically by username.
     *
     * @return A LiveData object containing the list of all users.
     */
    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    /**
     * Deletes all users from the user table.
     */
    @Query("DELETE from " + GymLogDatabase.USER_TABLE)
    void deleteAll();

    /**
     * Retrieves a User by their username.
     *
     * @param username The username of the User to look up.
     * @return A LiveData object containing the User with the given username, or null if not found.
     */
    @Query("SELECT * from " + GymLogDatabase.USER_TABLE + " WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    /**
     * Retrieves a User by their user ID.
     *
     * @param userId The ID of the User to look up.
     * @return A LiveData object containing the User with the given ID, or null if not found.
     */
    @Query("SELECT * from " + GymLogDatabase.USER_TABLE + " WHERE id = :userId")
    LiveData<User> getUserByUserId(int userId);
}
