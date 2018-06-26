package com.mji.tapia.youcantapia.features.convenient.photo.gallery;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mji.tapia.youcantapia.R;

/**
 * Created by Usman on 2018/05/08.
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder {
    public ImageView photo;
    public ImageView checkPhoto;
    private Activity mActivity;

    public RecyclerViewHolders(View item, Activity activity) {
        super(item);
        this.mActivity = activity;
        photo = (ImageView) itemView.findViewById(R.id.photo);
        checkPhoto = (ImageView) itemView.findViewById(R.id.selectPhoto);
    }
}
