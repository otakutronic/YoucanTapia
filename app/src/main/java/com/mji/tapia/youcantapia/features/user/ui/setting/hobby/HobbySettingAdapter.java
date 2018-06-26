package com.mji.tapia.youcantapia.features.user.ui.setting.hobby;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class HobbySettingAdapter extends BaseAdapter {

    private List<Pair<Boolean, User.Hobby>> hobbyList;

    private LayoutInflater inflater;

    HobbySettingAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        hobbyList = new ArrayList<>();
        for (User.Hobby hobby: User.Hobby.values()) {
            hobbyList.add(new Pair<>(false, hobby));
        }
    }

    @Override
    public int getCount() {
        return hobbyList.size();
    }

    @Override
    public Object getItem(int i) {
        return hobbyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HobbySettingAdapter.ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.setting_user_preference_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.name_tv.setText(hobbyList.get(i).second.getResId());
        if (hobbyList.get(i).first) {
            holder.check_icon.setSelected(true);
        } else {
            holder.check_icon.setSelected(false);
        }

        return view;
    }

    public void toggleItem(int i) {
        hobbyList.set(i, new Pair<>(!hobbyList.get(i).first, hobbyList.get(i).second));
        AndroidSchedulers.mainThread().scheduleDirect(this::notifyDataSetChanged);
    }

    static class ViewHolder {
        @BindView(R.id.check_icon) View check_icon;
        @BindView(R.id.name)
        TextView name_tv;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public List<User.Hobby> getSelectedItems() {
        List<User.Hobby> selectedItems = new ArrayList<>();
        for (Pair<Boolean, User.Hobby> f: hobbyList) {
            if (f.first)
                selectedItems.add(f.second);
        }
        return selectedItems;
    }

    void setSelectedItems(List<User.Hobby> selectedItems) {
        for (User.Hobby hobby : selectedItems) {
            for (int i = 0; i < hobbyList.size(); i++) {
                if (hobby == hobbyList.get(i).second) {
                    hobbyList.set(i, new Pair<>(true, hobby));
                }
            }
        }
    }
}
