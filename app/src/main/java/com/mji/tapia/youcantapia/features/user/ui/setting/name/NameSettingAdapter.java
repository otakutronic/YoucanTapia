package com.mji.tapia.youcantapia.features.user.ui.setting.name;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.widget.CustomFontTextView;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Sami on 4/27/2018.
 */

public class NameSettingAdapter extends BaseAdapter {

    List<String> stringList;


    private int selectedItem = 0;

    private LayoutInflater inflater;

    NameSettingAdapter(Context context, List<String> stringList) {
        this.stringList = stringList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int i) {
        return stringList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.setting_user_name_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.name_tv.setText(stringList.get(i));
        if (selectedItem == i) {
            holder.check_icon.setVisibility(View.VISIBLE);
        } else {
            holder.check_icon.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    public void setSelectedItem(int i) {
        selectedItem = i;
        AndroidSchedulers.mainThread().scheduleDirect(this::notifyDataSetChanged);
    }

    public void refresh(List<String> s) {
        stringList = s;
        AndroidSchedulers.mainThread().scheduleDirect(this::notifyDataSetChanged);
    }

    static class ViewHolder {
        @BindView(R.id.check_icon) View check_icon;
        @BindView(R.id.name)
        CustomFontTextView name_tv;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public String getSelectedItem() {
        return stringList.get(selectedItem);
    }
}
