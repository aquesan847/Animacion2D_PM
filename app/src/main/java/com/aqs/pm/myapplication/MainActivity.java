package com.aqs.pm.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btEasy,btNormal,btHard;
    TextView tvDifficulty;
    Title title;
    BouncingBallInside bouncingBallInside;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                System.out.println("Easy");
                //BouncingBallInside.setNumber(4);
                BouncingBallInside bbs = new BouncingBallInside(getApplicationContext());
                bbs.setNumber(10);
                setVisibility();
                startTitle();
                break;
            case R.id.btNormal:
                System.out.println("Normal");
                setVisibility();
                startTitle();
                break;
            case R.id.btHard:
                System.out.println("Hard");
                setVisibility();
                startTitle();
                break;
        }
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