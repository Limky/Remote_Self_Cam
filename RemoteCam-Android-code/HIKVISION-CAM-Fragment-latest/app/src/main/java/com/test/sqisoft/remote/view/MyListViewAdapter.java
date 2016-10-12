package com.test.sqisoft.remote.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.test.sqisoft.R;

import java.util.ArrayList;

/**
 * Created by SQISOFT on 2016-09-23.
 */
public class MyListViewAdapter extends BaseAdapter{

 /*   private DBmanager dbmanager;*/
    Activity context;
    LayoutInflater inflater;

    // 문자열을 보관 할 ArrayList
    private ArrayList<String> String_List = new ArrayList<String>();

    // 생성자
    public MyListViewAdapter(Activity context, ArrayList<String> arrayList) {
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

        if(convertView == null){
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, parent, false);

            // TextView에 현재 position의 인덱스 추가
            TextView index = (TextView) convertView.findViewById(R.id.index);
            index.setText(String_List.get(position));
            //String_List.get(position).getIndex()


      /*      // TextView에 현재 position의 문자열 추가
            TextView text1 = (TextView) convertView.findViewById(R.id.text1);


            // TextView에 현재 position의 문자열 추가
            TextView text2 = (TextView) convertView.findViewById(R.id.text2);


            TextView time = (TextView) convertView.findViewById(R.id.time);
         //   Log.i("time ",String_List.get(position).getScantime());

*/

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

        }
        return convertView;
    }


    public void add(String String_List){
        this.String_List.add(String_List);
    }


}



