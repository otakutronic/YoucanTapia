package com.mji.tapia.youcantapia.features.convenient.phonebook.ui;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mji.tapia.youcantapia.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 4/20/2018.
 */

public class PhoneBookMenuAdapter extends BaseAdapter {

    static public class SubMenuItem {
        public int id;
        public String label;

        public SubMenuItem(int id, String label) {
            this.id = id;
            this.label = label;
        }
    }

    private List<SubMenuItem> subMenuItems;

    private Context context;

    public PhoneBookMenuAdapter(Context context, List<SubMenuItem> subMenuItems) {
        this.context = context;
        this.subMenuItems = subMenuItems;
    }

    public void setTextColor() {

    }

    @Override
    public int getCount() {
        return subMenuItems.size();
    }

    @Override
    public Object getItem(int i) {
        return subMenuItems.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.phonebook_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        SubMenuItem subMenuItem = subMenuItems.get(i);

        if(i == 0) {
            holder.label_tv.setText(Html.fromHtml("<font color='#A9A9A9'>+ </font><font color='#000000'>" + subMenuItem.label + "</font>"));
        } else {
            holder.label_tv.setText(subMenuItem.label);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.label)
        TextView label_tv;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}