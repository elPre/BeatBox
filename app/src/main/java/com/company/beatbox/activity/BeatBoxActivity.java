package com.company.beatbox.activity;

import android.support.v4.app.Fragment;

import com.company.beatbox.fragment.BeatBoxFragment;

public class BeatBoxActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }

}
