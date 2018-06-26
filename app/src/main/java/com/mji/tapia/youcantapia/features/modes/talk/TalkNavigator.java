package com.mji.tapia.youcantapia.features.modes.talk;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.ConvenientActivity;
import com.mji.tapia.youcantapia.features.game.GameActivity;
import com.mji.tapia.youcantapia.features.menu.MenuActivity;
import com.mji.tapia.youcantapia.features.modes.standby.StandbyFragment;
import com.mji.tapia.youcantapia.features.music.MusicActivity;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.setting.MainSettingActivity;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TalkNavigator implements TalkContract.Navigator {

    private AppCompatActivity activity;

    TalkNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openMainMenuScreen() {
        Intent intent = new Intent(activity, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void openStandbyScreen() {
        final FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.fragment_layout, StandbyFragment.newInstance(StandbyFragment.SLEEP_CALLER));
        transaction.commit();
    }

    @Override
    public void openTodayTankaScreen() {

    }

    @Override
    public void openTodayHaikuScreen() {

    }

    @Override
    public void openTodayQuotationScreen() {

    }

    @Override
    public void openTodayMasterpieceScreen() {

    }

    @Override
    public void openScheduleScreen() {

    }

    @Override
    public void openAlarmScreen() {

    }

    @Override
    public void openSongPlayerAllScreen() {
        Intent intent = new Intent(activity, MusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target", "song_player");
        intent.putExtra("talk_mode", true);
        intent.putExtra("category", MusicRepository.Category.ALL.name());
        activity.startActivity(intent);
    }

    @Override
    public void openSongPlayerAnokoroScreen() {
        Intent intent = new Intent(activity, MusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target", "song_player");
        intent.putExtra("talk_mode", true);
        intent.putExtra("category", MusicRepository.Category.ANOKORO.name());
        activity.startActivity(intent);
    }

    @Override
    public void openSongPlayerPopScreen() {
        Intent intent = new Intent(activity, MusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target", "song_player");
        intent.putExtra("talk_mode", true);
        intent.putExtra("category", MusicRepository.Category.POP.name());
        activity.startActivity(intent);
    }

    @Override
    public void openSongPlayerClassicalScreen() {
        Intent intent = new Intent(activity, MusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target", "song_player");
        intent.putExtra("talk_mode", true);
        intent.putExtra("category", MusicRepository.Category.CLASSICAL.name());
        activity.startActivity(intent);
    }

    @Override
    public void openSongPlayerEnnkaScreen() {
        Intent intent = new Intent(activity, MusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target", "song_player");
        intent.putExtra("talk_mode", true);
        intent.putExtra("category", MusicRepository.Category.ENNKA.name());
        activity.startActivity(intent);
    }

    @Override
    public void openSongPlayerFavoriteScreen() {
        Intent intent = new Intent(activity, MusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target", "song_player");
        intent.putExtra("talk_mode", true);
        intent.putExtra("category", MusicRepository.Category.FAVORITE.name());
        activity.startActivity(intent);
    }

    @Override
    public void openSongPlayerRadioScreen() {
        Intent intent = new Intent(activity, MusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target", "radio_player");
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openSongMenuScreen() {
        Intent intent = new Intent(activity, MusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target", "song_menu");
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openKaraokeMenuScreen() {
        Intent intent = new Intent(activity, MusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target", "karaoke_menu");
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openKaraokeFavoriteScreen() {
        Intent intent = new Intent(activity, MusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target", "karaoke_favorite");
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openGameMenuScreen() {
        Intent intent = new Intent(activity, GameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openGameMarubatsuScreen() {
        Intent intent = new Intent(activity, GameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target","marubatsu");
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openGameTouchScreen() {
        Intent intent = new Intent(activity, GameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target","touch");
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openGameNotoreScreen() {

    }

    @Override
    public void openGameHyakunninScreen() {

    }

    @Override
    public void openClockScreen() {
        Intent intent = new Intent(activity, ConvenientActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target","clock");
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openVoiceMemoMenuScreen() {

    }

    @Override
    public void openVoiceMemoRecordScreen() {

    }

    @Override
    public void openVoiceMemoPlayScreen() {

    }

    @Override
    public void openPhoneBookMenuScreen() {
        Intent intent = new Intent(activity, ConvenientActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target","phone_book");
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openPhotoMenuScreen() {
        Intent intent = new Intent(activity, ConvenientActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target","photo");
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openPhotoTakeScreen() {
        Intent intent = new Intent(activity, ConvenientActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target","photo_take");
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openPhotoGalleryScreen() {
        Intent intent = new Intent(activity, ConvenientActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("target","photo_gallery");
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }

    @Override
    public void openMainSettingScreen() {
        Intent intent = new Intent(activity, MainSettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("talk_mode", true);
        activity.startActivity(intent);
    }
}
