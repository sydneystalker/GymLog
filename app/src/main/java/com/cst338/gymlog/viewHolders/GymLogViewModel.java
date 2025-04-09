package com.cst338.gymlog.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cst338.gymlog.database.GymLogRepository;
import com.cst338.gymlog.database.entities.GymLog;

import java.util.List;

public class GymLogViewModel extends AndroidViewModel {
    private final GymLogRepository repository;
    public GymLogViewModel(Application application){
        super(application);
        repository = GymLogRepository.getRepository(application);
    }

    public LiveData<List<GymLog>> getAllLogById(int userId) {
        return repository.getAllLogsByUserIdLiveData(userId);
    }

    public void insert(GymLog log){
        repository.insertGymLog(log);
    }
}
