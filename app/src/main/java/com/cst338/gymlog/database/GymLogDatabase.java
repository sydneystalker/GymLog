package com.cst338.gymlog.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cst338.gymlog.database.entities.GymLog;
import com.cst338.gymlog.MainActivity;
import com.cst338.gymlog.database.entities.User;
import com.cst338.gymlog.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Name: Sydney Stalker
 * Date: 4/2/25
 * Description: Singleton Room database for managing user and gym log data.
 */
@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {GymLog.class, User.class}, version = 5, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {

    public static final String USER_TABLE = "usertable";
    private static final String DATABASE_NAME = "GymLogdatabase";
    public static final String GYM_LOG_TABLE = "gymLogTable";
    private static volatile GymLogDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Get Database
     * Returns the singleton instance of the GymLogDatabase.
     * If the instance doesn't exist, it is created using Room's database builder.
     *
     * @param context The application context.
     * @return The singleton GymLogDatabase instance.
     */
    static GymLogDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GymLogDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    GymLogDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Callback to populate the database with default values (admin and test users)
     * when the database is first created.
     */
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    /**
     * Provides access to GymLogDAO for performing operations on the GymLog table.
     *
     * @return An instance of GymLogDAO.
     */
    public abstract GymLogDAO gymLogDAO();

    /**
     * Provides access to UserDAO for performing operations on the User table.
     *
     * @return An instance of UserDAO.
     */
    public abstract UserDAO userDAO();
}
