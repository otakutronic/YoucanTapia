package com.mji.tapia.youcantapia.features.modes.talk;

import android.content.res.Resources;
import android.util.Log;
import android.util.Pair;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;
import com.mji.tapia.youcantapia.managers.robot.RobotManager;
import com.mji.tapia.youcantapia.managers.scenario.Command;
import com.mji.tapia.youcantapia.managers.scenario.Scenario;
import com.mji.tapia.youcantapia.managers.scenario.ScenarioManager;
import com.mji.tapia.youcantapia.managers.stt.STTManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TalkPresenter extends BasePresenter<TalkContract.View, TalkContract.Navigator> implements TalkContract.Presenter {

    private STTManager sttManager;
    private ScenarioManager scenarioManager;
    private Scenario scenario;
    private TTSManager ttsManager;
    private UserRepository userRepository;
    private ResourcesManager resourcesManager;

    TalkPresenter(TalkContract.View view, TalkContract.Navigator navigator, ScenarioManager scenarioManager, STTManager sttManager, TTSManager ttsManager, UserRepository userRepository, ResourcesManager resourcesManager) {
        super(view, navigator);
        this.scenarioManager = scenarioManager;
        this.sttManager = sttManager;
        this.ttsManager = ttsManager;
        this.userRepository = userRepository;
        this.resourcesManager = resourcesManager;
    }

    @Override
    public void init() {
        super.init();
        scenario = scenarioManager.getScenario(R.xml.talk_mode_scenario);
        sttManager.initOffline(scenario.getModelPath());
        User user = userRepository.getUser();
        scenario.setVariable("owner", user.name);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy' 年 'MM' 月 'dd' 日'", Locale.JAPANESE);
        scenario.setVariable("owner_birthday", dateFormat .format(user.birthday));
//        scenario.setVariable("register_date", dateFormat.format());

    }

    private Disposable stateDisposable;
    private Disposable timeOutDisposable;
    private int failCount;
    @Override
    public void activate() {
        super.activate();
        Log.e("TALK", "ACTIVATE");
        failCount = 0;
        startTimeout();
        ttsManager.createSession(R.string.talk_mode_intro, userRepository.getUser().name).start()
        .subscribe(() -> {
            Log.e("TALK", "START SESSION 1");
            sttManager.createSession().listen().subscribe(new Consumer<List<String>>() {
                @Override
                public void accept(List<String> stringList) throws Exception {
                    if (stringList.size() <= 0) {
                        Log.e("TALK", "START SESSION 2");
                        sttManager.createSession().listen().subscribe(this);
                        return;
                    }
                    Command command = scenario.getCommand(stringList.get(0));
                    if (command == null) {
                        failCount++;
                        if (failCount >= 5) {
                            navigator.openStandbyScreen();
                        } else {
                            scenarioManager.playConfuse(scenario, view.getAnimationView())
                                    .subscribe(() -> {
                                        Log.e("TALK", "START SESSION 3");
                                sttManager.createSession().listen().subscribe(this);
                                    });
                        }
                        return;
                    }
                    failCount = 0;
                    startTimeout();
                    //Randomize food and hobbies every command
                    User user = userRepository.getUser();
                    Random random = new Random();
                    if (user.favoriteFood.size() > 0) {
                        scenario.setVariable("owner_food",
                                resourcesManager.getString(new ArrayList<>(user.favoriteFood).get(random.nextInt(user.favoriteFood.size())).getResId()));
                    }
                    if (user.hobbies.size() > 0) {
                        scenario.setVariable("owner_hobby",
                                resourcesManager.getString(new ArrayList<>(user.hobbies).get(random.nextInt(user.hobbies.size())).getResId()));
                    }
                    String[] joke_endings = resourcesManager.getStringArray(R.array.joke_ending);
                    scenario.setVariable("joke_ending", joke_endings[random.nextInt(joke_endings.length)]);

                    //Play one answer
                    scenarioManager.playAnswer(scenario, scenarioManager.getCommandAnswer(command), view.getAnimationView())
                            .subscribe(() -> {
                                switch (command.type) {
                                    case CONVERSATION:
                                        Log.e("TALK", "START SESSION 4");
                                        sttManager.createSession().listen().subscribe(this);
                                        break;
                                    case GOTO:
                                        goToFunction(command.param);
                                        break;
                                }
                            });

                }
            });
        });

        stateDisposable = Observable.combineLatest(ttsManager.getCurrentStateObserver(),
                sttManager.getCurrentStateObserver(), Pair::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pair -> {
                    if (pair.second == STTManager.STTState.LISTENING ||
                            pair.second == STTManager.STTState.RECORDING) {
                        view.setOrangeBackground();
                    } else if (pair.first == TTSManager.TTSState.SPEAKING){
                        view.setBlueBackground();
                    } else {
                        view.setNormalBackground();
                    }
                });
    }

    @Override
    public void deactivate() {
        super.deactivate();
        Log.e("TALK", "DEACTIVATE");
        if (stateDisposable != null && !stateDisposable.isDisposed()){
            stateDisposable.dispose();
        }
        if (timeOutDisposable != null && !timeOutDisposable.isDisposed()) {
            timeOutDisposable.dispose();
        }
    }

    private void startTimeout() {
        if (timeOutDisposable != null && !timeOutDisposable.isDisposed()) {
            timeOutDisposable.dispose();
        }
        timeOutDisposable = Completable.timer(30, TimeUnit.SECONDS)
                .subscribe(() -> navigator.openStandbyScreen());
    }

    @Override
    public void onLongTouch() {
        sttManager.stopCurrent();
        ttsManager.stopCurrent();
        view.startTransition().subscribe(navigator::openMainMenuScreen);
    }


    private void goToFunction(String param) {
        switch (param) {
            case "menu":
                view.startTransition().subscribe(navigator::openMainMenuScreen);
                break;
            case "todays::tanka":
                view.startTransition().subscribe(navigator::openTodayTankaScreen);
                break;
            case "todays::haiku":
                view.startTransition().subscribe(navigator::openTodayHaikuScreen);
                break;
            case "todays::quotation":
                view.startTransition().subscribe(navigator::openTodayQuotationScreen);
                break;
            case "todays::masterpiece":
                view.startTransition().subscribe(navigator::openTodayMasterpieceScreen);
                break;
            case "schedule":
                view.startTransition().subscribe(navigator::openScheduleScreen);
                break;
            case "alarm":
                view.startTransition().subscribe(navigator::openAlarmScreen);
                break;
            case "play_song::all":
                view.startTransition().subscribe(navigator::openSongPlayerAllScreen);
                break;
            case "song::menu":
                view.startTransition().subscribe(navigator::openSongMenuScreen);
                break;
            case "play_song::anokoro":
                view.startTransition().subscribe(navigator::openSongPlayerAnokoroScreen);
                break;
            case "play_song::pop":
                view.startTransition().subscribe(navigator::openSongPlayerPopScreen);
                break;
            case "play_song::classical":
                view.startTransition().subscribe(navigator::openSongPlayerClassicalScreen);
                break;
            case "play_song::ennka":
                view.startTransition().subscribe(navigator::openSongPlayerEnnkaScreen);
                break;
            case "play_song::radio":
                view.startTransition().subscribe(navigator::openSongPlayerRadioScreen);
                break;
            case "play_song::favorite":
                view.startTransition().subscribe(navigator::openSongPlayerFavoriteScreen);
                break;
            case "karaoke::menu":
                view.startTransition().subscribe(navigator::openKaraokeMenuScreen);
                break;
            case "karaoke::favorite":
                view.startTransition().subscribe(navigator::openKaraokeFavoriteScreen);
                break;
            case "game::menu":
                view.startTransition().subscribe(navigator::openGameMenuScreen);
                break;
            case "game::marubatsu":
                view.startTransition().subscribe(navigator::openGameMarubatsuScreen);
                break;
            case "game::touch":
                view.startTransition().subscribe(navigator::openGameTouchScreen);
                break;
            case "game::notore":
                view.startTransition().subscribe(navigator::openGameNotoreScreen);
                break;
            case "game::hyakunnin":
                view.startTransition().subscribe(navigator::openGameHyakunninScreen);
                break;
            case "clock":
                view.startTransition().subscribe(navigator::openClockScreen);
                break;
            case "voicememo::menu":
                view.startTransition().subscribe(navigator::openVoiceMemoMenuScreen);
                break;
            case "voicememo::record":
                view.startTransition().subscribe(navigator::openVoiceMemoRecordScreen);
                break;
            case "voicememo::play":
                view.startTransition().subscribe(navigator::openVoiceMemoPlayScreen);
                break;
            case "phone_book::menu":
                view.startTransition().subscribe(navigator::openPhoneBookMenuScreen);
                break;
            case "photo::menu":
                view.startTransition().subscribe(navigator::openPhotoMenuScreen);
                break;
            case "photo::take":
                view.startTransition().subscribe(navigator::openPhotoTakeScreen);
                break;
            case "photo::gallery":
                view.startTransition().subscribe(navigator::openPhotoGalleryScreen);
                break;
            case "setting":
                view.startTransition().subscribe(navigator::openMainSettingScreen);
                break;
        }
    }
}
