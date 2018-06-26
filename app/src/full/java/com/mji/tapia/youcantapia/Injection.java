package com.mji.tapia.youcantapia;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.ContactsRepository;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.ContactsRepositoryImpl;
import com.mji.tapia.youcantapia.features.game.touch.model.RankingRepository;
import com.mji.tapia.youcantapia.features.game.touch.model.RankingRepositoryImpl;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepositoryImpl;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepositoryImpl;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.features.user.model.UserRepositoryImpl;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManagerImpl;
import com.mji.tapia.youcantapia.managers.battery.TapiaBatteryManager;
import com.mji.tapia.youcantapia.managers.battery.TapiaBatteryManagerImpl;
import com.mji.tapia.youcantapia.managers.brightness.TapiaBrightnessManager;
import com.mji.tapia.youcantapia.managers.brightness.TapiaBrightnessManagerImpl;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManagerImpl;
import com.mji.tapia.youcantapia.managers.robot.RobotManager;
import com.mji.tapia.youcantapia.managers.robot.RobotManagerImpl;
import com.mji.tapia.youcantapia.managers.scenario.ScenarioManager;
import com.mji.tapia.youcantapia.managers.scenario.ScenarioManagerImpl;
import com.mji.tapia.youcantapia.managers.shared_preference.SharedPreferenceManager;
import com.mji.tapia.youcantapia.managers.shared_preference.SharedPreferenceManagerImpl;
import com.mji.tapia.youcantapia.managers.stt.FuetrekManager;
import com.mji.tapia.youcantapia.managers.stt.STTManager;
import com.mji.tapia.youcantapia.managers.time.TapiaTimeManager;
import com.mji.tapia.youcantapia.managers.time.TapiaTimeManagerImpl;
import com.mji.tapia.youcantapia.managers.tts.HoyaManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;
import com.mji.tapia.youcantapia.managers.wake_up.STTWakeUpManager;
import com.mji.tapia.youcantapia.managers.wake_up.WakeUpManager;
import com.mji.tapia.youcantapia.room_db.AppDatabase;

public class Injection {
    //this is real

    public static RobotManager provideRobotManager(@NonNull Context context) {
        return RobotManagerImpl.getInstance(context.getApplicationContext(), provideSharedPreferenceManager(context));
    }

    public static TapiaAudioManager provideTapiaAudioManager(@NonNull Context context) {
        return TapiaAudioManagerImpl.getInstance(context.getApplicationContext(), provideSharedPreferenceManager(context));
    }

    public static SharedPreferenceManager provideSharedPreferenceManager(@NonNull Context context) {
        return SharedPreferenceManagerImpl.getInstance(context.getApplicationContext());
    }

    public static ResourcesManager provideResourcesManager(@NonNull Context context) {
        return ResourcesManagerImpl.getInstance(context.getApplicationContext());
    }

    public static TTSManager provideTTSManager(@NonNull Context context) {
        return HoyaManager.getInstance(context.getApplicationContext(), Injection.provideTapiaAudioManager(context));
    }

    public static RankingRepository provideRankingRepository(@NonNull Context context) {
        return RankingRepositoryImpl.getInstance(Injection.provideSharedPreferenceManager(context));
    }

    public static TapiaTimeManager provideTapiaTimeManager(@NonNull Context context) {
        return TapiaTimeManagerImpl.getInstance(context.getApplicationContext());
    }

    public static UserRepository provideUserRepository(@NonNull Context context) {
        return UserRepositoryImpl.getInstance(Injection.provideSharedPreferenceManager(context.getApplicationContext()));
    }

    public static ContactsRepository provideContactRepository(@NonNull Context context) {
        return ContactsRepositoryImpl.getInstance(Injection.provideSharedPreferenceManager(context.getApplicationContext()));
    }

    public static TapiaBrightnessManager provideTapiaBrightnessManager(@NonNull Activity activity) {
        return TapiaBrightnessManagerImpl.getInstance(provideSharedPreferenceManager(activity), activity.getContentResolver(), activity.getWindow());
    }

    public static TapiaBatteryManager provideTapiaBatteryManager(@NonNull Context context) {
        return TapiaBatteryManagerImpl.getInstance(context.getApplicationContext());
    }

    public static KaraokeRepository provideKaraokeRepository(@NonNull Context context) {
        return KaraokeRepositoryImpl.getInstance(provideAppDatabase(context));
    }


    public static MusicRepository provideMusicRepository(@NonNull Context context) {
        return MusicRepositoryImpl.getInstance(provideAppDatabase(context));
    }

    public static AppDatabase provideAppDatabase(@NonNull Context context) {
        if (!AppDatabase.isDBImported(context.getApplicationContext())) {
            Throwable e = AppDatabase.copyDataBase(context.getApplicationContext()).blockingGet();
            //do something
            if (e != null) {
                throw new RuntimeException(e);
            }
        }
        return AppDatabase.getInstance(context.getApplicationContext());
    }

    public static ScenarioManager provideScenarioManager(@NonNull Context context) {
        return ScenarioManagerImpl.getInstance(context.getApplicationContext(), Injection.provideTTSManager(context), Injection.provideRobotManager(context), Injection.provideUserRepository(context));
    }

    public static STTManager provideSTTManager(@NonNull Context context) {
        return FuetrekManager.getInstance(context.getApplicationContext());
    }

    public static WakeUpManager provideWakeUpManager(@NonNull Context context) {
        return STTWakeUpManager.getInstance(Injection.provideSTTManager(context));
    }
}