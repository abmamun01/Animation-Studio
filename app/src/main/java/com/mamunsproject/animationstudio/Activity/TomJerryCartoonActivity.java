package com.mamunsproject.animationstudio.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mamunsproject.animationstudio.Adapter.VIdeoAdapter;
import com.mamunsproject.animationstudio.CategoryPlayer;
import com.mamunsproject.animationstudio.Model.ResponseVideo;
import com.mamunsproject.animationstudio.Model.Video;
import com.mamunsproject.animationstudio.R;
import com.mamunsproject.animationstudio.Retrofit.ApiClient;
import com.mamunsproject.animationstudio.Retrofit.ApiInterface;
import com.mamunsproject.animationstudio.Utils.MyConsts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TomJerryCartoonActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    VIdeoAdapter vIdeoAdapter;
    ArrayList<Video> arrayListVideo;
    ApiInterface apiInterface;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tom_jerry_cartoon);

        RecyclerView recyclerView=findViewById(R.id.tomJerryReclerView);

        animationView=findViewById(R.id.animationView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        arrayListVideo = new ArrayList<>();
        vIdeoAdapter = new VIdeoAdapter(getApplicationContext(), arrayListVideo);
        recyclerView.setAdapter(vIdeoAdapter);



        DocumentReference documentReference=firebaseFirestore.
                collection("CategoryCartoon").document("tomjerryCartoon");


        documentReference
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()){

                    String key=documentSnapshot.getString( "key");
                    getVideos(key);
                    Log.d("TAGD", "onSuccess: "+key);


                }else {
                    Toast.makeText(getApplicationContext(), "Does'nt Exist", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), "Failed firestore!", Toast.LENGTH_SHORT).show();
            }
        });


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
                    Toast.makeText(getApplicationContext(), "There is No Video In Your Channel!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseVideo> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Animatoo.animateDiagonal(TomJerryCartoonActivity.this);
    }
}