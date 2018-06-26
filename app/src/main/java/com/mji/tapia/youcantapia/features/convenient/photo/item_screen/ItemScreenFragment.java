package com.mji.tapia.youcantapia.features.convenient.photo.item_screen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.photo.camera.CameraFragment;
import com.mji.tapia.youcantapia.features.convenient.photo.gallery.RecyclerViewAdapter;
import com.mji.tapia.youcantapia.widget.TapiaDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Usman on 2018/05/08.
 */

public class ItemScreenFragment extends BaseFragment implements ItemScreenContract.View {
    static public final String TAG = "ItemScreenFragment";
    private ItemScreenPresenter presenter;
    private RecyclerViewAdapter.DeleteCallback callback;
    private List<String> photoList = new ArrayList<>();
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;

    @BindView(R.id.returnBtn)
    Button returnBtn;

    @BindView(R.id.deleteBtn)
    Button deleteBtn;

    static public ItemScreenFragment newInstance() {
        return new ItemScreenFragment();
    }

    public void setDeleteCallback(RecyclerViewAdapter.DeleteCallback callback) {
        this.callback = callback;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new ItemScreenPresenter(this, new ItemScreenNavigator((AppCompatActivity) getActivity()), Injection.provideTTSManager(getActivity()));
        return presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_screen_fragment, null);
        ButterKnife.bind(this, view);
        int startPosition = getArguments().getInt("TappedItem");
        photoList = getArguments().getStringArrayList("PhotoList");
        deleteBtn.setOnClickListener(v -> {
            presenter.playButtonSound();
            presenter.playTextSpeech(getString(R.string.photo_delete_popup_tts));
            TapiaDialog tapiaDialog = new TapiaDialog.Builder(getContext())
                    .setType(TapiaDialog.Type.WARNING)
                    .setPositiveButton(getString(R.string.photo_delete_dialog_yes), () -> {
                        presenter.playButtonSound();
                        presenter.playTextSpeech(getString(R.string.photo_delete_popup_yes_tts));
                        int currentItem = mViewPager.getCurrentItem();
                        deleteSelectedFile(photoList.get(currentItem));
                        mCustomPagerAdapter.notifyDataSetChanged();
                        presenter.onFinish();
                    })
                    .setNegativeButton(getString(R.string.photo_delete_dialog_no), () -> {
                        presenter.playButtonSound();
                        presenter.playTextSpeech(getString(R.string.photo_delete_popup_no_tts));
                        if (getActivity() != null)
                            getActivity().onBackPressed();
                    })
                    .setMessage(getString(R.string.photo_delete_dialog_msg))
                    .show();
            tapiaDialog.setMsgTextSize(28);
            tapiaDialog.enlargeMsgBody(10);
        });
        returnBtn.setOnClickListener(v -> {
            presenter.playButtonSound();
            presenter.onFinish();
        });
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity());
        mViewPager = view.findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + startPosition);
        return view;
    }

    private class CustomPagerAdapter extends PagerAdapter {
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return photoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.photo_item_fragment, container, false);
            TouchImageView imageView = itemView.findViewById(R.id.imageView);
            imageView.setImageURI(Uri.parse(CameraFragment.PHOTO_DIR + photoList.get(position)));
            imageView.setOnLongClickListener(v -> {
                presenter.onFinish();
                return true;
            });
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            TouchImageView imageView = view.findViewById(R.id.imageView);
            imageView.setImageURI(null);
            container.removeView(view);
        }
    }

    private void deleteSelectedFile(String fileName) {
        delete(CameraFragment.PHOTO_DIR + fileName);
        delete(CameraFragment.PHOTO_DIR_SMALL + fileName);
        callback.onDeleteFile(fileName);
        photoList.remove(fileName);
        if (photoList.size() == 0) {
            presenter.onFinish();
        }
    }

    private void delete(String fileName) {
        File file = new File(Uri.parse(fileName).getPath());
        if (file.exists()) {
            file.delete();
        }
    }
}
