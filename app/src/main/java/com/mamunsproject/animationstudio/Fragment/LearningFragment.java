package com.mamunsproject.animationstudio.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mamunsproject.animationstudio.Quiz_Activity;
import com.mamunsproject.animationstudio.R;
import com.mamunsproject.animationstudio.databinding.FragmentLearningBinding;

import org.jetbrains.annotations.NotNull;


public class LearningFragment extends Fragment {


    FragmentLearningBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLearningBinding.inflate(getLayoutInflater());



        binding.learningVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Quiz_Activity.class));
            }
        });

        return binding.getRoot();
    }
}