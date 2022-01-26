package com.aqs.pm.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;


public class Circle extends View {

    private static final int RADIO = 30 ;

    private int centroX;
    private int centroY;
    private int velocidadX = 55 ;
    private int velocidadY = 55 ;

    private final Paint paint;
    private final Paint paint2;

    public Circle(Context context) {
        super(context);
        paint = new Paint();
        paint2 = new Paint();
        //Circle color
        paint.setColor(Color.BLACK);
        paint2.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        centroX = w / 2;
        centroY = h / 2;
    }

    Random rd = new Random();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();

        centroX += velocidadX ;
        centroY += velocidadY ;

        //Límites pantalla
        int limiteDerecha = w - RADIO;
        int limiteInferior = h - RADIO;

        //Comprobar si invertir
        if (centroX >= limiteDerecha) {
            centroX = limiteDerecha ;
            velocidadX *= -1 ;
        }
        if (centroX <= RADIO) {
            centroX = RADIO ;
            velocidadX *= -1 ;
        }
        if (centroY >= limiteInferior) {
            centroY = limiteInferior ;
            velocidadY *= -1 ;
        }
        if (centroY <= RADIO) {
            centroY = RADIO ;
            velocidadY *= -1 ;
        }


        canvas.drawCircle(centroX, centroY , RADIO, paint);
        postInvalidateDelayed(100);
        addCircle(canvas);
    }

    public void addCircle(Canvas canvas) {
        canvas.drawCircle(centroX, centroY + 100, RADIO, paint2);
    }


}
