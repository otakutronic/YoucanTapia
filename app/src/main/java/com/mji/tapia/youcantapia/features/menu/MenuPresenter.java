package com.mji.tapia.youcantapia.features.menu;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MenuPresenter extends BasePresenter<MenuContract.View, MenuContract.Navigator> implements MenuContract.Presenter {
    MenuPresenter(MenuContract.View view, MenuContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void activate() {
        super.activate();
        view.fadeIn();
        ttsManager.createSession(R.string.menu_speech).start();
    }

    @Override
    public void onVoiceMessage() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
//        view.fadeOut().subscribe(navigator::openVoiceMessageScreen);
    }

    @Override
    public void onSleepMode() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        view.fadeOut().subscribe(navigator::openSleepModeScreen);
    }

    @Override
    public void onGameMenu() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        view.fadeOut().subscribe(navigator::openGameMenu);
    }

    @Override
    public void onMusicMenu() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        view.fadeOut().subscribe(navigator::openMusicMenu);
    }

    @Override
    public void onConvenientMenu() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        view.fadeOut().subscribe(navigator::openConvenientMenu);
    }

    @Override
    public void onScheduleMenu() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
//        view.fadeOut().subscribe(navigator::openScheduleMenu);
    }

    @Override
    public void onSettingMenu() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        view.fadeOut().subscribe(navigator::openSettingMenu);
    }

    @Override
    public void onTodaysMenu() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        view.fadeOut().subscribe(navigator::openTodaysMenu);
    }
}
