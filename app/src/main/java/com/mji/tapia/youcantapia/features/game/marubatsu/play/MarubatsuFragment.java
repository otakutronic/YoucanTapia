package com.mji.tapia.youcantapia.features.game.marubatsu.play;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.eftimoff.androipathview.PathView;
import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.widget.TapiaDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;


/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MarubatsuFragment extends BaseFragment implements MarubatsuContract.View {

    static public final String TAG = "MarubatsuFragment";

    static private final int side_length = 495;
    static private final int square_length = 150;

    MarubatsuPresenter presenter;

    int userType = Box.CIRCLE;

    @BindView(R.id.you) TextView you_tv;
    @BindView(R.id.tapita) TextView tapita_tv;

    @BindView(R.id.square_1) PathView square1;
    @BindView(R.id.square_2) PathView square2;
    @BindView(R.id.square_3) PathView square3;
    @BindView(R.id.square_4) PathView square4;
    @BindView(R.id.square_5) PathView square5;
    @BindView(R.id.square_6) PathView square6;
    @BindView(R.id.square_7) PathView square7;
    @BindView(R.id.square_8) PathView square8;
    @BindView(R.id.square_9) PathView square9;

    @BindView(R.id.hor_line1) PathView horLine1;
    @BindView(R.id.hor_line2) PathView horLine2;
    @BindView(R.id.ver_line1) PathView verLine1;
    @BindView(R.id.ver_line2) PathView verLine2;

    Box grid[][];

    static private class Box {
        static private final int UNSET = 0;
        static private final int CIRCLE = 1;
        static private final int CROSS = 2;

        PathView view;
        int type;

        Box(PathView pathView) {
            view = pathView;
            type = UNSET;
        }
    }

    static public MarubatsuFragment newInstance() {
        return new MarubatsuFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new MarubatsuPresenter(this, new MarubatsuNavigator((AppCompatActivity) getActivity()),
                Injection.provideTapiaAudioManager(getContext()),
                Injection.provideTTSManager(getContext()),
                Injection.provideResourcesManager(getContext()),
                Injection.provideUserRepository(getContext()));
        return presenter;
    }

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_marubatsu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Completable initGame(boolean isCircle, boolean isFirst) {
        CompletableSubject completableSubject = CompletableSubject.create();

        grid = new Box[][]{
                {new Box(square1), new Box(square2), new Box(square3)},
                {new Box(square4), new Box(square5), new Box(square6)},
                {new Box(square7), new Box(square8), new Box(square9)}};
        lockUI();

        if (isCircle){
            userType = Box.CIRCLE;
            you_tv.setText(String.format(getString(R.string.game_marubatsu_you), "○"));
            tapita_tv.setText(String.format(getString(R.string.game_marubatsu_tapita), "✖"));
        } else {
            userType = Box.CROSS;
            you_tv.setText(String.format(getString(R.string.game_marubatsu_you), "✖"));
            tapita_tv.setText(String.format(getString(R.string.game_marubatsu_tapita), "○"));
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                final int x = i;
                final int y = j;
                grid[i][j].view.setOnClickListener(view -> handleClick(grid[x][y]));
            }
        }

        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(0,side_length);
        path.close();
        verLine1.setPath(path);
        Path path2 = new Path();
        path2.moveTo(0.0f, 0.0f);
        path2.lineTo(0,side_length);
        path2.close();
        verLine2.setPath(path2);

        Path path3 = new Path();
        path3.moveTo(0.0f, 0.0f);
        path3.lineTo(side_length,0);
        path3.close();
        horLine1.setPath(path3);
        Path path4 = new Path();
        path4.moveTo(0.0f, 0.0f);
        path4.lineTo(side_length,0);
        path4.close();
        horLine2.setPath(path4);

        verLine1.getSequentialPathAnimator()
                .duration(2000)
                .interpolator(new AccelerateInterpolator())
                .start();

        verLine2.getSequentialPathAnimator()
                .delay(500)
                .duration(2000)
                .interpolator(new AccelerateInterpolator())
                .start();

        horLine1.getSequentialPathAnimator()
                .duration(2000)
                .interpolator(new AccelerateInterpolator())
                .start();

        horLine2.getSequentialPathAnimator()
                .delay(500)
                .duration(2000)
                .listenerEnd(() -> {
                    if(isFirst) {
                        unlockUI();
                    } else {
                        makeMove();
                    }
                    completableSubject.onComplete();
                })
                .interpolator(new AccelerateInterpolator())
                .start();
        return completableSubject;
    }

    private void handleClick(Box box) {
        if (box.type == Box.UNSET) {
            lockUI();
            box.type = userType;
            if (userType == Box.CIRCLE){
                   drawCircle(box.view)
                           .subscribe(() -> {
                               if (!checkFinish()) {
                                   makeMove();
                               }
                           });
               } else {
                    drawCross(box.view)
                        .subscribe(() -> {
                            if (!checkFinish()) {
                                makeMove();
                            }
                        });
               }
        }
    }

    private Completable makeMove() {
        CompletableSubject completableSubject = CompletableSubject.create();

        Box[][] checkArray = new Box[][] {
                {grid[0][0], grid[0][1], grid[0][2]},
                {grid[1][0], grid[1][1], grid[1][2]},
                {grid[2][0], grid[2][1], grid[2][2]},
                {grid[0][0], grid[1][0], grid[2][0]},
                {grid[0][1], grid[1][1], grid[2][1]},
                {grid[0][2], grid[1][2], grid[2][2]},
                {grid[0][0], grid[1][1], grid[2][2]},
                {grid[0][2], grid[1][1], grid[2][0]},
        };

        Box chosenBox = null;
        for (Box[] array: checkArray) {
            chosenBox = checkHasBlockOrWin(array);
            if (chosenBox != null)
                break;
        }


        if (chosenBox == null) {
            Random random = new Random();
            int i = random.nextInt(3);
            int j = random.nextInt(3);
            while (grid[i][j].type != Box.UNSET) {
                i = random.nextInt(3);
                j = random.nextInt(3);
            }
            chosenBox = grid[i][j];
        }



        if (userType == Box.CIRCLE) {
            chosenBox.type = Box.CROSS;
            drawCross(chosenBox.view).subscribe(() -> {
               if (!checkFinish()) {
                   unlockUI();
               }
               completableSubject.onComplete();
           });
        } else {
            chosenBox.type = Box.CIRCLE;
            drawCircle(chosenBox.view).subscribe(() -> {
                if (!checkFinish()) {
                    unlockUI();
                }
                completableSubject.onComplete();
            });
        }
        return completableSubject;
    }



    private boolean checkFinish() {

        Box[][] checkArray = new Box[][] {
                {grid[0][0], grid[0][1], grid[0][2]},
                {grid[1][0], grid[1][1], grid[1][2]},
                {grid[2][0], grid[2][1], grid[2][2]},
                {grid[0][0], grid[1][0], grid[2][0]},
                {grid[0][1], grid[1][1], grid[2][1]},
                {grid[0][2], grid[1][2], grid[2][2]},
                {grid[0][0], grid[1][1], grid[2][2]},
                {grid[0][2], grid[1][1], grid[2][0]},
        };
        Pair<Boolean, Integer> result = new Pair<>(false, Box.UNSET);

        for (Box[] array: checkArray) {
            result = checkArray(array);
            if (result.first)
                break;
        }

        if (result.first) {
            //show win or lose dialog
            if (userType == result.second) {
                Log.e("MARUBATSU","win");
                presenter.onWin();
                new TapiaDialog.Builder(getContext())
                        .setType(TapiaDialog.Type.NORMAL)
                        .setMessage(getString(R.string.game_marubatsu_dialog_win))
                        .setNegativeButton(getString(R.string.game_marubatsu_dialog_stop), presenter::onStop)
                        .setPositiveButton(getString(R.string.game_marubatsu_dialog_repeat), presenter::onRepeat)
                        .show();
            } else {
                Log.e("MARUBATSU","lose");
                presenter.onLose();
                new TapiaDialog.Builder(getContext())
                        .setType(TapiaDialog.Type.NORMAL)
                        .setMessage(getString(R.string.game_marubatsu_dialog_lose))
                        .setNegativeButton(getString(R.string.game_marubatsu_dialog_stop), presenter::onStop)
                        .setPositiveButton(getString(R.string.game_marubatsu_dialog_repeat), presenter::onRepeat)
                        .show();
            }
            return true;
        } else {
            if (checkFull()) {
                //show draw dialog
                presenter.onDraw();
                new TapiaDialog.Builder(getContext())
                        .setType(TapiaDialog.Type.NORMAL)
                        .setMessage(getString(R.string.game_marubatsu_dialog_draw))
                        .setNegativeButton(getString(R.string.game_marubatsu_dialog_stop), presenter::onStop)
                        .setPositiveButton(getString(R.string.game_marubatsu_dialog_repeat), presenter::onRepeat)
                        .show();
                Log.e("MARUBATSU","draw");
                return true;
            } else {
                return false;
            }
        }
    }

    private Pair<Boolean, Integer> checkArray(Box[] array) {
        Pair<Boolean, Integer> pair = new Pair<>(true, Box.UNSET);
        for (Box anArray : array) {
            if (anArray.type == Box.UNSET) {
                pair = new Pair<>(false, pair.second);
            } else if (pair.second == Box.UNSET) {
                pair = new Pair<>(pair.first, anArray.type);
            } else if (pair.second != anArray.type) {
                pair = new Pair<>(false, pair.second);
            }
        }
        return pair;
    }

    private boolean checkFull() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].type == Box.UNSET) {
                    return false;
                }
            }
        }
        return true;
    }


    //check if can lose next turn
    private Box checkHasBlockOrWin(Box[] array) {
        Box box = null;
        int foundType = Box.UNSET;
        boolean isBlock = false;
        for (Box anArray : array) {
            if (anArray.type == Box.UNSET) {
                box = anArray;
            } else if (foundType == Box.UNSET) {
                foundType = anArray.type;
            } else if (foundType == anArray.type) {
                isBlock = true;
            }
        }
        if (isBlock)
            return box;
        else
            return null;
    }

    private void lockUI() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].view.setEnabled(false);
            }
        }
    }

    private void unlockUI() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].view.setEnabled(true);
            }
        }
    }


    static private final int boxPadding = 10;
    private Completable drawCircle(PathView view) {
        CompletableSubject completableSubject = CompletableSubject.create();

        Path path = new Path();
        path.addCircle(square_length/2, square_length/2, (square_length - view.getPathWidth())/2  - boxPadding, Path.Direction.CW);
        path.close();
        view.setPath(path);
        view.getSequentialPathAnimator()
                .duration(1000)
                .listenerEnd(completableSubject::onComplete)
                .interpolator(new DecelerateInterpolator())
                .start();
        return completableSubject;
    }
    private Completable drawCross(PathView view) {
        CompletableSubject completableSubject = CompletableSubject.create();

        List<Path> pathList = new ArrayList<>();
        Path path1 = new Path();
        path1.moveTo(boxPadding, boxPadding);
        path1.lineTo(square_length - boxPadding, square_length - boxPadding);
        path1.close();
        Path path2 = new Path();
        path2.moveTo(boxPadding, square_length - boxPadding);
        path2.lineTo(square_length - boxPadding, boxPadding);
        path2.close();
        pathList.add(path1);
        pathList.add(path2);
        view.setPaths(pathList);
        view.getPathAnimator()
                .duration(1000)
                .listenerEnd(completableSubject::onComplete)
                .interpolator(new DecelerateInterpolator())
                .start();
        return completableSubject;
    }

}
