package com.mji.tapia.youcantapia.features.convenient.photo.gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.photo.camera.CameraFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Usman on 2018/05/01.
 */

public class GalleryFragment extends BaseFragment implements GalleryContract.View {
    static public final String TAG = "GalleryFragment";

    private GalleryPresenter presenter;
    private boolean isEnabled;
    private RecyclerViewAdapter rcAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.slideShow)
    Button slideShow;

    @BindView(R.id.trashCan)
    Button trashCan;

    @BindView(R.id.emptyView)
    View emptyView;

    @BindView(R.id.arrow_down)
    View arrow_down;

    @BindView(R.id.arrow_up)
    View arrow_up;

    static public GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    public interface EmptyCallback {
        void onEmptyView(boolean isEmpty);
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new GalleryPresenter(this, new GalleryNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.galary_fragment, null);
        ButterKnife.bind(this, view);

        presenter.playSpeech(getResources().getString(R.string.photo_list_tts));
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        rcAdapter = new RecyclerViewAdapter(getActivity());
        recyclerView.setAdapter(rcAdapter);
        rcAdapter.setEmptyCallback(isEmpty -> {
            if (!isEmpty) {
                arrow_up.setVisibility(View.GONE);
                arrow_down.setVisibility(View.GONE);
                slideShow.setEnabled(false);
                slideShow.setTextColor(getActivity().getResources().getColor(R.color.lt_gray));
                slideShow.setBackgroundTintList(getResources().getColorStateList(R.color.lt_gray));
                trashCan.setEnabled(false);
                trashCan.setTextColor(getActivity().getResources().getColor(R.color.lt_gray));
                DrawableCompat.setTint(DrawableCompat.wrap(trashCan.getCompoundDrawables()[0]), getResources().getColor(android.R.color.darker_gray));
                DrawableCompat.setTint(DrawableCompat.wrap(slideShow.getCompoundDrawables()[0]), getResources().getColor(android.R.color.darker_gray));
            }
            emptyView.setVisibility(!isEmpty ? View.VISIBLE : View.GONE);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager=GridLayoutManager.class.cast(recyclerView.getLayoutManager());
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition();
                View v1 = recyclerView.getChildAt(0);
                View v2 = recyclerView.getChildAt(visibleItemCount - 1);
                int top = (v1 == null) ? 0 : (v1.getTop() - recyclerView.getPaddingTop());
                int bottom = (v2 == null) ? 0 : (v2.getBottom() - recyclerView.getPaddingBottom());
                if (totalItemCount < 3 || (firstVisibleItem == 0 && top == 0)) {
                    arrow_up.setVisibility(View.INVISIBLE);
                } else {
                    arrow_up.setVisibility(View.VISIBLE);
                }

                if (totalItemCount < 3 || (firstVisibleItem + visibleItemCount >= totalItemCount && bottom == recyclerView.getHeight())) {
                    arrow_down.setVisibility(View.INVISIBLE);
                } else {
                    arrow_down.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        slideShow.setOnClickListener(v -> {
            presenter.playButtonSound();
            if (isGalleryEmpty()) {
                Toast.makeText(getActivity(), "You have no photo in the Gallery to slide show", Toast.LENGTH_SHORT).show();
            } else {
                presenter.onSlideShowSelect();
            }
        });

        trashCan.setOnClickListener(v -> {
            presenter.playButtonSound();
            if (isGalleryEmpty()) {
                Toast.makeText(getActivity(), "You have no photo in the Gallery to delete", Toast.LENGTH_SHORT).show();
            } else {
                if (!isEnabled) {
                    isEnabled = true;
                    trashCan.setTextColor(getResources().getColor(R.color.red));
                    presenter.playSpeech(getResources().getString(R.string.photo_list_delete_button_tts));
                } else {
                    rcAdapter.showConfirmDialog();
                    isEnabled = false;
                    trashCan.setTextColor(getResources().getColor(R.color.black));
                }
                rcAdapter.showCheckUncheckIcon(isEnabled);
            }
        });

        ImageLoadTask loadTask = new ImageLoadTask(rcAdapter);
        loadTask.execute();

        return view;
    }

    private boolean isGalleryEmpty() {
        File fileDir = new File(CameraFragment.PHOTO_DIR);
        File[] files = fileDir.listFiles();
        return !(files != null && files.length > 0);
    }
}
