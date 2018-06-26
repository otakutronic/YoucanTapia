package com.mji.tapia.youcantapia.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.mji.tapia.youcantapia.R;

import java.util.List;


public final class ActivityUtils {

    public static void addFragmentToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final int frameId) {
        addFragmentToActivity(fragmentManager, fragment, frameId, null,null,null);
    }

    public static void addFragmentToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final int frameId, final String backStackName) {
        addFragmentToActivity(fragmentManager, fragment, frameId, null,null,backStackName);
    }

    public static void addFragmentToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final int frameId, final Integer entranceAnim, final Integer exitAnim) {
        addFragmentToActivity(fragmentManager, fragment, frameId, entranceAnim,exitAnim,null);
    }

    public static void addFragmentToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final int frameId, final Integer entranceAnim, final Integer exitAnim, final String backStackName) {
        if(backStackName != null) {
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (entranceAnim != null && exitAnim != null) {
                transaction.setCustomAnimations(entranceAnim,exitAnim);
            }
            transaction.add(frameId, fragment);
            transaction.addToBackStack(backStackName);
            transaction.commit();
        } else {
            if (!fragment.isAdded()) {
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(frameId, fragment);
                transaction.commit();
            }
        }
    }

    public static void addFragmentWithTagToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final int frameId, @NonNull final String tag) {
        addFragmentWithTagToActivity(fragmentManager, fragment, frameId, tag, null,null,null);
    }

    public static void addFragmentWithTagToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final int frameId, @NonNull final String tag, final Integer entranceAnim, final Integer exitAnim) {
        addFragmentWithTagToActivity(fragmentManager, fragment, frameId, tag, entranceAnim,exitAnim,null);
    }

    public static void addFragmentWithTagToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final int frameId, @NonNull final String tag, final String backStackName) {
        addFragmentWithTagToActivity(fragmentManager, fragment, frameId, tag, null,null,backStackName);
    }

    public static void addFragmentWithTagToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final int frameId, final String tag, final Integer entranceAnim, final Integer exitAnim,
                                                    final String backStackName) {
        if(backStackName != null) {
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (entranceAnim != null || exitAnim != null) {
                transaction.setCustomAnimations(entranceAnim==null? 0: entranceAnim, exitAnim==null? 0: exitAnim);
            }
            transaction.add(frameId, fragment, tag);
            transaction.addToBackStack(backStackName);
            transaction.commit();
        } else {
            if (!fragment.isAdded()) {
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(frameId, fragment, tag);
                transaction.commit();
            }
        }
    }

    public static void setFragmentWithTagToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final String tag, final int frameId) {
        setFragmentWithTagToActivity(fragmentManager, fragment, tag, frameId, R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out, null);
    }


    public static void setFragmentWithTagToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final String tag, final int frameId,
                                             final String backStackName) {
        setFragmentWithTagToActivity(fragmentManager, fragment, tag, frameId, null, null, backStackName);
    }

    public static void setFragmentWithTagToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final String tag, final int frameId, final int entranceAnim, final int exitAnim ) {
        setFragmentWithTagToActivity(fragmentManager, fragment, tag, frameId, entranceAnim, exitAnim, null);
    }

    public static void setFragmentWithTagToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final String tag, final int frameId, final Integer entranceAnim, final Integer exitAnim, String backStackName) {
        setFragmentWithTagToActivity(fragmentManager, fragment, tag, frameId, entranceAnim, exitAnim, null, null, backStackName);
    }

    public static void setFragmentWithTagToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final String tag, final int frameId, final Integer entranceAnim, final Integer exitAnim, final Integer popEntrance, final Integer popExit, String backStackName) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (entranceAnim != null || exitAnim != null || popEntrance != null || popExit != null) {
            transaction.setCustomAnimations(entranceAnim==null? 0: entranceAnim, exitAnim==null? 0: exitAnim, popEntrance==null? 0: popEntrance, popExit==null? 0: popExit);
        }
        transaction.replace(frameId, fragment, tag);
        if(backStackName != null) {
            transaction.addToBackStack(backStackName);
        }
        transaction.commit();
    }

    public static void removeFragmentFromActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment) {
        removeFragmentFromActivity(fragmentManager, fragment, R.animator.fade_in,R.animator.fade_out,R.animator.fade_in,R.animator.fade_out);
    }

    public static void removeFragmentFromActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, Integer enter, Integer exit, Integer popIn, Integer popOut) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(enter,exit,popIn, popOut);
        transaction.remove(fragment);
        transaction.commit();
    }
}
