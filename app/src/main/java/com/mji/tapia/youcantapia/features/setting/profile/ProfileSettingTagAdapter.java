package com.mji.tapia.youcantapia.features.setting.profile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.menu.MenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by Sami on 5/7/2018.
 *
 */

public class ProfileSettingTagAdapter extends RecyclerView.Adapter<ProfileSettingTagAdapter.TagViewHolder> {


        private static final Integer SETTING_VIEW = 1;

        private List<String> dataList;

        ProfileSettingTagAdapter(List<String> dataList) {
            this.dataList = dataList;
        };

        @NonNull
        @Override
        public ProfileSettingTagAdapter.TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_profile_tag, parent,false);
            view.setOnClickListener(view1 -> parent.performClick());
            return new TagViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileSettingTagAdapter.TagViewHolder holder, int position) {
            holder.textView.setText(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }


        static class TagViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tag)
            TextView textView;

            TagViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
}
