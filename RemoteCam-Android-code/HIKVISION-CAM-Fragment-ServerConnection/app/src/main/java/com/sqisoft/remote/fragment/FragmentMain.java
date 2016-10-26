package com.sqisoft.remote.fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.sqisoft.remote.R;
import com.sqisoft.remote.data.ResponseListener;
import com.sqisoft.remote.domain.SelfieZoneObject;
import com.sqisoft.remote.domain.ServerImageDomain;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.util.CommandUtil;
import com.sqisoft.remote.util.FragmentUtil;
import com.sqisoft.remote.util.Log;
import com.sqisoft.remote.view.MyMainListViewAdapter;

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

    MyMainListViewAdapter myMainListViewAdapter;
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
    public void onResume(){
        Toast.makeText(getActivity(), "Resume "+ "Main 스타트", Toast.LENGTH_SHORT).show();

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setTitle("셀카 촬영");

        // Inflate the layout for this fragment
        fragment_main_view = inflater.inflate(R.layout.fragment_main, container, false);

      //  tourImageView = (ImageView)  fragment_main_view.findViewById(R.id.tour_imageview);
        m_ListView = (ListView) fragment_main_view.findViewById(R.id.list);
        //투어 이미지 설정
       // tourImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.main_tour_img_2));

        //프래그먼트 페이지 이동을 위한 초기 설정
       /*  manager = getFragmentManager();
         transaction = manager.beginTransaction();*/

        Button cameraBtn = (Button)fragment_main_view.findViewById(R.id.camera_btn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.addFragment(new FragmentCamera());
            //    transaction.replace(R.id.replacedLayout, new FragmentCamera(),"FCamera").addToBackStack(null).commit();

            }
        });


        ArrayList<SelfieZoneObject> selfieZoneObjects = new ArrayList<>();
        selfieZoneObjects.add(new SelfieZoneObject("오죽헌 입구","강원도 강릉시 죽헌동(竹軒洞)에 있는 조선 중기의 목조건물.", BitmapFactory.decodeResource(getResources(),R.drawable.tour_img_1)));
        selfieZoneObjects.add(new SelfieZoneObject("오죽헌 안채","1963년 1월 21일 보물 제165호로 지정되었다. 정면 3칸, 측면 2칸의 단층 팔작지붕 양식이다.", BitmapFactory.decodeResource(getResources(),R.drawable.tour_img_2)));
        selfieZoneObjects.add(new SelfieZoneObject("오죽헌 전경","지녀 주심포집에서 익공집으로의 변천과정을 보여주는 중요한 구조이다.", BitmapFactory.decodeResource(getResources(),R.drawable.tour_img_3)));

        myMainListViewAdapter = new MyMainListViewAdapter(getActivity(), selfieZoneObjects);

        View header = inflater.inflate(R.layout.listview_header,null,false);
        View footer = inflater.inflate(R.layout.listview_footer,null,false);
        m_ListView.addHeaderView(header);
        m_ListView.addFooterView(footer);
        m_ListView.setAdapter(myMainListViewAdapter);



        CommandUtil.getZone(new ResponseListener<ServerImageDomain[]>() {

            @Override
            public void response(boolean success, ServerImageDomain[] data) {
                Log.d("test","메인 안에 response (1)");
                if(success && data != null) {
                    Log.d("test","response  피니시(11)");
                    ArrayList<ServerImageDomain> serverImageDomains = new ArrayList<ServerImageDomain>();
                    for (int i = 0; i < data.length; i++) {
                        serverImageDomains.add(data[i]);
                        DataManager.getInstance().setServerImageDomains(serverImageDomains);
                        Log.d("test", data[i].getImageTitle());
                    }
                }
            }
        });

    //   MyVolleyUtil.getInstance().sendRequest();


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
