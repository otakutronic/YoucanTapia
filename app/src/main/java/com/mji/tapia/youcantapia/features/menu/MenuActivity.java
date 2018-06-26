package com.mji.tapia.youcantapia.features.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.mji.tapia.youcantapia.BaseActivity;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.util.AnimationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MenuActivity extends BaseActivity implements MenuContract.View {

    static final private int CONVENIENT_MENU = 1;
    static final private int GAME_MENU = 2;
    static final private int MUSIC_MENU = 3;
    static final private int SCHEDULE_MENU = 4;
    static final private int TODAYS_MENU = 5;
    static final private int SLEEP_MODE = 6;

    MenuPresenter presenter;

    @BindView(R.id.message_notification) View message_notification;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.left_arrow)
    View leftArrow_v;

    @BindView(R.id.right_arrow)
    View rightArrow_v;

    List<Pair<Integer, String>> menuList = new ArrayList<>();

    MenuAdapter menuAdapter;

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new MenuPresenter(this, new MenuNavigator(this));
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        ButterKnife.bind(this);

        menuList.add(new Pair<>(CONVENIENT_MENU, getString(R.string.menu_convenient_label)));
        menuList.add(new Pair<>(GAME_MENU, getString(R.string.menu_game_label)));
        menuList.add(new Pair<>(MUSIC_MENU, getString(R.string.menu_music_label)));
        menuList.add(new Pair<>(SCHEDULE_MENU, getString(R.string.menu_schedule_label)));
        menuList.add(new Pair<>(TODAYS_MENU, getString(R.string.menu_todays_label)));
        menuList.add(new Pair<>(SLEEP_MODE, getString(R.string.menu_sleep_mode_label)));

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        menuAdapter = new MenuAdapter(menuList);
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(menuList.size());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.e("MENU", "scroll x: " + recyclerView.computeHorizontalScrollOffset());
                if(recyclerView.computeHorizontalScrollOffset() <= 0) {
                    leftArrow_v.setVisibility(View.INVISIBLE);
                } else {
                    leftArrow_v.setVisibility(View.VISIBLE);
                }

                if(isMaxScrollReached(recyclerView)) {
                    rightArrow_v.setVisibility(View.INVISIBLE);
                } else {
                    rightArrow_v.setVisibility(View.VISIBLE);
                }
            }
        });

        menuAdapter.setOnItemClickListener(integerStringPair -> {
            switch (integerStringPair.first) {
                case GAME_MENU:
                    presenter.onGameMenu();
                    break;
                case CONVENIENT_MENU:
                    presenter.onConvenientMenu();
                    break;
                case MUSIC_MENU:
                    presenter.onMusicMenu();
                    break;
                case SCHEDULE_MENU:
                    presenter.onScheduleMenu();
                    break;
                case TODAYS_MENU:
                    presenter.onTodaysMenu();
                    break;
                case SLEEP_MODE:
                    presenter.onSleepMode();
                    break;
            }
        });

        menuAdapter.setOnSettingClickListener(presenter::onSettingMenu);

        leftArrow_v.setOnClickListener(view -> {
            recyclerView.smoothScrollBy(-1280,0);
        });
        rightArrow_v.setOnClickListener(view -> {
            recyclerView.smoothScrollBy(1280,0);
        });

        recyclerView.setVisibility(View.INVISIBLE);

        message_notification.setOnClickListener(view -> presenter.onVoiceMessage());
        presenter.init();
    }

    @Override
    protected void onResume() {
        recyclerView.setVisibility(View.INVISIBLE);
        super.onResume();
    }

    @Override
    public Completable fadeIn() {
        menuAdapter.setEndTransition(false);
        menuAdapter.setStartTransition(true);
        return Completable.mergeArray(Completable.fromSingle(AnimationUtils.fadeIn(recyclerView, 500)), Completable.fromSingle(AnimationUtils.fadeIn(leftArrow_v, 500)));
    }

    @Override
    public Completable fadeOut() {
        menuAdapter.setStartTransition(false);
        menuAdapter.setEndTransition(true);
        return Completable.mergeArray(Completable.fromSingle(AnimationUtils.fadeOut(recyclerView, 500)), Completable.fromSingle(AnimationUtils.fadeOut(leftArrow_v, 500)));
    }

    @Override
    public void showMessageNotification() {
        message_notification.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMessageNotification() {
        message_notification.setVisibility(View.INVISIBLE);
    }

    static private boolean isMaxScrollReached(RecyclerView recyclerView) {
        int maxScroll = recyclerView.computeHorizontalScrollRange();
        int currentScroll = recyclerView.computeHorizontalScrollOffset() + recyclerView.computeHorizontalScrollExtent();
        return currentScroll >= maxScroll;
    }
}
