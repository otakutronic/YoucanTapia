package com.mji.tapia.youcantapia.features.convenient.photo.gallery;

import android.os.AsyncTask;

import com.mji.tapia.youcantapia.features.convenient.photo.camera.CameraFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Usman on 2018/05/09.
 */

public class ImageLoadTask extends AsyncTask<Void, String, ArrayList<String>> {
    private File fileDir;
    private RecyclerViewAdapter rcAdapter;

    public ImageLoadTask(RecyclerViewAdapter adapter) {
        this.rcAdapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        fileDir = new File(CameraFragment.PHOTO_DIR_SMALL);
        super.onPreExecute();
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        ArrayList<String> fileList = new ArrayList<>();
        File[] files = fileDir.listFiles();
        if(files != null) {
            Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
            for (File file : files) {
                fileList.add(file.getName());
            }
        }
        return fileList;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        rcAdapter.setAdapterData(result);
        rcAdapter.notifyDataSetChanged();
    }
}