package com.mamunsproject.animationstudio.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mamunsproject.animationstudio.Adapter.VIdeoAdapter;
import com.mamunsproject.animationstudio.Model.ResponseVideo;
import com.mamunsproject.animationstudio.Model.Video;
import com.mamunsproject.animationstudio.R;
import com.mamunsproject.animationstudio.Retrofit.ApiClient;
import com.mamunsproject.animationstudio.Retrofit.ApiInterface;
import com.mamunsproject.animationstudio.Utils.MyConsts;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnimationFragment extends Fragment {

    VIdeoAdapter vIdeoAdapter;
    ArrayList<Video> arrayListVideo;
    ApiInterface apiInterface;
    LottieAnimationView animationView;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animation, container, false);

        animationView=view.findViewById(R.id.animationView);
        recyclerView = view.findViewById(R.id.animationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        arrayListVideo = new ArrayList<>();
        vIdeoAdapter = new VIdeoAdapter(getContext(), arrayListVideo);
        recyclerView.setAdapter(vIdeoAdapter);


        DocumentReference documentReference = firebaseFirestore.
                collection("AllCartoonPlayListKey").document("Animation_Movie");


        documentReference
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    String key = documentSnapshot.getString("animation_movie");
                    getVideos(key);
                    Log.d("TAGD", "onSuccess: " + key);


                } else {
                    Toast.makeText(getContext(), "Does'nt Exist", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getContext(), "Failed firestore!", Toast.LENGTH_SHORT).show();
            }
        });


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

}