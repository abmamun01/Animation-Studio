package com.mamunsproject.animationstudio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mamunsproject.animationstudio.Model.SliderModel;
import com.mamunsproject.animationstudio.PlayListPlayer;
import com.mamunsproject.animationstudio.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.mamunsproject.animationstudio.Fragment.HomeFragment.firebaseFirestore;

public class SliderAdapter extends
            SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

        private final Context context;
        private List<SliderModel> mSliderModels = new ArrayList<>();

        public SliderAdapter(Context context,List<SliderModel> mSliderModels) {
            this.context = context;
            this.mSliderModels = mSliderModels;

        }



        @Override
        public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.slider_item, parent,false);
            return new SliderAdapterVH(inflate);
        }

        @Override
        public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

            SliderModel SliderModel = mSliderModels.get(position);


            Glide.with(viewHolder.itemView)
                    .load(SliderModel.getImageUrl())
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);


//=================================ONCLICK LISTENER==================================================
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context,  SliderModel.getplaylist(), Toast.LENGTH_SHORT).show();;
                    String plalistId=SliderModel.getplaylist();

                    Intent intent = new Intent(context, PlayListPlayer.class);
                    intent.putExtra("id", plalistId);
                    context.startActivity(intent);
                }
            });

//=================================ONCLICK LISTENER==================================================

        }

        @Override
        public int getCount() {
            //slider view count could be dynamic size
            return mSliderModels.size();
        }

        public static class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

            ImageView imageViewBackground;

            public SliderAdapterVH(View itemView) {
                super(itemView);
                imageViewBackground = itemView.findViewById(R.id.slider_Image_layout);

            }
        }

        private String getKey(String key){

            return key;
        }

        private void fetchKey(){

            DocumentReference documentReference=firebaseFirestore.
                    collection("AllCartoonPlayListKey").document("AllCartoonID");


            documentReference
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.exists()){

                        String key=documentSnapshot.getString( "AllCartoonID");
                        getKey(key);

                    }else {
                        Toast.makeText(context, "Does'nt Exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(context, "Failed firestore!", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

