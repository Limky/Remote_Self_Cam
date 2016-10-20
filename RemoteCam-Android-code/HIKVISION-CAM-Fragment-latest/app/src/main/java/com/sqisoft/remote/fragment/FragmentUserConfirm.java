package com.sqisoft.remote.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sqisoft.remote.R;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.util.Log;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentUserConfirm.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentUserConfirm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUserConfirm extends FragmentBase {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ImageView catching_img,textimage;
    private  View fragment_fragment_user_confirm;

    FragmentManager manager;
    FragmentTransaction transaction;

    Button cancel_remote_btn,save_remote_image_btn;

    public FragmentUserConfirm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUserConfirm.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentUserConfirm newInstance(String param1, String param2) {
        FragmentUserConfirm fragment = new FragmentUserConfirm();
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
        // Inflate the layout for this fragment
        fragment_fragment_user_confirm = inflater.inflate(R.layout.fragment_fragment_user_confirm, container, false);

        catching_img = (ImageView) fragment_fragment_user_confirm.findViewById(R.id.catching_img);
        setTitle("저장 완료");

        //프래그먼트 페이지 이동을 위한 초기 설정
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        cancel_remote_btn = (Button) fragment_fragment_user_confirm.findViewById(R.id.cancel_remote_btn);
        cancel_remote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**원격서버로 이미지 저장을 취소하는 로직..
                 ..
                 ..
                 ..
                 **/
                transaction.replace(R.id.replacedLayout, new FragmentMain(),"FMain").commit();

            }
        });


        save_remote_image_btn = (Button) fragment_fragment_user_confirm.findViewById(R.id.save_remote_image_btn);
        save_remote_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**원격서버로 이미지를 저장하는 로직...
                 ..
                 ..
                 ..
                 **/
                transaction.replace(R.id.replacedLayout, new FragmentMain(),"FMain").commit();

            }
        });




        fragment_fragment_user_confirm.setFocusableInTouchMode(true);
        fragment_fragment_user_confirm.requestFocus();
        fragment_fragment_user_confirm.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("tag", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    Toast.makeText(getActivity(),"백버튼 인터셉터",Toast.LENGTH_LONG).show();
                    Log.i("tag", "onKey Back listener is working!!!");

                    transaction.replace(R.id.replacedLayout, new FragmentMain(),"FMain").commit();
                    // setTitle("셀카 촬영");

                    //    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                } else {
                    return false;
                }
            }
        });


        //사용자가 원격서버에 이미지 저장 유무를 묻기위해 임시로 저장한 bitmap을 불러온다.
        DataManager dataManager = DataManager.getInstance();
        catching_img.setImageBitmap(dataManager.getBitmap());

        return fragment_fragment_user_confirm;
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
        return "저장완료";
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
