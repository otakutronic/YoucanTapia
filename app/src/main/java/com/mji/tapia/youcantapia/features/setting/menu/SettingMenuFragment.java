package com.mji.tapia.youcantapia.features.setting.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class SettingMenuFragment extends BaseFragment implements SettingMenuContract.View {

    static public final String TAG = "SettingMenuFragment";

    static private final int VOLUME = 2;
    static private final int BRIGHTNESS = 3;
    static private final int POSITION = 4;
    static private final int BATTERY = 5;
    static private final int PROFILE = 6;
    static private final int LICENSE = 7;
    static private final int SERIAL = 8;
    static private final int RESET = 9;



    SettingMenuPresenter presenter;

    static public SettingMenuFragment newInstance() {
        return new SettingMenuFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new SettingMenuPresenter(this, new SettingMenuNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }

    @BindView(R.id.submenu_list)
    ListView listView;

    List<SettingMenuAdapter.SubMenuItem> subMenuItems;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.submenu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        subMenuItems = new ArrayList<>();

        subMenuItems.add(new SettingMenuAdapter.SubMenuItem(VOLUME, R.drawable.volume_icon, getString(R.string.menu_volume_label)));
        subMenuItems.add(new SettingMenuAdapter.SubMenuItem(BRIGHTNESS, R.drawable.brightness_icon, getString(R.string.menu_brightness_label)));
        subMenuItems.add(new SettingMenuAdapter.SubMenuItem(POSITION, R.drawable.position_icon, getString(R.string.menu_position_label)));
        subMenuItems.add(new SettingMenuAdapter.SubMenuItem(BATTERY, R.drawable.battery_icon, getString(R.string.menu_battery_label)));
        subMenuItems.add(new SettingMenuAdapter.SubMenuItem(PROFILE, R.drawable.profile_icon, getString(R.string.menu_profile_label)));
        subMenuItems.add(new SettingMenuAdapter.SubMenuItem(LICENSE, R.drawable.license_icon, getString(R.string.menu_license_label)));
        subMenuItems.add(new SettingMenuAdapter.SubMenuItem(SERIAL, R.drawable.serial_icon, getString(R.string.menu_serial_label)));
        subMenuItems.add(new SettingMenuAdapter.SubMenuItem(RESET, R.drawable.factory_reset_icon, getString(R.string.menu_reset_label)));

        SettingMenuAdapter settingMenuAdapter = new SettingMenuAdapter(getContext(), subMenuItems);
        listView.setAdapter(settingMenuAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            switch (subMenuItems.get(i).id) {
                case VOLUME:
                    presenter.onVolume();
                    break;
                case BRIGHTNESS:
                    presenter.onBrightness();
                    break;
                case POSITION:
                    presenter.onPosition();
                    break;
                case BATTERY:
                    presenter.onBattery();
                    break;
                case PROFILE:
                    presenter.onProfile();
                    break;
                case LICENSE:
                    presenter.onLicense();
                    break;
                case SERIAL:
                    presenter.onSerial();
                    break;
                case RESET:
                    presenter.onFactoryReset();
                    break;
            }
        });
        presenter.init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
