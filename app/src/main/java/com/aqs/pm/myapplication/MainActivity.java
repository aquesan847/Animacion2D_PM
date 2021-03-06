package com.aqs.pm.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btEasy,btNormal,btHard;
    TextView tvDifficulty;
    Title title;
    BouncingBallInside bouncingBallInside;
    static boolean numberEasy, numberNormal, numberHard;
    public static MediaPlayer mep;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        // Añadimos musica con MediaPlayer
        mep = MediaPlayer.create(this, R.raw.softcircle);
        mep.setLooping(true);

        btEasy = findViewById(R.id.btEasy);
        btEasy.setOnClickListener(this);

        btNormal = findViewById(R.id.btNormal);
        btNormal.setOnClickListener(this);

        btHard = findViewById(R.id.btHard);
        btHard.setOnClickListener(this);

        tvDifficulty = findViewById(R.id.tvSelectDifficulty);
        bouncingBallInside = findViewById(R.id.bouncingBallInside);

        title = findViewById(R.id.title);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btEasy:
                numberEasy = true;
                setVisibility();
                startTitle();
                break;
            case R.id.btNormal:
                numberNormal = true;
                setVisibility();
                startTitle();
                break;
            case R.id.btHard:
                numberHard = true;
                setVisibility();
                startTitle();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mep.start();
    }

    private void setVisibility() {
        btEasy.setVisibility(View.GONE);
        btNormal.setVisibility(View.GONE);
        btHard.setVisibility(View.GONE);
        tvDifficulty.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
    }

    private void startTitle() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        ObjectAnimator animation = ObjectAnimator.ofFloat(title, "translationY", 100, height);
        animation.setDuration(4000);
        animation.start();

        // Mostrar bolas cuando termine la animación del titulo
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bouncingBallInside.setVisibility(View.VISIBLE);
            }
        });

    }

}