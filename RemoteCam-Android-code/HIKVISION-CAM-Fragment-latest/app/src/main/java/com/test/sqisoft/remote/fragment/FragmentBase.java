package com.test.sqisoft.remote.fragment;

import android.support.v4.app.Fragment;

import com.test.sqisoft.remote.activity.MainActivity;
import com.test.sqisoft.remote.view.TitleView;

/**
 * Created by SQISOFT on 2016-10-12.
 */
public abstract class FragmentBase extends Fragment{
    abstract String getTitle();

    public FragmentBase(){
        TitleView titleView = MainActivity.getTitleView();
        if(titleView != null) {
            titleView.setText(getTitle());
        }
    }


}
