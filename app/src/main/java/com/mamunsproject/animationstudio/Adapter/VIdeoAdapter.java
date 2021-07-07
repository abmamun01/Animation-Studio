package com.mamunsproject.animationstudio.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.mamunsproject.animationstudio.Model.TypeThumbnail;
import com.mamunsproject.animationstudio.Model.Video;
import com.mamunsproject.animationstudio.PlayerActivity;
import com.mamunsproject.animationstudio.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class VIdeoAdapter extends RecyclerView.Adapter<VIdeoAdapter.VideoHolder> {

    Context context;
    ArrayList<Video> arrayListVideo;

    private LinearLayout adView;
    private NativeAd nativeAd;
    View containerView;


    public VIdeoAdapter(Context context, ArrayList<Video> arrayListVideo) {
        this.context = context;
        this.arrayListVideo = arrayListVideo;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        containerView=LayoutInflater.from(context).inflate(R.layout.native_ad_layout,parent,false);
        View view=LayoutInflater.from(context).inflate(R.layout.sample_recycler, parent,false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        AudienceNetworkAds.initialize(context);


        nativeAd = new NativeAd(context, "4573092826051762_4573094539384924");

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e("TAG", "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e("TAG", "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d("TAG", "Native ad is loaded and ready to be displayed!");
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }


                nativeAd.unregisterView();

                // Add the Ad view into the ad container.
                LayoutInflater inflater = LayoutInflater.from(context);
                // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, holder.nativeAdLayout, false);
                holder.nativeAdLayout.addView(adView);

                // Add the AdOptionsView
                LinearLayout adChoicesContainer = containerView.findViewById(R.id.ad_choices_container);
                AdOptionsView adOptionsView = new AdOptionsView(context, nativeAd, holder.nativeAdLayout);
                adChoicesContainer.removeAllViews();
                adChoicesContainer.addView(adOptionsView, 0);

                // Create native UI using the ad metadata.
                MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
                TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
                TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
                Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

                // Set the Text.
                nativeAdTitle.setText(nativeAd.getAdvertiserName());
                nativeAdBody.setText(nativeAd.getAdBodyText());
                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
                sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

                // Create a list of clickable views
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdCallToAction);

                // Register the Title and CTA button to listen for clicks.
                nativeAd.registerViewForInteraction(
                        adView, nativeAdMedia, nativeAdIcon, clickableViews);



                holder.nativeAdLayout.setVisibility(View.VISIBLE);


            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d("TAG", "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d("TAG", "Native ad impression logged!");
            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());


































        Video video=arrayListVideo.get(position);
        if (video!=null){
            holder.textViewTitle.setText(video.snippet.title);
            if (video.snippet.thumbnails!=null){
                TypeThumbnail t=video.snippet.thumbnails.high;
                if (t==null) t=video.snippet.thumbnails.medium;
                if (t==null) t=video.snippet.thumbnails.standard;


            if (t==null) {

                Log.d("TAG", "onBindViewHolder: "+"Some video are deleted!");
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
//                                              context.startActivity(intent,options.toBundle());

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
        NativeAdLayout nativeAdLayout;


        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            textViewTitle=itemView.findViewById(R.id.tv_item_title);
            imageViewThumbnail=itemView.findViewById(R.id.iv_item_cover);
            relativeTransitionId=itemView.findViewById(R.id.relativeTranstionId);
            nativeAdLayout = itemView.findViewById(R.id.native_ad_container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

   /* private void loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = new NativeAd(context, "CAROUSEL_IMG_SQUARE_LINK#4573092826051762_4573094539384924");

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e("TAG", "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e("TAG", "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d("TAG", "Native ad is loaded and ready to be displayed!");
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }


                inflateAd(nativeAd);


            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d("TAG", "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d("TAG", "Native ad impression logged!");
            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }*/

/*    private void inflateAd(NativeAd nativeAd) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout_1, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(NativeAdActivity.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }*/
}
