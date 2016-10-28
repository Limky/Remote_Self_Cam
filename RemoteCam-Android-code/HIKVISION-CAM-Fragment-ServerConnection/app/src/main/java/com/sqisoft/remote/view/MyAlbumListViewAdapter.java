package com.sqisoft.remote.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sqisoft.remote.R;
import com.sqisoft.remote.domain.ServerImageDomain;
import com.sqisoft.remote.fragment.FragmentBase;
import com.sqisoft.remote.fragment.FragmentMyAlbumImageDetail;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.util.FragmentUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SQISOFT on 2016-09-23.
 */
public class MyAlbumListViewAdapter extends BaseAdapter{

 /*   private DBmanager dbmanager;*/
    Activity context;
    LayoutInflater inflater;
    int indexNum = 0;
    // 문자열을 보관 할 ArrayList
    private ArrayList<ServerImageDomain> serverImageDomains = DataManager.getInstance().getServerImageDomains();
    private Fragment adapterContext;

    // 생성자
    public MyAlbumListViewAdapter(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    // 생성자
    public MyAlbumListViewAdapter(Activity context, Fragment adapterContext) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.adapterContext = adapterContext;
    }

    @Override
    public int getCount() {
        return serverImageDomains.size();
    }

    @Override
    public Object getItem(int position) {
        return serverImageDomains.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();



        // view가 null일 경우 커스텀 레이아웃을 얻어 옴
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.my_album_listitem, parent, false);

        TextView selfieZone = (TextView) convertView.findViewById(R.id.my_album_selfie_zone);
        selfieZone.setText(serverImageDomains.get(position).getImageTitle());

        TextView imageTitle = (TextView) convertView.findViewById(R.id.my_album_image_date);
        imageTitle.setText(serverImageDomains.get(position).getImageDate());


         ImageView myAlbumImageView = (ImageView) convertView.findViewById(R.id.my_album_image);

    //    Uri uri = Uri.fromFile(new File(serverImageDomains.get(position).getImageUrl()));
       // Log.i("getmImagePath","getmImagePath "+ serverImageDomains.get(position).getmImagePath());
        Picasso.with(context)
                .load(serverImageDomains.get(position).getImageUrl())
                .placeholder(R.drawable.dx)
                .resize(452,432)
                .into(myAlbumImageView);


        final ToggleButton toggleButton = (ToggleButton)convertView.findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton.isChecked()){
                    toggleButton.setTextColor(Color.parseColor("#FFFFFF"));
                    toggleButton.setBackgroundResource(R.drawable.availabletime);

                    /**
                     *
                     *  local에 있는 사진 서버로 공개
                     *  공개 로직
                     *
                     *
                     * **/

                }else{
                    toggleButton.setTextColor(Color.parseColor("#BDBDBD"));
                    toggleButton.setBackgroundResource(R.drawable.notavailabletime);

                    /**
                     *
                     *  local에 있는 사진 서버로 비공개
                     *  비공개 로직
                     *
                     *
                     * **/

                }
            }
        });



         // 리스트 아이템을 터치 했을 때 이벤트 발생
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력

                   FragmentManager fragmentManager = adapterContext.getFragmentManager();
                   FragmentTransaction transaction = fragmentManager.beginTransaction();
                    FragmentBase fragment = new FragmentMyAlbumImageDetail(pos);
                    FragmentUtil.addFragment(fragment);
                    Toast.makeText(context, "리스트 클릭 : "+ serverImageDomains.get(pos).toString(), Toast.LENGTH_SHORT).show();
                }
            });


        return convertView;
    }




}



