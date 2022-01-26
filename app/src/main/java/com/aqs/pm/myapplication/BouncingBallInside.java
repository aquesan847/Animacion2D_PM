package com.aqs.pm.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

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
        balls.add(new Ball(50,50,100, Color.RED));
        balls.add(new Ball(500,100,100,Color.BLACK));
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Draw the balls
        for(Ball ball : balls){
            //Move first
            ball.move(canvas);
            //Draw them
            canvas.drawOval(ball.oval,ball.paint);
        }
        invalidate(); // See note
    }
}

