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
import com.sqisoft.remote.domain.SelfieZoneObject;

import java.util.ArrayList;

/**
 * Created by SQISOFT on 2016-09-23.
 */
public class MyMainListViewAdapter extends BaseAdapter{

 /*   private DBmanager dbmanager;*/
    Activity context;
    LayoutInflater inflater;
    int indexNum = 0;
    // 문자열을 보관 할 ArrayList
    private ArrayList<SelfieZoneObject> String_List = new ArrayList<SelfieZoneObject>();

    // 생성자
    public MyMainListViewAdapter(Activity context, ArrayList<SelfieZoneObject> arrayList) {
        this.context = context;
        this.String_List = arrayList;
       // String_List = new ArrayList<String>();
        inflater = LayoutInflater.from(this.context);

    }

    @Override
    public int getCount() {
        return String_List.size();
    }

    @Override
    public Object getItem(int position) {
        return String_List.get(position);
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
            convertView = inflater.inflate(R.layout.main_listitem, parent, false);

            // TextView에 현재 position의 인덱스 추가
            TextView index = (TextView) convertView.findViewById(R.id.index);
             indexNum = position+1;
            index.setText("셀카존 00"+indexNum);

            TextView title = (TextView) convertView.findViewById(R.id.title_text);
               title.setText(String_List.get(position).getmTitle());

            TextView desc = (TextView) convertView.findViewById(R.id.desc_text);
               desc.setText(String_List.get(position).getmDesc());

            ImageView imageView = (ImageView) convertView.findViewById(R.id.selfie_zone);
               imageView.setImageBitmap(String_List.get(position).getmTourBitmap());


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
                    Toast.makeText(context, "리스트 클릭 : "+String_List.get(pos).toString(), Toast.LENGTH_SHORT).show();
                }
            });


        return convertView;
    }


}



