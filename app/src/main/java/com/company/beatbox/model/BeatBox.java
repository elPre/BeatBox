package com.company.beatbox.model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hectorleyvavillanueva on 12/22/16.
 */

public class BeatBox {
    private static final String TAG = BeatBox.class.getSimpleName();
    private static final String SOUNDS_FOLFER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssetManager;
    private List<Sound> mSounds = new ArrayList<>(22);
    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssetManager = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    public void play(Sound sound) {
        Integer soundId = sound.getmSoundId();
        if(soundId == null)
            return;
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {
        mSoundPool.release();
    }

    private void loadSounds() {
        String[] soundNames;
        try {
            soundNames = mAssetManager.list(SOUNDS_FOLFER);
            Log.d(TAG, "Found asssets " + soundNames.length);
        } catch (IOException e) {
            Log.e(TAG, "could not list assets " + e);
            e.printStackTrace();
            return;
        }
        Sound sound;
        StringBuilder assetPath = new StringBuilder();

        for (String fileName : soundNames) {
            assetPath.setLength(0);
            assetPath.append(SOUNDS_FOLFER + "/" + fileName);
            sound = new Sound(assetPath.toString());
            try{
                load(sound);
            }catch (IOException e){
                Log.e(TAG, "Could not load sound " + fileName, e);
            }

            mSounds.add(sound);
        }
        sound = null;
        assetPath = null;
    }

    public List<Sound> getmSounds() {
        return mSounds;
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssetManager.openFd(sound.getmAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setmSoundId(soundId);
    }

}
