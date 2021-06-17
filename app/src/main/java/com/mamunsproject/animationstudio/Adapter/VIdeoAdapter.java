package com.mamunsproject.animationstudio.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mamunsproject.animationstudio.Model.TypeThumbnail;
import com.mamunsproject.animationstudio.Model.Video;
import com.mamunsproject.animationstudio.PlayerActivity;
import com.mamunsproject.animationstudio.R;


import java.util.ArrayList;

import butterknife.ButterKnife;

public class VIdeoAdapter extends RecyclerView.Adapter<VIdeoAdapter.VideoHolder> {

    Context context;
    ArrayList<Video> arrayListVideo;

    public VIdeoAdapter(Context context, ArrayList<Video> arrayListVideo) {
        this.context = context;
        this.arrayListVideo = arrayListVideo;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.sample_recycler, parent,false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        Video video=arrayListVideo.get(position);
        if (video!=null){
            holder.textViewTitle.setText(video.snippet.title);
            if (video.snippet.thumbnails!=null){
                TypeThumbnail t=video.snippet.thumbnails.high;
                if (t==null) t=video.snippet.thumbnails.medium;
                if (t==null) t=video.snippet.thumbnails.standard;


            if (t==null) {
                Toast.makeText(context, "Some Video Deleted!", Toast.LENGTH_SHORT).show();
            }else {
                Glide.with(context).load(t.url).into(holder.imageViewThumbnail);
            }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String id_=video.contentDetails.videoId;
                        Intent intent=new Intent(context, PlayerActivity.class);
                        intent.putExtra("videoid",id_);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

//                        Pair[] pairs=new Pair[1];
//                        pairs[0]=new Pair<View,String>(holder.relativeTransitionId,"playerTransition");

//                        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation((Activity) context,pairs);
                        //                      context.startActivity(intent,options.toBundle());
                    }
                });




            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayListVideo.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_item_title)
//        TextView textViewTitle;
//        @BindView(R.id.iv_item_cover)
//        ImageView imageViewThumbnail;

        TextView textViewTitle;
        ImageView imageViewThumbnail;
        RelativeLayout relativeTransitionId;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            textViewTitle=itemView.findViewById(R.id.tv_item_title);
            imageViewThumbnail=itemView.findViewById(R.id.iv_item_cover);
            relativeTransitionId=itemView.findViewById(R.id.relativeTranstionId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
