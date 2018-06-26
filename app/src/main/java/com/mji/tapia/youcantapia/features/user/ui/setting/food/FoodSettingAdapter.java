package com.mji.tapia.youcantapia.features.user.ui.setting.food;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.features.user.ui.setting.name.NameSettingAdapter;
import com.mji.tapia.youcantapia.widget.animation.face.FunnyAnimation;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FoodSettingAdapter extends BaseAdapter {

    private List<Pair<Boolean, User.Food>> foodList;

    private LayoutInflater inflater;

    FoodSettingAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        foodList = new ArrayList<>();
        for (User.Food food: User.Food.values()) {
            foodList.add(new Pair<>(false, food));
        }
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FoodSettingAdapter.ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.setting_user_preference_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.name_tv.setText(foodList.get(i).second.getResId());
        if (foodList.get(i).first) {
            holder.check_icon.setSelected(true);
        } else {
            holder.check_icon.setSelected(false);
        }

        return view;
    }

    public void toggleItem(int i) {
        foodList.set(i, new Pair<>(!foodList.get(i).first, foodList.get(i).second));
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

    public List<User.Food> getSelectedItems() {
        List<User.Food> selectedItems = new ArrayList<>();
        for (Pair<Boolean, User.Food> f: foodList) {
            if (f.first)
                selectedItems.add(f.second);
        }
        return selectedItems;
    }

    void setSelectedItems(List<User.Food> selectedItems) {
        for (User.Food food : selectedItems) {
            for (int i = 0; i < foodList.size(); i++) {
                if (food == foodList.get(i).second) {
                    foodList.set(i, new Pair<>(true, food));
                }
            }
        }
    }
}
