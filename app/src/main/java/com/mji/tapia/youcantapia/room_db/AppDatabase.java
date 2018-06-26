package com.mji.tapia.youcantapia.room_db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;
import com.mji.tapia.youcantapia.features.music.karaoke.model.source.KaraokeSongConverter;
import com.mji.tapia.youcantapia.features.music.karaoke.model.source.KaraokeSongDao;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;
import com.mji.tapia.youcantapia.features.music.song.model.source.MusicSongConverter;
import com.mji.tapia.youcantapia.features.music.song.model.source.MusicSongDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 4/9/2018.
 *
 */

@Database(version = 1, entities = {KaraokeSong.class, MusicSong.class})
@TypeConverters({Converters.class, KaraokeSongConverter.class, MusicSongConverter.class})
public abstract class AppDatabase extends RoomDatabase{

    static private final String dbName = "app.db";

    public abstract KaraokeSongDao karaokeSongDao();
    public abstract MusicSongDao musicSongDao();

    static private AppDatabase instance;
    static public AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "app.db").build();
        }
        return instance;
    }

    public static boolean isDBImported(Context context) {
        String outFileName =  "/data/data/"
                +context.getApplicationContext().getPackageName()
                + "/databases/" + dbName;
        File file=new File(outFileName);
        return file.exists();
    }

    public static Completable copyDataBase(Context context) {
        CompletableSubject completableSubject = CompletableSubject.create();

        Schedulers.io().scheduleDirect(() -> {
            try {
                InputStream myInput = context.getAssets().open("db/" + dbName);
                String outFileName =  "/data/data/"
                        +context.getApplicationContext().getPackageName()
                        + "/databases/" + dbName;
                File file=new File(outFileName);
                if (file.exists()){
                    completableSubject.onComplete();
                    return;
                }
                new File("/data/data/"
                        +context.getApplicationContext().getPackageName()
                        + "/databases").mkdirs();
                OutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
                completableSubject.onComplete();
            } catch (IOException e) {
                e.printStackTrace();
                completableSubject.onError(e);
            }
        });

        return completableSubject;
    }
}
