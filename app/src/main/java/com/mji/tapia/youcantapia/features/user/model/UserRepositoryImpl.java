package com.mji.tapia.youcantapia.features.user.model;

import com.mji.tapia.youcantapia.features.user.model.source.LocalDataSource;
import com.mji.tapia.youcantapia.managers.shared_preference.SharedPreferenceManager;

/**
 * Created by Sami on 4/10/2018.
 */

public class UserRepositoryImpl implements UserRepository {


    private LocalDataSource localDataSource;

    static private UserRepository instance;

    static public UserRepository getInstance(SharedPreferenceManager sharedPreferenceManager) {
        if(instance == null) {
            instance = new UserRepositoryImpl(sharedPreferenceManager);
        }
        return instance;
    }

    private UserRepositoryImpl(SharedPreferenceManager sharedPreferenceManager) {
        localDataSource = new LocalDataSource(sharedPreferenceManager);
    }


    @Override
    public User getUser() {
        return localDataSource.getUser();
    }

    @Override
    public void saveUser(User user) {
        localDataSource.saveUser(user);
    }

    @Override
    public String getCustomName() {
        return localDataSource.getCustomName();
    }

    @Override
    public void saveCustomName(String name) {
        localDataSource.saveCustomName(name);
    }

    @Override
    public void setUserCreated() {
        localDataSource.setUserCreated();
    }

    @Override
    public String getUserName() {
        return localDataSource.getUserName();
    }
}
