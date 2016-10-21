package com.sqisoft.remote.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sqisoft.remote.R;
import com.sqisoft.remote.domain.ServerImageObject;
import com.sqisoft.remote.fragment.FragmentBase;
import com.sqisoft.remote.fragment.FragmentImageDetail;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.util.FragmentUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by SQISOFT on 2016-10-07.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter{

    private ArrayList<ServerImageObject> serverImageObjects;
    private Fragment adapterContext;
    private ServerImageObject dataItem;
    private FragmentManager fragmentManager;
    private  FragmentTransaction transaction;
    private  Context context;

    // Adapter constructor
    public MyRecyclerAdapter(Fragment adapterContext, Context context) {
        
        this.adapterContext = adapterContext;
        this.serverImageObjects = DataManager.getInstance().getServerImageObjects();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.server_gallery_item_layout, null);

        return new MyViewHolder(layoutView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        dataItem = serverImageObjects.get(position);

        // Casting the viewHolder to MyViewHolder so I could interact with the views
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                Log.d("getmImagePath","getmImagePath = "+ serverImageObjects.get(position).getmImagePath());

                fragmentManager = adapterContext.getFragmentManager();
                transaction = fragmentManager.beginTransaction();
                FragmentBase fragment = new FragmentImageDetail(serverImageObjects,position);
                FragmentUtil.addFragment(fragment);
              //  transaction.add(R.id.replacedLayout, fragment,"FImageDetail").addToBackStack(null).commit();

            }
        });

        myViewHolder.mImageTttleTextView.setText(serverImageObjects.get(position).getmImageTitle());


        Uri uri = Uri.fromFile(new File(serverImageObjects.get(position).getmImagePath()));
        Picasso.with(context)
                .load(uri)
                .placeholder(R.drawable.dx)
                .resize(452,432)
                .into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {

        return serverImageObjects.size();
    }

    /** This is our ViewHolder class */
    public static class MyViewHolder extends RecyclerView.ViewHolder  {

        public ImageView imageView;
        public TextView mImageTttleTextView;

        public MyViewHolder(View itemView) {
            super(itemView); // Must call super() first

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            mImageTttleTextView = (TextView) itemView.findViewById(R.id.server_image_title);

        }

    }


}
