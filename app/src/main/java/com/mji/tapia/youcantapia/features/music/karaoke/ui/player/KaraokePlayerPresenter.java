package com.mji.tapia.youcantapia.features.music.karaoke.ui.player;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

import java.util.Random;


/**
 * Created by Sami on 3/30/2018.
 *
 */

public class KaraokePlayerPresenter extends BasePresenter<KaraokePlayerContract.View, KaraokePlayerContract.Navigator> implements KaraokePlayerContract.Presenter {


    private TapiaAudioManager tapiaAudioManager;

    private TTSManager ttsManager;

    private UserRepository userRepository;

    KaraokePlayerPresenter(KaraokePlayerContract.View view, KaraokePlayerContract.Navigator navigator, TapiaAudioManager tapiaAudioManager, TTSManager ttsManager, UserRepository userRepository) {
        super(view, navigator);
        this.userRepository = userRepository;
        this.ttsManager = ttsManager;
        this.tapiaAudioManager = tapiaAudioManager;
    }


    @Override
    public void init() {
        super.init();

    }

    private int originalVolume;

    @Override
    public void activate() {
        super.activate();
        originalVolume = tapiaAudioManager.getVolume();
        view.setVolume(originalVolume);
    }

    @Override
    public void deactivate() {
        super.deactivate();
        tapiaAudioManager.setVolume(originalVolume, false);
    }

    @Override
    public void onKaraokeFinish() {
        int endSpeeches[] = new int[3];
        endSpeeches[0] = R.string.music_karaoke_finish_speech_0;
        endSpeeches[1] = R.string.music_karaoke_finish_speech_1;
        endSpeeches[2] = R.string.music_karaoke_finish_speech_2;
        Random random = new Random();
        ttsManager.createSession(endSpeeches[random.nextInt(3)], userRepository.getUser().name).start();
        tapiaAudioManager.createAudioSessionFromAsset("game/se/clap_se.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void onValueChange(int value) {
        if (value >= TapiaAudioManager.MAX_VOLUME) {
            tapiaAudioManager.setVolume(TapiaAudioManager.MAX_VOLUME, false);
            view.setVolume(TapiaAudioManager.MAX_VOLUME);
        } else if (value <= 0) {
            tapiaAudioManager.setVolume(0, false);
            view.setVolume(0);
        } else {
            tapiaAudioManager.setVolume(value, false);
            view.setVolume(value);
        }
    }

    @Override
    public void onVolumeUp() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void onVolumeDown() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }
}
