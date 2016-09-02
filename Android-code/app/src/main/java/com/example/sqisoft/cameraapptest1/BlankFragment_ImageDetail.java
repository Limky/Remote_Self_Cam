package com.example.sqisoft.cameraapptest1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment_ImageDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment_ImageDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment_ImageDetail extends Fragment {
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
    private final int imgWidth = 320;
    private final int imgHeight = 372;

    private OnFragmentInteractionListener mListener;

    public BlankFragment_ImageDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment_ImageDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment_ImageDetail newInstance(String param1, String param2) {
        BlankFragment_ImageDetail fragment = new BlankFragment_ImageDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            Log.d("mParam1","mParam1 = "+mParam1);
            Log.d("mParam2","mParam2 = "+mParam2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View imageDetailView = inflater.inflate(R.layout.fragment_imagedetail, container, false);
        mContext = getActivity();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        /** 전송메시지 */
        String imgPath = mParam1;

/*
        File imgFile = new  File(imgPath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView iv = (ImageView) imageDetailView.findViewById(R.id.imageView);
            iv.setImageBitmap(myBitmap);
        }
*/


       //** 완성된 이미지 보여주기  *//*
        BitmapFactory.Options bfo = new BitmapFactory.Options();
        bfo.inSampleSize = 2;
        ImageView iv = (ImageView) imageDetailView.findViewById(R.id.imageView);
        Bitmap bm = BitmapFactory.decodeFile(imgPath, bfo);
        Bitmap resized = Bitmap.createScaledBitmap(bm,imgWidth, imgHeight, true);
        iv.setImageBitmap(resized);


        /** 리스트로 가기 버튼 */
       Button btn = (Button)imageDetailView.findViewById(R.id.btn_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
       /*         transaction.replace(R.id.replacedLayout, new BlankFragment_Album()).commit();*/
            }
        });

        return imageDetailView;
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
