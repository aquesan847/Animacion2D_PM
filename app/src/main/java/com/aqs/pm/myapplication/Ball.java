package com.aqs.pm.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

public class Ball{
    public int[] direction = new int[]{-1, 1}; //direction modifier (-1,1)
    public Random rd = new Random();
    public int x,y,size;
    public int speed = 15;
    public Paint paint, paint2;
    public RectF oval;

    public Ball(int x, int y, int size, int color, int speed){
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(color);
        this.paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        this.paint2 = new Paint();
        this.paint2.setStyle(Paint.Style.STROKE);
        this.paint2.setStrokeWidth(2);
        this.paint2.setColor(Color.BLACK);
        this.paint2.setFlags(Paint.ANTI_ALIAS_FLAG);

        if (rd.nextInt(100)%2 == 0) {
            direction[0] = 1;
            direction[1] = 1;
        }
        if (rd.nextInt(100)%3 == 0) {
            direction[0] = -1;
            direction[1] = -1;
        }
        if (rd.nextInt(100)%5 == 0) {
            direction[0] = 1;
            direction[1] = -1;
        }
    }

    public void move(Canvas canvas) {
        this.x += speed*direction[0];
        this.y += speed*direction[1];
        this.oval = new RectF(x-size/2,y-size/2,x+size/2,y+size/2);

        Rect bounds = new Rect();
        this.oval.roundOut(bounds); ///store our int bounds

        if(!canvas.getClipBounds().contains(bounds)){
            if(this.x-size<0 || this.x+size > canvas.getWidth()){
                direction[0] = direction[0]*-1;
            }
            if(this.y-size<0 || this.y+size > canvas.getHeight()){
                direction[1] = direction[1]*-1;
            }
        }
    }
}

