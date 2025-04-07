package com.cst338.gymlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cst338.gymlog.database.GymLogRepository;
import com.cst338.gymlog.database.entities.GymLog;
import com.cst338.gymlog.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.cst338.gymlog.MAIN_ACTIVITY_USER_ID";
    private ActivityMainBinding binding;
    private GymLogRepository repository;
    public static final String TAG = "SS_GYMLOG";
    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;
    int loggedInUserId = -1;

    //TODO add login information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUser();

        if(loggedInUserId == -1){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        repository = GymLogRepository.getRepository(getApplication());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());
        updateDisplay();
        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertGymLogRecord();
                updateDisplay();
            }
        });


     binding.exerciseInputEditText.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             updateDisplay();
         }
     });
    }

    private void loginUser() {
        //TODO: create login method
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
    }

    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private void insertGymLogRecord(){
        if(mExercise.isEmpty()){
            return;
        }
        GymLog log = new GymLog(mExercise, mWeight, mReps, loggedInUserId);
        repository.insertGymLog(log);
    }

    private void updateDisplay(){
        ArrayList<GymLog> alllogs = repository.getAllLogs();
        if(alllogs.isEmpty()){
            binding.logDisplayTextView.setText(R.string.nothing_to_show_time_to_hit_the_gym);
        }
        StringBuilder sb = new StringBuilder();
        for(GymLog log : alllogs){
            sb.append(log);
        }
        binding.logDisplayTextView.setText(sb.toString());
    }


    private void getInformationFromDisplay(){
        mExercise = binding.exerciseInputEditText.getText().toString();
        try {
            mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        }catch (NumberFormatException e){
            Log.d(TAG, "Error reading value from Weight edit text.");
        }
        try {
            mReps = Integer.parseInt(binding.repInputEditText.getText().toString());
        }catch (NumberFormatException e){
            Log.d(TAG, "Error reading value from Reps edit text.");
        }
    }
}