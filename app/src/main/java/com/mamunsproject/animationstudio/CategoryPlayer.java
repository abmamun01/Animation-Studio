package com.mamunsproject.animationstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class CategoryPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_player);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Animatoo.animateDiagonal(CategoryPlayer.this);
    }
}