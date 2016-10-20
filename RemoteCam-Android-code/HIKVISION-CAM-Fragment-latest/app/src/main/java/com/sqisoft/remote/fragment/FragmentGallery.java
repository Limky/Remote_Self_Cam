package com.sqisoft.remote.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.sqisoft.remote.R;
import com.sqisoft.remote.domain.ImageObject;
import com.sqisoft.remote.view.MyRecyclerAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentGallery.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentGallery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGallery extends  FragmentBase {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    FragmentManager manager;
    FragmentTransaction transaction;


    ArrayList<String> thumbsDatas = new ArrayList<String>();
    ArrayList<ImageObject> imagesList = new ArrayList<ImageObject>();
    Cursor imageCursor;

    private OnFragmentInteractionListener mListener;
    View albumView;

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;


    @Override
    String getTitle() {
        return "셀카 갤러리";
    }

    public FragmentGallery() {
        // Required empty public constructor
        mContext = getActivity();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Gallery.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGallery newInstance(String param1, String param2) {
        FragmentGallery fragment = new FragmentGallery();
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
        mContext = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        albumView = inflater.inflate(R.layout.fragment_gallery, container, false);

        RecyclerView recyclerView = (RecyclerView) albumView.findViewById(R.id.recycler_view);

        setTitle("셀카 갤러리");

        getThumbInfo();

        MyRecyclerAdapter myAdapter = new MyRecyclerAdapter(imagesList,this,getActivity());
        recyclerView.setAdapter(myAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));

        spinner = (Spinner) albumView.findViewById(R.id.spin);
    /*    adapter = ArrayAdapter.createFromResource(getActivity(),R.array.country_names,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);*/



        RecyclerViewHeader header = (RecyclerViewHeader) albumView.findViewById(R.id.header);
        header.attachTo(recyclerView);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

                String selectedItemText = parent.getItemAtPosition(pos).toString();
                Toast toastSpinnerSelection = Toast.makeText(getActivity(), selectedItemText, Toast.LENGTH_SHORT);

                toastSpinnerSelection.setGravity(Gravity.LEFT|Gravity.BOTTOM,20,150);
                toastSpinnerSelection.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                //Another interface callback
            }

        });



        return albumView;
    }



    @Override
    public void onResume(){
      Toast.makeText(getActivity(), "Resume "+"갤러리 스타트", Toast.LENGTH_SHORT).show();

        setTitle("셀카 갤러리");
        super.onResume();
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

    @Override
    public void onStart(){
        super.onStart();


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
            int num = 0;
            int nCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA); //bitmap
            do {
                thumbsID = imageCursor.getString(thumbsIDCol);
                thumbsData = imageCursor.getString(thumbsDataCol);
                thumbsImageID = imageCursor.getString(thumbsImageIDCol);
                thumbsImageID = imageCursor.getString(nCol);
                imgSize = imageCursor.getString(thumbsSizeCol);
                num++;
                if (thumbsImageID != null && thumbsImageID.startsWith("/storage/emulated/0/DCIM/HIKVISION")){
                    Log.d("test","img is " + thumbsImageID);
                    imagesList.add(new ImageObject(thumbsData));
                }
            }while (imageCursor.moveToNext());
        }
        imageCursor.close();
        return;
    }


}

