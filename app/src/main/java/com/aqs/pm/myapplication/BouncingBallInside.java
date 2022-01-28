package com.aqs.pm.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BouncingBallInside extends View {
    private List<Ball> balls = new ArrayList<>();
    private boolean bouncingBool = true;
    private Button btCount;
    private EditText etCount;
    private int cont;

    public BouncingBallInside(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BouncingBallInside(Context context) {
        super(context);
    }

    private void init(){
        Random rd = new Random();
        cont = 0;
        int speed = 5;
        int height = 1400;
        int width = 900;
        if (MainActivity.numberEasy) {
            cont = ThreadLocalRandom.current().nextInt(3, 6 + 1);
            speed = ThreadLocalRandom.current().nextInt(8, 12 + 1);
        }
        if (MainActivity.numberNormal) {
            cont = ThreadLocalRandom.current().nextInt(6, 10 + 1);
            speed = ThreadLocalRandom.current().nextInt(12, 18 + 1);
        }
        if (MainActivity.numberHard) {
            cont = ThreadLocalRandom.current().nextInt(10, 14 + 1);
            speed = ThreadLocalRandom.current().nextInt(18, 24 + 1);
        }
        for (int i = 0; i < cont; i++) {
            //Add a new ball to the view
            int[] colors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW, Color.GRAY, Color.DKGRAY, Color.LTGRAY, Color.BLACK};
            balls.add(new Ball(rd.nextInt(width) + 50,rd.nextInt(height) + 80,100, colors[rd.nextInt(colors.length)], speed));
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (MainActivity.numberEasy || MainActivity.numberNormal || MainActivity.numberHard) {
            init();
            System.out.println(cont);
        }
        MainActivity.numberEasy = false;
        MainActivity.numberNormal = false;
        MainActivity.numberHard = false;
        //Draw the balls
        for(Ball ball : balls){
            //Move first
            ball.move(canvas);
            //Draw them
            canvas.drawOval(ball.oval,ball.paint);
            canvas.drawOval(ball.oval,ball.paint2);

            //Wait for the player to count balls
            Handler handler = new Handler();
            final Runnable r = () -> {
                if (bouncingBool) {
                    BouncingBallInside bouncingBallInside = findViewById(R.id.bouncingBallInside);
                    bouncingBallInside.setVisibility(GONE);
                    winActivity();
                    bouncingBool = false;
                }
            };
            handler.postDelayed(r, 8000);
        }
        invalidate(); // See note
    }

    private void winActivity() {
        ((Activity)getContext()).setContentView(R.layout.activity_win);
        startTitle();
    }

    private void startTitle() {
        Title title2 = ((Activity)getContext()).findViewById(R.id.title2);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(title2, "translationY", 100, 1794);
        animation2.setDuration(2000);
        animation2.start();

        // Mostrar campos cuando termine la animación del titulo2
        animation2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(true);
                // Cuando se apriete el botón se comprobará si el numero de bolas coincide, ademas de controlar que no meta un campo vacío
                btCount.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!etCount.getText().toString().isEmpty()) {
                            TextView tvWin = ((Activity)getContext()).findViewById(R.id.tvWin);
                            TextView tvCountingBalls = ((Activity)getContext()).findViewById(R.id.tvCountingBalls);
                            if (etCount.getText().toString().equals(String.valueOf(cont))) {
                                setVisibility(false);
                                tvWin.setText(R.string.winText);
                                tvWin.setVisibility(VISIBLE);
                                //SONIDO DE VICTORIA
                            } else {
                                setVisibility(false);
                                tvWin.setText(R.string.loseText);
                                tvWin.setVisibility(VISIBLE);
                                //SONIDO DE PERDER
                            }
                            tvCountingBalls.setText("There were " + cont + " balls");
                            tvCountingBalls.setVisibility(VISIBLE);
                        } else {
                            Toast.makeText((Activity)getContext(), R.string.etCountEmpty, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    private void setVisibility(Boolean visibility) {
        btCount = ((Activity)getContext()).findViewById(R.id.btCount);
        etCount = ((Activity)getContext()).findViewById(R.id.etCount);
        TextView tvCount = ((Activity)getContext()).findViewById(R.id.tvCount);
        if (visibility) {
            btCount.setVisibility(View.VISIBLE);
            etCount.setVisibility(View.VISIBLE);
            tvCount.setVisibility(View.VISIBLE);
        } else {
            btCount.setVisibility(View.GONE);
            etCount.setVisibility(View.GONE);
            tvCount.setVisibility(View.GONE);
        }
    }

}

