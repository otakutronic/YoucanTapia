package com.mji.tapia.youcantapia.features.game.touch.ui.play;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.util.AnimationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchPlayFragment extends BaseFragment implements TouchPlayContract.View {

    static public final String TAG = "TouchPlayFragment";

    TouchPlayPresenter presenter;

    private int playTimeInMilli = 30000;
    private int smallIconNumber = 10;
    private int bigIconNumber = 50;
    private int finishDelay = 2000;
    private int intervalInMilli = (playTimeInMilli - finishDelay)/(smallIconNumber + bigIconNumber);
    private int smallIconDisplayTime =700;
    private int bigIconDisplayTime = 1400;

    private static class Icon {
        static final int BIG = 1;
        static final int SMALL = 2;
        ImageView view;
        int type;
        Disposable timeDisposable;
    }
    List<Icon> iconList;

    List<Icon> onScreenIconList;

    @BindView(R.id.countdown)
    TextView countdown_tv;

    @BindView(R.id.touch_view)
    RelativeLayout touch_rl;


    static public TouchPlayFragment newInstance() {
        return new TouchPlayFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new TouchPlayPresenter(this, new TouchPlayNavigator((AppCompatActivity) getActivity()), Injection.provideRankingRepository(getActivity()), Injection.provideTapiaAudioManager(getContext()));
        return presenter;
    }


    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_touch_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();
        countdown_tv.setText("残り時間"+ playTimeInMilli/1000 + "秒");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Completable clearScreen() {

        List<Completable> completables = new ArrayList<>();
        for (Icon ic: onScreenIconList) {
            if (ic.timeDisposable != null && !ic.timeDisposable.isDisposed()) {
                ic.timeDisposable.dispose();
            }
        }

        return Completable.merge(completables);
    }

    Disposable updateDisposable;
    Disposable countdownDisposable;
    int playTimeCountDown;
    @Override
    public void start() {
        initIconList();
        playTimeCountDown = playTimeInMilli;
        countdownDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    playTimeCountDown -= 1000;
                    countdown_tv.setText("残り時間"+ playTimeCountDown/1000 + "秒");
                    if (playTimeCountDown <= 0) {
                        presenter.onFinish();
                        countdownDisposable.dispose();
                    }
                });

        updateDisposable  = Observable.interval(intervalInMilli, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (iconList.size() > 0){
                        Icon icon = iconList.remove(0);
                        icon.view = createView(icon.type);
                        touch_rl.addView(icon.view);
                        AnimationUtils.jumpIn(icon.view);
                        onScreenIconList.add(icon);
                        if (icon.type == Icon.BIG) {
                            icon.timeDisposable = Completable.timer(bigIconDisplayTime, TimeUnit.MILLISECONDS)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        icon.view.setEnabled(false);
                                        icon.view.setColorFilter(Color.parseColor("#59E35654"));
                                        AnimationUtils.popOut(icon.view);
                                        onScreenIconList.remove(icon);
                                    });

                            icon.view.setOnTouchListener((view, motionEvent) -> {
                                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                    presenter.onBigIconClick();
                                    icon.view.setColorFilter(Color.parseColor("#594F8835"));
                                    if(icon.timeDisposable != null && !icon.timeDisposable.isDisposed()){
                                        icon.timeDisposable.dispose();
                                    }
                                    view.setEnabled(false);
                                    AnimationUtils.popOut(view);
                                    onScreenIconList.remove(icon);
                                }
                                return false;
                            });
                        }
                        else {
                            icon.timeDisposable = Completable.timer(smallIconDisplayTime, TimeUnit.MILLISECONDS)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        icon.view.setEnabled(false);
                                        icon.view.setColorFilter(Color.parseColor("#59E35654"));
                                        AnimationUtils.popOut(icon.view);
                                        onScreenIconList.remove(icon);
                                    });
                            icon.view.setOnTouchListener((view, motionEvent) -> {
                                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                                    presenter.onSmallIconClick();
                                    icon.view.setColorFilter(Color.parseColor("#594F8835"));
                                    if(icon.timeDisposable != null && !icon.timeDisposable.isDisposed()){
                                        icon.timeDisposable.dispose();
                                    }

                                    view.setEnabled(false);
                                    AnimationUtils.popOut(view);
                                    onScreenIconList.remove(icon);
                                }
                                return false;
                            });
                        }

                    } else {
                        if (updateDisposable != null && !updateDisposable.isDisposed())
                            updateDisposable.dispose();
                    }

                });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (updateDisposable != null && !updateDisposable.isDisposed()){
            updateDisposable.dispose();
        }

        if (countdownDisposable != null && !countdownDisposable.isDisposed()){
            countdownDisposable.dispose();
        }
    }

    void initIconList() {
        iconList = new ArrayList<>();
        onScreenIconList = new ArrayList<>();

        for (int i = 0; i < bigIconNumber; i++) {
            Icon icon = new Icon();
            icon.type = Icon.BIG;
            iconList.add(icon);
        }
        for (int i = 0; i < smallIconNumber; i++) {
            Icon icon = new Icon();
            icon.type = Icon.SMALL;
            iconList.add(icon);
        }
        Collections.shuffle(iconList);
    }

    ImageView createView(int type) {
        ImageView iv = new ImageView(getContext());
        iv.setImageResource(R.drawable.game_touch_icon);
        Random random = new Random();
        int rotation = random.nextInt(60);
        iv.setRotation(rotation - 30);
        RelativeLayout.LayoutParams layoutParams;
        if (type == Icon.SMALL){
            layoutParams = new RelativeLayout.LayoutParams(127,135);
        }
        else {
            layoutParams = new RelativeLayout.LayoutParams(225, 241);
        }


        Rect rect = new Rect();
        boolean hasCollision = true;
        while (hasCollision){
            int left = random.nextInt(1280 - 225);
            int top = random.nextInt(720 - 241);
            rect = new Rect(left, top, left + layoutParams.width, top+ layoutParams.height);
            hasCollision = false;
            for (Icon ic: onScreenIconList) {
                Rect icRect = new Rect(ic.view.getLeft(), ic.view.getTop(), ic.view.getRight(), ic.view.getBottom());
                if (icRect.intersect(rect))
                    hasCollision = true;
            }
        }
        layoutParams.leftMargin = rect.left;
        layoutParams.topMargin = rect.top;
        iv.setLayoutParams(layoutParams);
        iv.setVisibility(View.INVISIBLE);
        return iv;
    }
}
