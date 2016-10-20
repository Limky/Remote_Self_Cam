package com.sqisoft.remote.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sqisoft.remote.R;
import com.sqisoft.remote.domain.MyImageObject;

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
    private ArrayList<MyImageObject> myImageObject = new ArrayList<MyImageObject>();

    // 생성자
    public MyAlbumListViewAdapter(Activity context, ArrayList<MyImageObject> arrayList) {
        this.context = context;
        this.myImageObject = arrayList;
       // myImageObject = new ArrayList<String>();
        inflater = LayoutInflater.from(this.context);

    }
    
    @Override
    public int getCount() {
        return myImageObject.size();
    }

    @Override
    public Object getItem(int position) {
        return myImageObject.get(position);
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
        selfieZone.setText(myImageObject.get(position).getmSelfieZone());

        ImageView myAlbumImageView = (ImageView) convertView.findViewById(R.id.my_album_image);
        myAlbumImageView.setImageBitmap(myImageObject.get(position).getmBitmap());



/*            // 버튼을 터치 했을 때 이벤트 발생
            Button btn = (Button) convertView.findViewById(R.id.btn_test);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    Toast.makeText(context, "Selected : " + pos, Toast.LENGTH_SHORT).show();
                }
            });*/


         // 리스트 아이템을 터치 했을 때 이벤트 발생
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    Toast.makeText(context, "리스트 클릭 : "+myImageObject.get(pos).toString(), Toast.LENGTH_SHORT).show();
                }
            });


        return convertView;
    }


}



