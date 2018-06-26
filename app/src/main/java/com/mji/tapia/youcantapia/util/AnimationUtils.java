package com.mji.tapia.youcantapia.util;

import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.mji.tapia.youcantapia.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by Sami on 3/29/2018.
 * Animation utils
 */

public class AnimationUtils {

    static public SingleSubject<View> jumpIn(View view) {
        SingleSubject<View> singleSubject = SingleSubject.create();
        AndroidSchedulers.mainThread().scheduleDirect(() -> {
            view.setVisibility(View.VISIBLE);
            Animation jumpIn = android.view.animation.AnimationUtils.loadAnimation(view.getContext(), R.anim.jump_in);
            jumpIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    singleSubject.onSuccess(view);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(jumpIn);

        });
        return singleSubject;
    }

    static public Observable<View> jumpIn(List<View> viewList) {
        return jumpIn(viewList, 300);
    }

    static public Observable<View> jumpIn(List<View> viewList, int delay) {
        PublishSubject<View> publishSubject = PublishSubject.create();
        Observable.zip(Observable.fromArray(viewList.toArray()),Observable.interval(delay, TimeUnit.MILLISECONDS), (view, timer) -> view)
                .subscribe(view -> {
                    publishSubject.onNext((View) view);
                    jumpIn((View) view)
                            .subscribe(view1 -> {
                                if(viewList.indexOf(view1) == viewList.size() - 1) {
                                    publishSubject.onComplete();
                                }
                            });
                });
        return publishSubject;
    }

    static public SingleSubject<View> jumpOut(View view) {
        SingleSubject<View> singleSubject = SingleSubject.create();
        AndroidSchedulers.mainThread().scheduleDirect(() -> {
            Animation jumpOut = android.view.animation.AnimationUtils.loadAnimation(view.getContext(), R.anim.jump_out);
            jumpOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.INVISIBLE);
                    singleSubject.onSuccess(view);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(jumpOut);
        });

        return singleSubject;
    }

    static public Observable<View> jumpOut(List<View> viewList) {
        return jumpOut(viewList, 300);
    }


    static public Observable<View> jumpOut(List<View> viewList, int delay) {
        PublishSubject<View> publishSubject = PublishSubject.create();
        Observable.zip(Observable.fromArray(viewList.toArray()),Observable.interval(delay, TimeUnit.MILLISECONDS), (view, timer) -> view)
                .subscribe(view -> {
                    publishSubject.onNext((View) view);
                    jumpOut((View) view)
                            .subscribe(view1 -> {
                                if(viewList.indexOf(view1) == viewList.size() - 1) {
                                    publishSubject.onComplete();
                                }
                            });
                });
        return publishSubject;
    }

    static public SingleSubject<View> popOut(View view) {
        SingleSubject<View> singleSubject = SingleSubject.create();
        AndroidSchedulers.mainThread().scheduleDirect(() -> {
            Animation jumpOut = android.view.animation.AnimationUtils.loadAnimation(view.getContext(), R.anim.pop_out);
            jumpOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.INVISIBLE);
                    singleSubject.onSuccess(view);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(jumpOut);
        });

        return singleSubject;
    }

    static public Single<View> fadeIn(View view) {
        return fadeIn(view, 3000);
    };

    static public Single<View> fadeIn(View view, int duration) {
        SingleSubject<View> singleSubject = SingleSubject.create();
        AndroidSchedulers.mainThread().scheduleDirect(() -> {
            view.setVisibility(View.VISIBLE);
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(duration);
//            animation.setInterpolator(new DecelerateInterpolator());
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    singleSubject.onSuccess(view);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animation);
        });

        return singleSubject;
    };

    static public Single<View> fadeOut(View view) {
        return fadeOut(view, 3000);
    };

    static public Single<View> fadeOut(View view, int duration) {
        SingleSubject<View> singleSubject = SingleSubject.create();
        AndroidSchedulers.mainThread().scheduleDirect(() -> {
            view.setVisibility(View.VISIBLE);
            Animation animation = new AlphaAnimation(1,0);
            animation.setDuration(duration);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.INVISIBLE);
                    singleSubject.onSuccess(view);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animation);
        });

        return singleSubject;
    };

    static public Observable<Animation> glow(View view) {
        return glow(view, 500);
    }
    static public Observable<Animation> glow(View view, int duration) {
        PublishSubject<Animation> publishSubject = PublishSubject.create();
        AndroidSchedulers.mainThread().scheduleDirect(() -> {
            Animation animation = new AlphaAnimation(1, 0.5f);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setDuration(duration);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Log.d("TEST_UI", "onAnimationStart");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.d("TEST_UI", "onAnimationEnd");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    Log.d("TEST_UI", "onAnimationRepeat");
                    publishSubject.onNext(animation);
                }
            });

            view.startAnimation(animation);
        });
        return publishSubject;
    }
}
