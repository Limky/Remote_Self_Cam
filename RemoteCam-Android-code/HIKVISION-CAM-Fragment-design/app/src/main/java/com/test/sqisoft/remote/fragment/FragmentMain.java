package com.test.sqisoft.remote.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.test.sqisoft.R;
import com.test.sqisoft.remote.data.ResponseListener;
import com.test.sqisoft.remote.data.ZoneData;
import com.test.sqisoft.remote.util.CommandUtil;
import com.test.sqisoft.remote.util.Log;
import com.test.sqisoft.remote.view.MyListViewAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends FragmentBase {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View fragment_main_view;

    FragmentManager manager;
    FragmentTransaction transaction;
    ImageView tourImageView;

    MyListViewAdapter myListViewAdapter;
    private ListView m_ListView;


    public FragmentMain() {
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Main.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getTitle();

        // Inflate the layout for this fragment
        fragment_main_view = inflater.inflate(R.layout.fragment_main, container, false);

      //  tourImageView = (ImageView)  fragment_main_view.findViewById(R.id.tour_imageview);
        m_ListView = (ListView) fragment_main_view.findViewById(R.id.list);
        //투어 이미지 설정
       // tourImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.main_tour_img_2));

        //프래그먼트 페이지 이동을 위한 초기 설정
         manager = getFragmentManager();
         transaction = manager.beginTransaction();


        Button cameraBtn = (Button)fragment_main_view.findViewById(R.id.camera_btn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                transaction.replace(R.id.replacedLayout, new FragmentCamera()).addToBackStack("tag").commit();

            }
        });


        ArrayList<String> arraylist = new ArrayList<String>();
        arraylist.add("셀카존 001");
        arraylist.add("셀카존 002");
        arraylist.add("셀카존 003");
        arraylist.add("셀카존 004");
        arraylist.add("셀카존 005");

        myListViewAdapter = new MyListViewAdapter(getActivity(), arraylist);

        View header = inflater.inflate(R.layout.listview_header,null,false);
        m_ListView.addHeaderView(header);
        m_ListView.setAdapter(myListViewAdapter);





        CommandUtil.getZone(new ResponseListener<ZoneData[]>() {
            @Override
            public void response(boolean success, ZoneData[] data) {
                if(success && data != null) {
                    for (int i = 0; i < data.length; i++) {
                        Log.d("test", data[i].getName());
                    }
                }
            }
        });
        return fragment_main_view;

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

    //해당 프래그먼트 title 던져주기
    @Override
    String getTitle() {
        return "셀카촬영";
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
