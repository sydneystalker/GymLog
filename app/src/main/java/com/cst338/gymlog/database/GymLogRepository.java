package com.cst338.gymlog.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cst338.gymlog.database.entities.GymLog;
import com.cst338.gymlog.MainActivity;
import com.cst338.gymlog.database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Name: Sydney Stalker
 * Date: 4/2/25
 * Description: Central repository for accessing and managing GymLog and User data from the Room database.
 */
public class GymLogRepository {
    private final GymLogDAO gymLogDAO;
    private final UserDAO userDAO;
    private ArrayList<GymLog> allLogs;
    private static GymLogRepository repository;

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param application application The application context used to get the Room database instance.
     */
    private GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<GymLog>) this.gymLogDAO.getAllRecords();
    }

    /**
     * Retrieves the singleton instance of the repository.
     * Uses a background thread to avoid blocking the main thread during initialization.
     *
     * @param application The application context.
     * @return The singleton instance of GymLogRepository.
     */
    public static GymLogRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<GymLogRepository> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<GymLogRepository>() {
                    @Override
                    public GymLogRepository call() throws Exception {
                        return new GymLogRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    /**
     * Retrieves all GymLog entries from the database on a background thread.
     *
     * @return A list of all GymLog entries or null if an error occurs.
     */
    public ArrayList<GymLog> getAllLogs() {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() throws Exception {
                        return (ArrayList<GymLog>) gymLogDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all GymLog in the repository.");
        }
        return null;
    }

    /**
     * Inserts a new GymLog record into the database on a background thread.
     *
     * @param gymLog The GymLog object to insert.
     */
    public void insertGymLog(GymLog gymLog) {
        GymLogDatabase.databaseWriteExecutor.execute(() ->
        {
            gymLogDAO.insert(gymLog);
        });
    }

    /**
     * Inserts one or more User entries into the database on a background thread.
     *
     * @param user One or more User objects to insert.
     */
    public void insertUser(User... user) {
        GymLogDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    /**
     * Retrieves a User from the database based on their username.
     * Uses LiveData to allow observation from the UI.
     *
     * @param username The username to search for.
     * @return LiveData containing the User object if found.
     */
    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    /**
     * Retrieves a User from the database based on their user ID.
     * Uses LiveData to allow observation from the UI.
     *
     * @param userId The user ID to search for.
     * @return LiveData containing the User object if found.
     */
    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    /**
     * Retrieves all GymLog entries for a specific user.
     *
     * @param loggedInUserId The ID of the user.
     * @return A list of GymLog entries for the specified user, or null if an error occurs.
     */
    public ArrayList<GymLog> getAllLogsByUserId(int loggedInUserId) {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() throws Exception {
                        return (ArrayList<GymLog>) gymLogDAO.getRecordsetUserId(loggedInUserId);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all GymLog in the repository.");
        }
        return null;
    }
}
