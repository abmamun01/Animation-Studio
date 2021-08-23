package com.mamunsproject.animationstudio.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mamunsproject.animationstudio.Adapter.SliderAdapter;
import com.mamunsproject.animationstudio.Adapter.VIdeoAdapter;
import com.mamunsproject.animationstudio.Model.ResponseVideo;
import com.mamunsproject.animationstudio.Model.SliderModel;
import com.mamunsproject.animationstudio.Model.Video;
import com.mamunsproject.animationstudio.R;
import com.mamunsproject.animationstudio.Retrofit.ApiClient;
import com.mamunsproject.animationstudio.Retrofit.ApiInterface;
import com.mamunsproject.animationstudio.Utils.MyConsts;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    SliderView sliderView;
    VIdeoAdapter vIdeoAdapter;
    ArrayList<Video> arrayListVideo;
    ApiInterface apiInterface;
    LottieAnimationView animationView;
    public static FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    public static AdRequest adRequest;
    public static InterstitialAd mInterstitialAd;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        animationView=view.findViewById(R.id.animationView);
        recyclerView = view.findViewById(R.id.recyclerMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        arrayListVideo = new ArrayList<>();
        vIdeoAdapter = new VIdeoAdapter(getContext(), arrayListVideo);
        recyclerView.setAdapter(vIdeoAdapter);
        adRequest = new AdRequest.Builder().build();
        context=getContext();


        loadInterstitialAds(getContext());

        DocumentReference documentReference=firebaseFirestore.
                collection("AllCartoonPlayListKey").document("AllCartoonID");


        documentReference
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()){

                    String key=documentSnapshot.getString( "AllCartoonID");
                    getVideos(key);
                    Log.d("TAGD", "onSuccess: "+key);


                }else {
                    Toast.makeText(getContext(), "Does'nt Exist", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getContext(), "Failed firestore!", Toast.LENGTH_SHORT).show();
            }
        });


        SliderView sliderView = view.findViewById(R.id.imageSlider);
        List<SliderModel> list = new ArrayList<>();
        SliderAdapter adapter = new SliderAdapter(getContext(), list);
        sliderView.setSliderAdapter(adapter);

        firebaseFirestore.collection("SliderImage")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        //ager data delete kore notun data add korbe
                        list.clear();
                        for (DocumentSnapshot snapshot:value.getDocuments()  ){

                            SliderModel model= snapshot.toObject(SliderModel.class);
                            assert model != null;
                            model.setId(snapshot.getId());
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });



        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();



        return view;

    }

    private void getVideos(String key) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseVideo> callVideo = apiInterface.getAllVideos(300, key
                , MyConsts.APIKEY);

        callVideo.enqueue(new Callback<ResponseVideo>() {
            @Override
            public void onResponse(Call<ResponseVideo> call, Response<ResponseVideo> response) {

                ResponseVideo responseVideo = response.body();
                if (responseVideo != null) {
                    animationView.setVisibility(View.GONE);
                    if (responseVideo.items.size() > 0) {
                        for (int i = 0; i < responseVideo.items.size(); i++) {
                            arrayListVideo.add(responseVideo.items.get(i));
                        }
                        vIdeoAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), "There is No Video In Your Channel!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseVideo> call, Throwable t) {

            }
        });

    }






    public static void loadInterstitialAds(Context context){
        //--------------------INTERSTITIAL ADS---------------------
        InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("MaINAcADSFDFDFSSS", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("MaINAcADSFDFDFSSS", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


        //--------------------INTERSTITIAL ADS---------------------
    }
    public static void showInterstitial(Context context) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show((Activity) context);
        } else {
            Log.d("MaINAcADSFDFDFSSS", "The interstitial ad wasn't ready yet.");
        }
    }


}


/*
        AudienceNetworkAds.initialize(context);


        nativeAd = new NativeAd(context, "4573092826051762_4627825243911853");

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





























*/