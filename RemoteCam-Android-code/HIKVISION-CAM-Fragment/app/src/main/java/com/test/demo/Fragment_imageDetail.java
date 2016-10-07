package com.test.demo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_imageDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_imageDetail#} factory method to
 * create an instance of this fragment.
 */
public class Fragment_imageDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    FragmentManager manager;
    FragmentTransaction transaction;
    private Context mContext = null;

    PhotoViewAttacher mAttacher;
    RelativeLayout DetailLayout;
    BackPressCloseHandler backPressCloseHandler;
    ViewPager pager;
    int currentIndex;
    ArrayList<ImageObject> imagesList;

    private OnFragmentInteractionListener mListener;

    public Fragment_imageDetail(ArrayList<ImageObject> param1, int param2) {
        this.imagesList = param1;
        this.currentIndex = param2;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View imageDetailView = inflater.inflate(R.layout.fragment_imagedetail, container, false);
        mContext = getActivity();

        DetailLayout = (RelativeLayout) imageDetailView.findViewById(R.id.DetailLayout);

        Log.i("imagesList","imagesList = "+imagesList.get(currentIndex).getImagePath());
        Log.i("currentIndex","currentIndex = "+currentIndex);

        pager= (ViewPager)imageDetailView.findViewById(R.id.pager);
        MyPageAdapter adapter= new MyPageAdapter(getActivity().getLayoutInflater(),imagesList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(currentIndex);


        return imageDetailView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
