package com.mji.tapia.youcantapia.features.music.karaoke.ui.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class KaraokeCategoryFragment extends BaseFragment implements KaraokeCategoryContract.View {

    static public final String TAG = "KaraokeCategoryFragment";

    static private final String CATEGORY = "CATEGORY";

    KaraokeCategoryPresenter presenter;

    static public KaraokeCategoryFragment newInstance(KaraokeRepository.Category category) {
        KaraokeCategoryFragment karaokeCategoryFragment = new KaraokeCategoryFragment();
        karaokeCategoryFragment.setArguments(new Bundle());
        karaokeCategoryFragment.getArguments().putString(CATEGORY,category.name());
        return karaokeCategoryFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new KaraokeCategoryPresenter(this, new KaraokeCategoryNavigator((AppCompatActivity) getActivity()), Injection.provideResourcesManager(getContext()), Injection.provideKaraokeRepository(getContext()));
        return presenter;
    }

    @BindView(R.id.item_list) ListView karaokeListView;
    @BindView(R.id.item_text) TextView categoryTitle_tv;

    private KaraokeCategoryAdapter karaokeCategoryAdapter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_category_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        karaokeCategoryAdapter = new KaraokeCategoryAdapter(getContext(), new ArrayList<>());

        karaokeCategoryAdapter.getKaraokeSongPublishSubject()
                .subscribe(karaokeSong -> presenter.onFavoriteChange(karaokeSong));
        karaokeListView.setAdapter(karaokeCategoryAdapter);

        karaokeListView.setOnItemClickListener((adapterView, view1, i, l) -> presenter.onKaraokeSong((KaraokeSong) karaokeCategoryAdapter.getItem(i)));
        if (getArguments() != null)
            presenter.init(KaraokeRepository.Category.valueOf(getArguments().getString(CATEGORY)));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setTitle(String title) {
        categoryTitle_tv.setText(title);
    }

    @Override
    public void setList(List<KaraokeSong> karaokeSongs) {
        karaokeCategoryAdapter.refresh(karaokeSongs);
    }
}
