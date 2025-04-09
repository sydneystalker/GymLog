package com.cst338.gymlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.cst338.gymlog.database.GymLogRepository;
import com.cst338.gymlog.database.entities.User;
import com.cst338.gymlog.databinding.ActivityLoginBinding;

/**
 * Name: Sydney Stalker
 * Date: 4/2/25
 * Description: Activity responsible for handling login logic and routing to the main app interface.
 */
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private GymLogRepository repository;

    /**
     * On Create
     * Called when the activity is created. Initializes view binding and sets up
     * a click listener for the login button.
     * @param savedInstanceState Bundle containing the activity's previously saved state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
    }

    /**
     * Verify User
     * Validates the user's input and verifies credentials against the database.
     * If login is successful, navigates to MainActivity.
     */
    private void verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Username should not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.passwordLoginEditText.getText().toString();
                if (password.equals(user.getPassword())) {
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                } else {
                    toastMaker("Invalid password");
                    binding.passwordLoginEditText.setSelection(0);
                }
            } else {
                toastMaker(String.format("%s is not a valid username.", username));
                binding.userNameLoginEditText.setSelection(0);
            }
        });
    }

    /**
     * Toast Maker
     * Displays a Toast message to the user.
     * @param message The message to display.
     */
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Login Intent Factory
     * Creates an Intent to start the LoginActivity.
     * @param context Context from which the intent is created.
     * @return A configured Intent to start LoginActivity.
     */
    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}