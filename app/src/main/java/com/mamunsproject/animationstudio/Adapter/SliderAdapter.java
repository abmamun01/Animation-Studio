package com.mamunsproject.animationstudio.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mamunsproject.animationstudio.Model.SliderModel;
import com.mamunsproject.animationstudio.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends
            SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

        private final Context context;
        private List<SliderModel> mSliderModels = new ArrayList<>();

        public SliderAdapter(Context context,List<SliderModel> mSliderModels) {
            this.context = context;
            this.mSliderModels = mSliderModels;

        }



        /*        public void renewItems(List<SliderModel> SliderModels) {
            this.mSliderModels = SliderModels;
            notifyDataSetChanged();
        }

        public void deleteItem(int position) {
            this.mSliderModels.remove(position);
            notifyDataSetChanged();
        }

        public void addItem(SliderModel SliderModel) {
            this.mSliderModels.add(SliderModel);
            notifyDataSetChanged();
        }*/

        @Override
        public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.slider_item, parent,false);
            return new SliderAdapterVH(inflate);
        }

        @Override
        public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

            SliderModel SliderModel = mSliderModels.get(position);

/*            viewHolder.textViewDescription.setText(SliderModel.getDescription());
            viewHolder.textViewDescription.setTextSize(16);
            viewHolder.textViewDescription.setTextColor(Color.WHITE);*/


            Glide.with(viewHolder.itemView)
                    .load(SliderModel.getImageUrl())
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
                }
            });
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

    }


