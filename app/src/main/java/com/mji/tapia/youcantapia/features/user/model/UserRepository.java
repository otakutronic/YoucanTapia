package com.mji.tapia.youcantapia.features.user.model;


import java.util.Date;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Sami on 4/9/2018.
 *
 */

public interface UserRepository {

    User getUser();

    void saveUser(User user);

    String getCustomName();

    void saveCustomName(String name);

    void setUserCreated();

    String getUserName();

}
