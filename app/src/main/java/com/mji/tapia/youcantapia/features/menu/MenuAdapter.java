package com.mji.tapia.youcantapia.features.menu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.util.AnimationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by Sami on 4/18/2018.
 */

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final Integer SETTING_VIEW = 1;

    private List<Pair<Integer, String>> dataList;

    MenuAdapter(List<Pair<Integer, String>> dataList) {
        this.dataList = dataList;
    };


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SETTING_VIEW){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_setting, parent, false);
            return new SettingViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent,false);
            return new MenuViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof SettingViewHolder) {
                SettingViewHolder vh = (SettingViewHolder) holder;
                vh.itemView.setOnClickListener(view -> {
                    if (onSettingListener != null)
                        onSettingListener.run();
                });
                if (isStartTransition) {
                    AnimationUtils.jumpIn(vh.itemView);
                }
                if (isEndTransition) {
                    AnimationUtils.jumpOut(vh.itemView);
                }
            } else if (holder instanceof MenuViewHolder) {
                MenuViewHolder vh = (MenuViewHolder) holder;
                vh.textView.setOnClickListener(view -> {
                    if (onItemClickListener != null)
                        try {
                            onItemClickListener.accept(dataList.get(position - 1));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                });
                vh.textView.setText(dataList.get(position - 1).second);
                if (isStartTransition) {
                    AnimationUtils.fadeIn(vh.itemView, 500);
                }
                if (isEndTransition) {
                    AnimationUtils.fadeOut(vh.itemView, 500);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    static class SettingViewHolder extends RecyclerView.ViewHolder{

        public SettingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_text)
        TextView textView;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            // This is where we'll add footer.
            return SETTING_VIEW;
        }
        return super.getItemViewType(position);
    }


    private Consumer<Pair<Integer, String>> onItemClickListener;
    void setOnItemClickListener(Consumer<Pair<Integer, String>> pairConsumer) {
        onItemClickListener = pairConsumer;
    };

    private Runnable onSettingListener;
    void setOnSettingClickListener(Runnable runnable) {
        onSettingListener = runnable;
    };

    public boolean isStartTransition() {
        return isStartTransition;
    }

    public void setStartTransition(boolean transition) {
        isStartTransition = transition;
        notifyDataSetChanged();
    }

    private boolean isStartTransition = false;

    public boolean isEndTransition() {
        return isEndTransition;
    }

    public void setEndTransition(boolean transition) {
        isEndTransition = transition;
        notifyDataSetChanged();
    }

    private boolean isEndTransition = false;
}
