package com.mji.tapia.youcantapia.features.setting.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.touch.model.Ranking;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sami on 4/20/2018.
 */

public class SettingMenuAdapter extends BaseAdapter {

    static public class SubMenuItem {
        int id;
        int resIcon;
        String label;

        SubMenuItem(int id, int resIcon, String label) {
            this.id = id;
            this.resIcon = resIcon;
            this.label = label;
        }
    }

    private List<SubMenuItem> subMenuItems;

    private Context context;

    SettingMenuAdapter(Context context, List<SubMenuItem> subMenuItems) {
        this.context = context;
        this.subMenuItems = subMenuItems;
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
            view = LayoutInflater.from(context).inflate(R.layout.submenu_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        SubMenuItem subMenuItem = subMenuItems.get(i);

        holder.icon_iv.setImageResource(subMenuItem.resIcon);
        holder.label_tv.setText(subMenuItem.label);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.label)
        TextView label_tv;

        @BindView(R.id.icon)
        ImageView icon_iv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
