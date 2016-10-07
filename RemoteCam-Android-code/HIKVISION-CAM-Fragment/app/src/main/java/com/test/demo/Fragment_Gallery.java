package com.test.demo;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Gallery.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Gallery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Gallery extends Fragment {
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

    public Fragment_Gallery() {
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
    public static Fragment_Gallery newInstance(String param1, String param2) {
        Fragment_Gallery fragment = new Fragment_Gallery();
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

        RecyclerView recyclerView = (RecyclerView) albumView.findViewById(R.id.recyclerView);

        getThumbInfo();

        MyRecyclerAdapter myAdapter = new MyRecyclerAdapter(imagesList,getActivity());
        recyclerView.setAdapter(myAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));

        return albumView;
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
























/*



package com.test.demo;

        import android.content.Context;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.BaseAdapter;
        import android.widget.GridView;
        import android.widget.ImageView;

        import java.util.ArrayList;


*/
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Gallery.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Gallery#newInstance} factory method to
 * create an instance of this fragment.
 *//*

public class Fragment_Gallery extends Fragment {
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

    private OnFragmentInteractionListener mListener;
    View albumView;

    public Fragment_Gallery() {
        // Required empty public constructor
        mContext = getActivity();
    }

    */
/**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Gallery.
     *//*

    // TODO: Rename and change types and number of parameters
    public static Fragment_Gallery newInstance(String param1, String param2) {
        Fragment_Gallery fragment = new Fragment_Gallery();
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

        mContext = getActivity();
        GridView gv =  (GridView) albumView.findViewById(R.id.ImgGridView);
        final  ImageAdapter ia = new ImageAdapter(getContext());
        gv.setAdapter(ia);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ia.callImageViewer(position);
            }
        });


        return albumView;
    }


    */
/**==========================================
     * 		        Adapter class
     * ==========================================*//*



    public class ImageAdapter extends BaseAdapter {
        private String imgData;
        private String geoData;
        private ArrayList<String> thumbsDataList;
        private ArrayList<String> thumbsIDList;

        ImageAdapter(Context c){
            mContext = c;
            thumbsDataList = new ArrayList<String>();
            thumbsIDList = new ArrayList<String>();
            getThumbInfo(thumbsIDList, thumbsDataList);
        }

        public final void callImageViewer(int selectedIndex){

            // String imgPath = getImageInfo(imgData, geoData, thumbsIDList.get(selectedIndex));

            manager = getFragmentManager();
            transaction = manager.beginTransaction();

         //   transaction.replace(R.id.replacedLayout, Fragment_imageDetail.newInstance(imgPath,"Limky값")).addToBackStack("tag").commit();

        }

        public boolean deleteSelected(int sIndex){
            return true;
        }

        public int getCount() {
            return thumbsIDList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null){
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(360, 300));
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(1, 1, 1, 1);
            }else{
                imageView = (ImageView) convertView;
            }
            BitmapFactory.Options bo = new BitmapFactory.Options();
            bo.inSampleSize =10;
            Bitmap bmp = BitmapFactory.decodeFile(thumbsDataList.get(position), bo);


            Bitmap resized = Bitmap.createScaledBitmap(bmp, 150 , 150, true);
            imageView.setImageBitmap(resized);

            return imageView;
        }

        private void getThumbInfo(ArrayList<String> thumbsIDs, ArrayList<String> thumbsDatas){

            Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
            String[] proj = new String[]{MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.SIZE};
            final String orderBy = MediaStore.Images.Media.DATE_ADDED;

            Cursor imageCursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, orderBy + " DESC");

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
                //    Log.d("test","img is " + "ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ");
                do {
                    Log.d("test","img " + "로드 되었습니다.");

                    thumbsID = imageCursor.getString(thumbsIDCol);
                    thumbsData = imageCursor.getString(thumbsDataCol);
                    thumbsImageID = imageCursor.getString(thumbsImageIDCol);
                    thumbsImageID = imageCursor.getString(nCol);
                    imgSize = imageCursor.getString(thumbsSizeCol);
                    num++;
                    if (thumbsImageID != null && thumbsImageID.startsWith("/storage/emulated/0/DCIM/HIKVISION")){
                        Log.d("test","img is " + thumbsImageID);
                        thumbsIDs.add(thumbsID);
                        thumbsDatas.add(thumbsData);
                    }
                }while (imageCursor.moveToNext());
            }
            imageCursor.close();
            return;
        }

        private String getImageInfo(String ImageData, String Location, String thumbID){

            String imageDataPath = null;
            String[] proj = {MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.SIZE};

            final String orderBy = MediaStore.Images.Media.DATE_ADDED;

            Cursor imageCursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, "_ID='"+ thumbID +"'", null, orderBy + " DESC");
            Log.d("test","img is " + "getImageInfo");
            if (imageCursor != null && imageCursor.moveToFirst()){
                String strImage;
                if (imageCursor.getCount() > 0){
                    int imgData = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    imageDataPath = imageCursor.getString(imgData);
                }

                int nCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA); //bitmap
                do {
                    strImage = imageCursor.getString(nCol);
                    if (strImage != null && strImage.startsWith("/storage/emulated/0/DCIM/HIKVISION")){
                        Log.d("test","img is " + strImage);
                        //ImageList에 삽입
                    }
                }while (imageCursor.moveToNext());

            }

            imageCursor.close();
            return imageDataPath;
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

    */
/**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}*/
