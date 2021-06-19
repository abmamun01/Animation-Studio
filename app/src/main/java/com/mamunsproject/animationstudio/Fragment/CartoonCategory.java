package com.mamunsproject.animationstudio.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mamunsproject.animationstudio.Activity.ArabicCartoonActivity;
import com.mamunsproject.animationstudio.Activity.Ben10CartoonActivity;
import com.mamunsproject.animationstudio.Activity.ChottaBheemCartoonActivity;
import com.mamunsproject.animationstudio.Activity.HindiCartoonActivity;
import com.mamunsproject.animationstudio.Activity.OggyCartoonActivity;
import com.mamunsproject.animationstudio.Activity.TomJerryCartoonActivity;
import com.mamunsproject.animationstudio.CategoryPlayer;
import com.mamunsproject.animationstudio.Custom.IOnBackPressed;
import com.mamunsproject.animationstudio.MainActivity;
import com.mamunsproject.animationstudio.R;

import java.util.Objects;


public class CartoonCategory extends Fragment  {


    ConstraintLayout hindiCartoon, arabicCartoon, tomjerryCartoon, chottaBheemCartoon, oggyCartoon, benCartoon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cartoon_category, container, false);


        hindiCartoon = view.findViewById(R.id.hindiCartoonID);
        arabicCartoon = view.findViewById(R.id.arabicCartoonID);
        tomjerryCartoon = view.findViewById(R.id.tom_and_jerryCartoonID);
        chottaBheemCartoon = view.findViewById(R.id.chottaBheemCartoonID);
        oggyCartoon = view.findViewById(R.id.oggyandCoakroachCartoonID);
        benCartoon = view.findViewById(R.id.bentenCartoonID);


        hindiCartoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), HindiCartoonActivity.class));
                Animatoo.animateDiagonal(requireContext());

            }
        });


        arabicCartoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ArabicCartoonActivity.class));
                Animatoo.animateDiagonal(requireContext());

            }
        });


        tomjerryCartoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TomJerryCartoonActivity.class));
                Animatoo.animateDiagonal(requireContext());

            }
        });


        chottaBheemCartoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChottaBheemCartoonActivity.class));
                Animatoo.animateDiagonal(requireContext());

            }
        });


        oggyCartoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OggyCartoonActivity.class));
                Animatoo.animateDiagonal(requireContext());

            }
        });

        benCartoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Ben10CartoonActivity.class));
                Animatoo.animateDiagonal(requireContext());

            }
        });


        return view;

    }




}