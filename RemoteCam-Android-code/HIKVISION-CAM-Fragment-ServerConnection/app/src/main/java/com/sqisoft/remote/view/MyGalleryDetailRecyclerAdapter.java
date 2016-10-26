package com.sqisoft.remote.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sqisoft.remote.R;
import com.sqisoft.remote.domain.ServerImageDomain;
import com.sqisoft.remote.domain.ServerImageObject;
import com.sqisoft.remote.manager.DataManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SQISOFT on 2016-10-07.
 */

public class MyGalleryDetailRecyclerAdapter extends RecyclerView.Adapter{

    private ArrayList<ServerImageDomain> serverImageDomains;
    private Fragment adapterContext;
    private ServerImageObject dataItem;
    private FragmentManager fragmentManager;
    private  FragmentTransaction transaction;
    private  Context context;
    private ImageLoader mImageLoader;
    private ImageView mDetail_image;
    // Adapter constructor
    public MyGalleryDetailRecyclerAdapter(Fragment adapterContext, Context context, ImageView detail_image) {

        this.adapterContext = adapterContext;
        this.serverImageDomains = DataManager.getInstance().getServerImageDomains();
        this.context = context;
        this.mDetail_image = detail_image;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.server_gallery_item_layout, null);

        return new MyViewHolder(layoutView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        // Casting the viewHolder to MyViewHolder so I could interact with the views
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.server_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(context)
                        .load(serverImageDomains.get(position).getImageUrl())
                        .placeholder(R.drawable.dx)
                        .into(mDetail_image);
            }
        });


        myViewHolder.mImageTttleTextView.setText(serverImageDomains.get(position).getImageTitle());

        Picasso.with(context)
                .load(serverImageDomains.get(position).getImageUrl())
                .placeholder(R.drawable.dx)
                .resize(352,332)
                .into(myViewHolder.server_imageview);
    }

    @Override
    public int getItemCount() {

        return serverImageDomains.size();
    }

    /** This is our ViewHolder class */
    public static class MyViewHolder extends RecyclerView.ViewHolder  {

        public ImageView server_imageview;
        public NetworkImageView mNetwokrImageView;
        public TextView mImageTttleTextView;

        public MyViewHolder(View itemView) {
            super(itemView); // Must call super() first

            server_imageview = (ImageView) itemView.findViewById(R.id.server_imageview);
     //       mNetwokrImageView = (NetworkImageView) itemView.findViewById(R.id.network_imageview);
            mImageTttleTextView = (TextView) itemView.findViewById(R.id.server_image_title);

        }

    }


}
