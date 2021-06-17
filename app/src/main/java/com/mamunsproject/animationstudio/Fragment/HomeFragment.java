package com.mamunsproject.animationstudio.Fragment;

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

import com.google.android.gms.tasks.OnCompleteListener;
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
    ProgressBar progressBar;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = view.findViewById(R.id.recyclerMain);
        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        arrayListVideo = new ArrayList<>();
        vIdeoAdapter = new VIdeoAdapter(getContext(), arrayListVideo);
        recyclerView.setAdapter(vIdeoAdapter);


        DocumentReference documentReference = firebaseFirestore
                .collection("AllCartoonPlayListKey").document("AllCartoonID");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    String key = documentSnapshot.getString("key");
                    //getVideos(key);
                }
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

        getVideos();


        return view;

    }

    private void getVideos() {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseVideo> callVideo = apiInterface.getAllVideos(300, MyConsts.CHOTA_BHEEM
                , MyConsts.APIKEY);

        callVideo.enqueue(new Callback<ResponseVideo>() {
            @Override
            public void onResponse(Call<ResponseVideo> call, Response<ResponseVideo> response) {

                ResponseVideo responseVideo = response.body();
                if (responseVideo != null) {
                    // progressBar.setVisibility(View.GONE);
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