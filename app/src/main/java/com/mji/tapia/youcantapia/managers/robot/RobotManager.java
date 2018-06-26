package com.mji.tapia.youcantapia.managers.robot;

import io.reactivex.Completable;

/**
 * Created by Sami on 4/6/2018.
 */

public interface RobotManager {

    enum Direction{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    Completable rotate(Direction direction, int degree);

    Completable rotateToVerticalMin();

    Completable rotateToVerticalMax();

    int getDefaultVerticalPosition();

    void setDefaultVerticalPosition(int position);

    String getSerialNumber();

    Completable clearAppsData();
}
