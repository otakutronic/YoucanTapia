package com.mji.tapia.youcantapia.features.convenient.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ConvenientMenuFragment extends BaseFragment implements ConvenientMenuContract.View {

    static public final String TAG = "ConvenientMenuFragment";

    static private final int VOICE_MESSAGE = 1;
    static private final int PHOTO = 2;
    static private final int PHONE_BOOK = 3;
    static private final int CLOCK = 4;


    ConvenientMenuPresenter presenter;

    static public ConvenientMenuFragment newInstance() {
        return new ConvenientMenuFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new ConvenientMenuPresenter(this, new ConvenientMenuNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }

    @BindView(R.id.submenu_list)
    ListView listView;

    List<ConvenientMenuAdapter.SubMenuItem> subMenuItems;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.submenu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        subMenuItems = new ArrayList<>();

        subMenuItems.add(new ConvenientMenuAdapter.SubMenuItem(VOICE_MESSAGE, R.drawable.convenient_voice_message_icon, getString(R.string.conveninent_menu_voice_message_label)));
        subMenuItems.add(new ConvenientMenuAdapter.SubMenuItem(PHONE_BOOK, R.drawable.convenient_phone_book, getString(R.string.conveninent_menu_phone_book_label)));
        subMenuItems.add(new ConvenientMenuAdapter.SubMenuItem(PHOTO, R.drawable.convenient_photo_icon, getString(R.string.conveninent_menu_photo_label)));
        subMenuItems.add(new ConvenientMenuAdapter.SubMenuItem(CLOCK, R.drawable.convenient_clock_icon, getString(R.string.conveninent_menu_clock_label)));

        ConvenientMenuAdapter convenientMenuAdapter = new ConvenientMenuAdapter(getContext(), subMenuItems);
        listView.setAdapter(convenientMenuAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            switch (subMenuItems.get(i).id) {
                case PHONE_BOOK:
                    presenter.onPhoneBook();
                    break;
                case PHOTO:
                    presenter.onPhoto();
                    break;
                case VOICE_MESSAGE:
                    presenter.onVoiceMessage();
                    break;
                case CLOCK:
                    presenter.onClock();
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
