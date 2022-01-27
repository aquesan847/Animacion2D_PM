package com.aqs.pm.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BouncingBallInside extends View {
    private List<Ball> balls = new ArrayList<>();

    public BouncingBallInside(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public BouncingBallInside(Context context) {
        super(context);
        init();
    }

    private void init(){
        //Add a new ball to the view
        Random rd = new Random();
        int height = 1490;
        int width = 950;
        int[] colors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW};
        System.out.println();
        balls.add(new Ball(rd.nextInt(width) + 50,rd.nextInt(height) + 50,100, colors[rd.nextInt(colors.length)]));
        balls.add(new Ball(rd.nextInt(width) + 50,rd.nextInt(height) + 50,100, colors[rd.nextInt(colors.length)]));
        balls.add(new Ball(rd.nextInt(width) + 50,rd.nextInt(height) + 50,100, colors[rd.nextInt(colors.length)]));
        balls.add(new Ball(rd.nextInt(width) + 50,rd.nextInt(height) + 50,100, colors[rd.nextInt(colors.length)]));
        balls.add(new Ball(rd.nextInt(width) + 50,rd.nextInt(height) + 50,100, colors[rd.nextInt(colors.length)]));
        balls.add(new Ball(rd.nextInt(width) + 50,rd.nextInt(height) + 50,100, colors[rd.nextInt(colors.length)]));
        balls.add(new Ball(rd.nextInt(width) + 50,rd.nextInt(height) + 50,100, colors[rd.nextInt(colors.length)]));

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Draw the balls
        for(Ball ball : balls){
            //Move first
            ball.move(canvas);
            //Draw them
            canvas.drawOval(ball.oval,ball.paint);
            canvas.drawOval(ball.oval,ball.paint2);

            //Wait for the player to count balls
            Handler handler = new Handler();
            final Runnable r = new Runnable() {
                public void run() {
                    BouncingBallInside bouncingBallInside = findViewById(R.id.bouncingBallInside);
                    bouncingBallInside.setVisibility(GONE);
                    ((Activity)getContext()).setContentView(R.layout.activity_win);
                }
            };
            handler.postDelayed(r, 10000);
        }
        invalidate(); // See note
    }

}

