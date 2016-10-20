package com.sqisoft.remote.fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sqisoft.remote.R;
import com.sqisoft.remote.domain.MyImageObject;
import com.sqisoft.remote.view.MyAlbumListViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMyAlbum.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMyAlbum#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMyAlbum extends FragmentBase {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View my_album_view;
    private ListView m_ListView;
    private OnFragmentInteractionListener mListener;

    public FragmentMyAlbum() {
        // Required empty public constructor
    }

    @Override
    String getTitle() {
        return "MY 앨범";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMyAlbum.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMyAlbum newInstance(String param1, String param2) {
        FragmentMyAlbum fragment = new FragmentMyAlbum();
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
        my_album_view = inflater.inflate(R.layout.fragment_fragment_my_album, container, false);
        m_ListView = (ListView) my_album_view.findViewById(R.id.list);

 /*       ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");*/

        ArrayList<MyImageObject> myImageObjects = new ArrayList<MyImageObject>();
        myImageObjects.add(new MyImageObject("오죽헌 안채 005","2016.09.27", BitmapFactory.decodeResource(getResources(),R.drawable.tour_img_1),false));
        myImageObjects.add(new MyImageObject("오죽헌 입구 012","2016.09.27", BitmapFactory.decodeResource(getResources(),R.drawable.tour_img_2),false));
        myImageObjects.add(new MyImageObject("오죽헌 정경 001","2016.09.27", BitmapFactory.decodeResource(getResources(),R.drawable.tour_img_3),false));
        myImageObjects.add(new MyImageObject("오죽헌 안채 005","2016.09.27", BitmapFactory.decodeResource(getResources(),R.drawable.tour_img_1),false));
        myImageObjects.add(new MyImageObject("오죽헌 안채 005","2016.09.27", BitmapFactory.decodeResource(getResources(),R.drawable.tour_img_2),false));

        MyAlbumListViewAdapter myAlbumListViewAdapter = new MyAlbumListViewAdapter(getActivity(),myImageObjects);

        m_ListView.setAdapter(myAlbumListViewAdapter);


        return  my_album_view;

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
