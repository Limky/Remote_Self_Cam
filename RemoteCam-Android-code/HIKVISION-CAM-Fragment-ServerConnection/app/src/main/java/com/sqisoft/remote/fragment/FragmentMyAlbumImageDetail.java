package com.sqisoft.remote.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sqisoft.remote.R;
import com.sqisoft.remote.domain.LocalImageObject;
import com.sqisoft.remote.domain.ServerImageDomain;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.view.MyAlbumDetailRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

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
    private Bitmap bitmap;
  //  private Button share,download,delete;

   ArrayList<ServerImageDomain> serverImageDomains = DataManager.getInstance().getServerImageDomains();



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
        setGalleryButton(true);
        setTitle("MY 앨범 사진보기");
        final RecyclerView image_detail_recyclerView = (RecyclerView) myAlbumImageDetailView.findViewById(R.id.image_detail_recycler_view);
        ImageView my_album_detail_image = (ImageView) myAlbumImageDetailView.findViewById(R.id.my_album_detail_image);

        MyAlbumDetailRecyclerAdapter myAdapter = new MyAlbumDetailRecyclerAdapter(this,getActivity(),my_album_detail_image);

        image_detail_recyclerView.setAdapter(myAdapter);
        image_detail_recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1, OrientationHelper.HORIZONTAL,false));


     /*
        Thread mThread = new Thread(){

            @Override
            public void run(){

                try {
                    URL url = new URL(serverImageDomains.get(currentIndex).getImageUrl());
                    try {

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        };

        mThread.start();
        try {
            mThread.join();
            Bitmap resized = Bitmap.createScaledBitmap(bitmap,1920, 1080, true);
            my_album_detail_image.setImageBitmap(resized);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/



     /*   bitmap = DataManager.getInstance().getWebimage(serverImageDomains.get(currentIndex).getImageUrl());
        my_album_detail_image.setImageBitmap(bitmap);
*/

        Picasso.with(getActivity())
                .load(serverImageDomains.get(currentIndex).getImageUrl())
                .placeholder(R.drawable.dx)
                .resize(1420,580)
                .into(my_album_detail_image);


        PhotoViewAttacher attacher = new PhotoViewAttacher(my_album_detail_image);
        attacher.setScaleType(ImageView.ScaleType.FIT_XY);
        attacher.update();


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
