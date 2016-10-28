package com.sqisoft.remote.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sqisoft.remote.R;
import com.sqisoft.remote.domain.LocalImageObject;
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
    private Cursor imageCursor;
    ArrayList<LocalImageObject> localImageObjects = new ArrayList<LocalImageObject>();


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

        getThumbInfo();

        setTitle("MY 앨범");
        setGalleryButton(true);

        MyAlbumListViewAdapter myAlbumListViewAdapter = new MyAlbumListViewAdapter(getActivity(),this);

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    private void getThumbInfo(){

        String[] proj = new String[]{MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE};

        final String orderBy = MediaStore.Images.Media.DATE_ADDED;

        imageCursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, orderBy + " DESC");

        if (imageCursor != null && imageCursor.moveToFirst()){
            String title;
            String thumbsID;
            String thumbsImageID;
            String thumbsData;
            String data;
            String imgSize;


            int thumbsIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
            int thumbsDataCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            int thumbsImageIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            int thumbsSizeCol = imageCursor.getColumnIndex(MediaStore.Images.Media.SIZE);
            int thumbsTitle = imageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            int num = 0;
            int nCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA); //bitmap
            do {
                thumbsID = imageCursor.getString(thumbsIDCol);
                thumbsData = imageCursor.getString(thumbsDataCol);
                thumbsImageID = imageCursor.getString(thumbsImageIDCol);
                thumbsImageID = imageCursor.getString(nCol);
                imgSize = imageCursor.getString(thumbsSizeCol);
                title = imageCursor.getString(thumbsTitle);

                num++;

                if (thumbsImageID != null && thumbsImageID.startsWith("/storage/emulated/0/DCIM/HIKVISION")){
                  Log.d("test","img is " + thumbsImageID);
                    localImageObjects.add(new LocalImageObject("오죽헌 안채 005",title, thumbsData , true));
                    // imagesList.add(new ServerImageObject(thumbsData));
                }
            }while (imageCursor.moveToNext());
        }
        imageCursor.close();
        return;
    }


}
