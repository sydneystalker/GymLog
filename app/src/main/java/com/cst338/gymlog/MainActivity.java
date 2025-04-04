package com.cst338.gymlog;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cst338.gymlog.database.GymLogRepository;
import com.cst338.gymlog.database.entities.GymLog;
import com.cst338.gymlog.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private GymLogRepository repository;
    public static final String TAG = "SS_GYMLOG";
    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertGymLogRecord();
                updateDisplay();
            }
        });
    }
    
    private void insertGymLogRecord(){
        GymLog log = new GymLog(mExercise, mWeight, mReps);
        repository.insertGymLog(log);
    }

    private void updateDisplay(){
        String currentInfo = binding.logDisplayTextView.getText().toString();
        Log.d(TAG, "current info" + currentInfo);
        String newDisplay = String.format(Locale.US,"Exercise:%s%nWeight:%.2f%nReps:%d%n=-=-=-=%n%s",mExercise,mWeight,mReps, currentInfo);
        binding.logDisplayTextView.setText(newDisplay);
        Log.i(TAG, repository.getAllLogs().toString());
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