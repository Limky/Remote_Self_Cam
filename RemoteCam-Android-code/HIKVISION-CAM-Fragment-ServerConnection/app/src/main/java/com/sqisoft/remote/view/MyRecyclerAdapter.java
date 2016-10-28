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
import com.sqisoft.remote.fragment.FragmentBase;
import com.sqisoft.remote.fragment.FragmentImageDetail;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.util.FragmentUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SQISOFT on 2016-10-07.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter{

    private ArrayList<ServerImageDomain> serverImageDomains;
    private Fragment adapterContext;
    private ServerImageObject dataItem;
    private FragmentManager fragmentManager;
    private  FragmentTransaction transaction;
    private  Context context;
    private ImageLoader mImageLoader;
    // Adapter constructor
    public MyRecyclerAdapter(Fragment adapterContext, Context context) {

        this.adapterContext = adapterContext;
        this.serverImageDomains = DataManager.getInstance().getServerImageDomains();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_listitem, null);

        return new MyViewHolder(layoutView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        // Casting the viewHolder to MyViewHolder so I could interact with the views
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.mServer_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = adapterContext.getFragmentManager();
                transaction = fragmentManager.beginTransaction();
                FragmentBase fragment = new FragmentImageDetail(position);
                FragmentUtil.addFragment(fragment);

            }
        });


        myViewHolder.mImageTttleTextView.setText(serverImageDomains.get(position).getImageTitle());

        Picasso.with(context)
                .load(serverImageDomains.get(position).getImageUrl())
                .placeholder(R.drawable.dx)
                .resize(452,432)
                .into(myViewHolder.mServer_imageview);
    }

    @Override
    public int getItemCount() {

        return serverImageDomains.size();
    }

    /** This is our ViewHolder class */
    public static class MyViewHolder extends RecyclerView.ViewHolder  {

        public ImageView mServer_imageview;
        public NetworkImageView mNetwokrImageView;
        public TextView mImageTttleTextView;

        public MyViewHolder(View itemView) {
            super(itemView); // Must call super() first

            mServer_imageview = (ImageView) itemView.findViewById(R.id.server_imageview);
            //       mNetwokrImageView = (NetworkImageView) itemView.findViewById(R.id.network_imageview);
            mImageTttleTextView = (TextView) itemView.findViewById(R.id.server_image_title);

        }

    }


}