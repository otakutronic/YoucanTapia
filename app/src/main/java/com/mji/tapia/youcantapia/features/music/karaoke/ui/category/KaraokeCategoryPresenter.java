package com.mji.tapia.youcantapia.features.music.karaoke.ui.category;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class KaraokeCategoryPresenter extends BasePresenter<KaraokeCategoryContract.View, KaraokeCategoryContract.Navigator> implements KaraokeCategoryContract.Presenter {

    private ResourcesManager resourcesManager;
    private KaraokeRepository karaokeRepository;

    KaraokeCategoryPresenter(KaraokeCategoryContract.View view, KaraokeCategoryContract.Navigator navigator, ResourcesManager resourcesManager, KaraokeRepository karaokeRepository) {
        super(view, navigator);
        this.resourcesManager = resourcesManager;
        this.karaokeRepository = karaokeRepository;
    }

    @Override
    public void init(KaraokeRepository.Category category) {
        super.init();
        switch (category) {
            case ANOKORO:
                view.setTitle(resourcesManager.getString(R.string.music_karaoke_menu_category_4_label));
                break;
            case ENNKA:
                view.setTitle(resourcesManager.getString(R.string.music_karaoke_menu_category_3_label));
                break;
            case ANIME:
                view.setTitle(resourcesManager.getString(R.string.music_karaoke_menu_category_5_label));
                break;
            case CLASSICAL:
                view.setTitle(resourcesManager.getString(R.string.music_karaoke_menu_category_1_label));
                break;
            case POP:
                view.setTitle(resourcesManager.getString(R.string.music_karaoke_menu_category_2_label));
                break;
        }
        karaokeRepository.getCategorySongs(category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karaokeSongs -> {
            view.setList(karaokeSongs);
        });
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.music_karaoke_category_speech).start();
    }

    @Override
    public void onFavoriteChange(KaraokeSong karaokeSong) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        karaokeRepository.setFavorite(karaokeSong, karaokeSong.isFavorite());
    }

    @Override
    public void onKaraokeSong(KaraokeSong karaokeSong) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openPlayerScreen(karaokeSong);
    }



}
