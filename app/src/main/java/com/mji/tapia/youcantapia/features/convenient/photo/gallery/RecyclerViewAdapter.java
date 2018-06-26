package com.mji.tapia.youcantapia.features.convenient.photo.gallery;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.ConvenientActivity;
import com.mji.tapia.youcantapia.features.convenient.photo.camera.CameraFragment;
import com.mji.tapia.youcantapia.features.convenient.photo.item_screen.ItemScreenFragment;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;
import com.mji.tapia.youcantapia.util.ActivityUtils;
import com.mji.tapia.youcantapia.widget.TapiaDialog;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Usman on 2018/05/08.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private Activity mActivity;
    private ArrayList<String> itemList = new ArrayList<>();
    private boolean isChecked = false;
    private ArrayList<String> checkedItemList = new ArrayList<>();
    private GalleryFragment.EmptyCallback callback;
    private boolean isDialogShowing;
    private TapiaAudioManager tapiaAudioManager;

    public RecyclerViewAdapter(Activity activity) {
        this.mActivity = activity;
        tapiaAudioManager = Injection.provideTapiaAudioManager(mActivity);
    }

    public void setAdapterData(ArrayList<String> imageList) {
        this.itemList = imageList;
        callback.onEmptyView(itemList.size() > 0);
    }

    public void setEmptyCallback(GalleryFragment.EmptyCallback callback) {
        this.callback = callback;
    }

    public interface DeleteCallback {
        void onDeleteFile(String fileName);
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_item, null);
        return new RecyclerViewHolders(layoutView, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, int position) {
        holder.photo.setImageURI(Uri.parse(CameraFragment.PHOTO_DIR_SMALL + itemList.get(position)));
        holder.photo.setOnClickListener(view -> loadItemFragment(position));
        holder.checkPhoto.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        if (!isChecked && !isDialogShowing) {
            holder.checkPhoto.setImageResource(R.drawable.uncheck);
            checkedItemList.remove(itemList.get(position));
        }
        holder.checkPhoto.setOnClickListener(v -> {
            if (checkedItemList.contains(itemList.get(position))) {
                holder.checkPhoto.setImageResource(R.drawable.uncheck);
                checkedItemList.remove(itemList.get(position));
            } else {
                holder.checkPhoto.setImageResource(R.drawable.check);
                checkedItemList.add(itemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void showCheckUncheckIcon(boolean isEnabled) {
        isChecked = isEnabled;
        notifyDataSetChanged();
    }

    private void deleteFilesList() {
        for (int i = 0; i < checkedItemList.size(); i++) {
            String fileName = checkedItemList.get(i);
            delete(CameraFragment.PHOTO_DIR_SMALL + fileName);
            delete(CameraFragment.PHOTO_DIR + fileName);
            itemList.remove(fileName);
        }

        notifyDataSetChanged();
        callback.onEmptyView(itemList.size() > 0);
    }

    private void delete(String path) {
        File file = new File(Uri.parse(path).getPath());
        if (file.exists()) {
            file.delete();
        }
    }

    public void showConfirmDialog() {
        if (checkedItemList.size() > 0) {
            TTSManager ttsManager = Injection.provideTTSManager(mActivity);
            ttsManager.createSession(mActivity.getResources().getString(R.string.photo_delete_popup_tts)).start();
            isDialogShowing = true;
            TapiaDialog tapiaDialog =new TapiaDialog.Builder(mActivity)
                    .setType(TapiaDialog.Type.WARNING)
                    .setPositiveButton(mActivity.getString(R.string.photo_delete_dialog_yes), () -> {
                        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
                        ttsManager.createSession(mActivity.getResources().getString(R.string.photo_delete_popup_yes_tts)).start();
                        deleteFilesList();
                        isDialogShowing = false;
                    })
                    .setNegativeButton(mActivity.getString(R.string.photo_delete_dialog_no), () -> {
                        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
                        ttsManager.createSession(mActivity.getResources().getString(R.string.photo_delete_popup_no_tts)).start();
                        isDialogShowing = false;
                        notifyDataSetChanged();
                    })
                    .setMessage(mActivity.getString(R.string.photo_delete_dialog_msg))
                    .show();
            tapiaDialog.setMsgTextSize(28);
            tapiaDialog.enlargeMsgBody(10);
        }
    }

    private void loadItemFragment(int position) {
        if (mActivity instanceof ConvenientActivity) {
            ((ConvenientActivity) mActivity).hideBack();
        }
        ItemScreenFragment fragment = ItemScreenFragment.newInstance();
        fragment.setDeleteCallback(fileName -> {
            itemList.remove(new File(fileName).getName());
            notifyDataSetChanged();
        });
        Bundle args = new Bundle();
        args.putInt("TappedItem", position);
        args.putStringArrayList("PhotoList", itemList);
        fragment.setArguments(args);
        ActivityUtils.setFragmentWithTagToActivity(
                ((AppCompatActivity) mActivity).getSupportFragmentManager(),
                fragment,
                ItemScreenFragment.TAG,
                R.id.fragment_layout,
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out,
                null);
    }
}
