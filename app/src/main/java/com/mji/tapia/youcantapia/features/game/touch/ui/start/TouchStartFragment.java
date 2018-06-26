package com.mji.tapia.youcantapia.features.game.touch.ui.start;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.util.AnimationUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchStartFragment extends BaseFragment implements TouchStartContract.View {

    static public final String TAG = "TouchRankingFragment";

    TouchStartPresenter presenter;

    static public TouchStartFragment newInstance() {
        TouchStartFragment touchStartFragment = new TouchStartFragment();
        return touchStartFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new TouchStartPresenter(this, new TouchStartNavigator((AppCompatActivity) getActivity()), Injection.provideResourcesManager(getContext()), Injection.provideTTSManager(getContext()));
        return presenter;
    }

    @BindView(R.id.counter)
    TextView counter_view;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_touch_start_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    int counter;
    Disposable currentDisposable;
    @Override
    public Completable startCounter() {
        counter = 3;
        counter_view.setText(Integer.toString(counter));
        presenter.onTimeChange(counter);
        counter--;
        CompletableSubject completableSubject = CompletableSubject.create();
        try {

            currentDisposable = Completable.timer(1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> currentDisposable = AnimationUtils.fadeOut(counter_view, 500).toCompletable()
                        .subscribe(() -> currentDisposable = Completable.timer(200, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> {
                                counter_view.setText(Integer.toString(counter));
                                presenter.onTimeChange(counter);
                                counter--;
                                countDown(completableSubject);
                            })));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return completableSubject;
    }

    void countDown(CompletableSubject completableSubject) {
        currentDisposable = AnimationUtils.fadeIn(counter_view, 500).toCompletable()
                .subscribe(() -> currentDisposable = Completable.timer(500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> currentDisposable = AnimationUtils.fadeOut(counter_view, 500).toCompletable()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    if (counter > 0) {
                                        counter_view.setText(Integer.toString(counter));
                                        presenter.onTimeChange(counter);
                                        counter--;
                                        currentDisposable = Completable.timer(200, TimeUnit.MILLISECONDS).subscribe(() -> countDown(completableSubject));
                                    } else {
                                        completableSubject.onComplete();
                                    }
                                })));
    }

    @Override
    public void onPause() {
        super.onPause();
        if(currentDisposable != null && !currentDisposable.isDisposed()){
            currentDisposable.dispose();
        }
    }
}
