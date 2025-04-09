package com.cst338.gymlog.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cst338.gymlog.database.GymLogRepository;
import com.cst338.gymlog.database.entities.GymLog;

import java.util.List;

public class GymLogViewModel extends AndroidViewModel {
    private final GymLogRepository repository;
    private final LiveData<List<GymLog>> allLogById;
    public GymLogViewModel(Application application, int userId){
        super(application);
        repository = GymLogRepository.getRepository(application);
        allLogById = repository.getAllLogsByUserIdLiveData(userId);
    }

    public GymLogRepository getRepository() {
        return repository;
    }

    public void insert(GymLog log){
        repository.insertGymLog(log);
    }
}
