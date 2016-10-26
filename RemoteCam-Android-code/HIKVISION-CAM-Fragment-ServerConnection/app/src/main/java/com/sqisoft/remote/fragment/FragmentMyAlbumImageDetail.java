package com.sqisoft.remote.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sqisoft.remote.R;
import com.sqisoft.remote.domain.LocalImageObject;
import com.sqisoft.remote.manager.DataManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMyAlbumImageDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMyAlbumImageDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMyAlbumImageDetail extends FragmentBase {

    ImageView imageView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<LocalImageObject> localImageObjects = new ArrayList<LocalImageObject>();
    private OnFragmentInteractionListener mListener;
    private int currentIndex;
    private ViewPager pager;
    private View myAlbumImageDetailView;
  //  private Button share,download,delete;
    private Integer[] pics = null;


    public FragmentMyAlbumImageDetail() {
        // Required empty public constructor
    }

    public FragmentMyAlbumImageDetail(int pos) {
        // Required empty public constructor
        this.localImageObjects = DataManager.getInstance().getLocalImageObjects();
        this.currentIndex = pos;
    }


    @Override
    String getTitle() {
        return null;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMyAlbumImageDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMyAlbumImageDetail newInstance(String param1, String param2) {
        FragmentMyAlbumImageDetail fragment = new FragmentMyAlbumImageDetail();
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
        myAlbumImageDetailView = inflater.inflate(R.layout.fragment_fragment_my_album_image_detail, container, false);

/*
        Gallery ga = (Gallery)myAlbumImageDetailView.findViewById(R.id.Gallery01);
        ga.setAdapter(new ImageAdapter(getActivity()));

        imageView = (ImageView)myAlbumImageDetailView.findViewById(R.id.ImageView01);
        ga.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getActivity(), "You have selected picture " + (arg2+1) + " of Antartica", Toast.LENGTH_SHORT).show();
                imageView.setImageResource(pics[arg2]);

            }

        });*/


    /*    ArrayList<ServerImageObject> imagesList = new ArrayList<>();
        for(int i = 0; i < localImageObject.size() ; i++)
            imagesList.add(new ServerImageObject(localImageObject.get(i).getmImagePath()));

        pager= (ViewPager)myAlbumImageDetailView.findViewById(R.id.pager);
        MyPageAdapter adapter= new MyPageAdapter(getActivity().getLayoutInflater(),imagesList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(currentIndex);


        // 버튼을 터치 했을 때 이벤트 발생
        Button save_btn = (Button) myAlbumImageDetailView.findViewById(R.id.save);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 터치 시 해당 아이템 이름 출력
                Toast.makeText(getActivity(), "Selected : save_btn", Toast.LENGTH_SHORT).show();
            }
        });



        // 버튼을 터치 했을 때 이벤트 발생
        Button share_sns_btn = (Button) myAlbumImageDetailView.findViewById(R.id.share_sns);
        share_sns_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 터치 시 해당 아이템 이름 출력
                Toast.makeText(getActivity(), "Selected : share_sns_btn", Toast.LENGTH_SHORT).show();
            }
        });



        // 버튼을 터치 했을 때 이벤트 발생
        Button delete_btn = (Button) myAlbumImageDetailView.findViewById(R.id.delete);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 터치 시 해당 아이템 이름 출력
                Toast.makeText(getActivity(), "Selected : delete_btn", Toast.LENGTH_SHORT).show();
            }
        });

*/



        return myAlbumImageDetailView;
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
/*

    public class ImageAdapter extends BaseAdapter {

        private Context ctx;
        int imageBackground;

        public ImageAdapter(Context c) {
            ctx = c;
            TypedArray ta = getContext().obtainStyledAttributes(R.styleable.Gallery1);
            //    imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
            ta.recycle();
        }



        @Override
        public int getCount() {

            return pics.length;
        }

        @Override
        public Object getItem(int arg0) {

            return arg0;
        }

        @Override
        public long getItemId(int arg0) {

            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ImageView iv = new ImageView(ctx);
            iv.setImageResource(pics[arg0]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(new Gallery.LayoutParams(150,150));

            //   iv.setBackgroundResource(imageBackground);
            return iv;
        }

    }*/

}
