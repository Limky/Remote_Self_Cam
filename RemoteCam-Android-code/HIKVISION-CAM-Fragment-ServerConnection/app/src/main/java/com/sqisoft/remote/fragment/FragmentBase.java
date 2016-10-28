package com.sqisoft.remote.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.sqisoft.remote.activity.MainActivity;
import com.sqisoft.remote.view.TitleView;

/**
 * Created by SQISOFT on 2016-10-12.
 */
public abstract class FragmentBase extends Fragment{

    private TitleView titleView;
    private String mtitle;

    public FragmentBase(){

         titleView = MainActivity.getTitleView();
        if(titleView != null) {
            titleView.setText(getTitle());
        }
    }

    abstract String getTitle();

    public void setTitle(String title) {

        mtitle = title;
        titleView.setText(title);
        titleView.invalidate();
    }

    public void  setGalleryButton(Boolean b){
        if(b) {
            MainActivity.getGalleryButton().setVisibility(View.GONE);
        }else{
            MainActivity.getGalleryButton().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
   //     Toast.makeText(getActivity(), "Pause "+this.getClass()+" "+this.toString(), Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    public void onResume(){
    //   Toast.makeText(getActivity(), "Resume "+this.getClass()+" "+this.toString(), Toast.LENGTH_SHORT).show();
       setTitle(mtitle);
        super.onResume();
    }


}
