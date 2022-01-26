package com.aqs.pm.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        Ball ball = new Ball(100, 100, 50, 10);
        BouncingBallInside bouncingBallInside = findViewById(R.id.bouncingBallInside2);

        Title title = findViewById(R.id.title);

        ObjectAnimator animation = ObjectAnimator.ofFloat(title, "translationY", 0, height);
        animation.setDuration(5000);
        animation.start();

    }

}