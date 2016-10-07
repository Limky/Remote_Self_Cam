package com.test.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by SQISOFT on 2016-10-07.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter{

    private ArrayList<ImageObject> imagesList;
    private Activity context;
    ImageObject dataItem;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    // Adapter constructor
    public MyRecyclerAdapter(ArrayList<ImageObject> imagesList, Activity context) {
        this.context = context;
        this.imagesList = imagesList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, null);

        return new MyViewHolder(layoutView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        dataItem = imagesList.get(position);

        // Casting the viewHolder to MyViewHolder so I could interact with the views
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                Log.d("getImagePath","getImagePath = "+ imagesList.get(position).getImagePath());
/*
                fragmentManager = context.getFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.replacedLayout,Fragment_imageDetail.newInstance("13dd","asd"));*/

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("imagePath",imagesList.get(position).getImagePath());
                intent.putExtra("currentIndex",position);
                intent.putParcelableArrayListExtra("imagesList",imagesList);
                ((Activity) context).startActivity(intent);
                ((Activity) context).finish();
            }
        });

        //   myViewHolder.imageView.setImageBitmap(BitmapFactory.decodeFile(dataItem.getImagePath()));


        Uri uri = Uri.fromFile(new File(imagesList.get(position).getImagePath()));
        Picasso.with(context)
                .load(uri)
                .placeholder(R.drawable.dx)
                .resize(452,432)
                .into(myViewHolder.imageView);

    }

    @Override
    public int getItemCount() {

        return imagesList.size();
    }

    /** This is our ViewHolder class */
    public static class MyViewHolder extends RecyclerView.ViewHolder  {

        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView); // Must call super() first

            imageView = (ImageView) itemView.findViewById(R.id.imageView);


        }

    }


}
