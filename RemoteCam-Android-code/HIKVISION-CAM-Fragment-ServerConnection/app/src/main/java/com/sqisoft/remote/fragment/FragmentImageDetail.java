package com.sqisoft.remote.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sqisoft.remote.R;
import com.sqisoft.remote.domain.ServerImageDomain;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.view.MyGalleryDetailRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentImageDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentImageDetail#} factory method to
 * create an instance of this fragment.
 */
public class FragmentImageDetail extends FragmentBase{
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

    ViewPager pager;
    int currentIndex;
    ArrayList<ServerImageDomain> serverImageDomains = DataManager.getInstance().getServerImageDomains();

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static FragmentCamera newInstance(String param1, String param2) {
        FragmentCamera fragment = new FragmentCamera();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public FragmentImageDetail(int param2) {
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
        View imageDetailView = inflater.inflate(R.layout.fragment_gallery_image_detail, container, false);
        ImageView detailImageView = (ImageView) imageDetailView.findViewById(R.id.detail_image_view);


        setTitle("셀카 갤러리 사진보기");
        setGalleryButton(true);
        mContext = getActivity();

        RecyclerView image_detail_recyclerView = (RecyclerView) imageDetailView.findViewById(R.id.image_detail_recycler_view);
        MyGalleryDetailRecyclerAdapter myAdapter = new MyGalleryDetailRecyclerAdapter(this,getActivity(),detailImageView);



        image_detail_recyclerView.setAdapter(myAdapter);
        image_detail_recyclerView.setLayoutManager(new GridLayoutManager(mContext,1,OrientationHelper.HORIZONTAL,false));



           Picasso.with(getContext())
                .load(serverImageDomains.get(currentIndex).getImageUrl())
                .placeholder(R.drawable.dx)
                .into(detailImageView);


        PhotoViewAttacher attacher = new PhotoViewAttacher(detailImageView);
        attacher.setScaleType(ImageView.ScaleType.FIT_XY);
        attacher.update();




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

    @Override
    String getTitle() {
        return "셀카 갤러리 사진보기";
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}