package com.mji.tapia.youcantapia.features.user.ui.setting.food;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FoodSettingPresenter extends BasePresenter<FoodSettingContract.View, FoodSettingContract.Navigator> implements FoodSettingContract.Presenter {


    private UserRepository userRepository;

    FoodSettingPresenter(FoodSettingContract.View view, FoodSettingContract.Navigator navigator, UserRepository userRepository) {
        super(view, navigator);
        this.userRepository = userRepository;
    }


    @Override
    public void activate() {
        super.activate();
        String name =userRepository.getUserName();
        ttsManager.createSession(R.string.user_food_speech, name).start();
    }

    @Override
    public void init() {
        super.init();
        User user = userRepository.getUser();
        if (user != null) {
            view.setSelectedFood(new ArrayList<>(user.favoriteFood));
        }
    }

    @Override
    public void onFoodChange() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void onNext(List<User.Food> foodList) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        User user = userRepository.getUser();
        if (user == null) {
            user = new User();
        }
        user.favoriteFood = new HashSet<>(foodList);
        userRepository.saveUser(user);
        navigator.openPostConfirmScreen();
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.goBack();
    }
}
