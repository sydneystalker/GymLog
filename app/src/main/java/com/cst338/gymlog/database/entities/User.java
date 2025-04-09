package com.cst338.gymlog.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.cst338.gymlog.database.GymLogDatabase;

import java.util.Objects;

/**
 * Name: Sydney Stalker
 * Date: 4/2/25
 * Description: Room entity for managing user account data.
 */
@Entity(tableName = GymLogDatabase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;

    /**
     * Constructs a non-admin user with the given username and password.
     *
     * @param username the user's username
     * @param password the user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
    }

    /**
     * Compares this User object with another for equality based on fields.
     *
     * @param o the object to compare with
     * @return true if the objects are equal; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    /**
     * Computes a hash code for the User object based on its fields.
     *
     * @return hash code for the User object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isAdmin);
    }

    //Auto Generated Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
