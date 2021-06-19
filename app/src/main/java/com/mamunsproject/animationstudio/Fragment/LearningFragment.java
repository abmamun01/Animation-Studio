package com.mamunsproject.animationstudio.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import com.mamunsproject.animationstudio.LearningVideoActivity;
import com.mamunsproject.animationstudio.Quiz_Activity;
import com.mamunsproject.animationstudio.R;
import com.smb.glowbutton.GlowButton;

import org.jetbrains.annotations.NotNull;


public class LearningFragment extends Fragment {

    GlowButton videoButton,quizButton;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_learning, container, false);

        videoButton=view.findViewById(R.id.learningVideoID);
        quizButton=view.findViewById(R.id.learningQuizID);



        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LearningVideoActivity.class));
            }
        });

        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Quiz_Activity.class));

            }
        });


        return view;
    }


}