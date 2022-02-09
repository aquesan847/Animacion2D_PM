package com.aqs.pm.myapplication;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pub.devrel.easypermissions.EasyPermissions;

public class BouncingBallInside extends View {
    private List<Ball> balls = new ArrayList<>();
    private boolean bouncingBool = true;
    private boolean imgBool = false;
    private boolean diffEasy, diffNormal, diffHard;
    private Button btCount, btCheckHard, btRestartHard, btLoad;
    private EditText etCount;
    private TextView tvBlue, tvCyan, tvGreen, tvMagenta, tvRed, tvYellow, tvGray, tvBlack;
    private EditText etBlue, etCyan, etGreen, etMagenta, etRed, etYellow, etGray, etBlack;
    private int cont, contBlue, contCyan, contGreen, contMagenta, contRed, contYellow, contGray, contBlack;
    private MediaPlayer mep2;
    private ImageView imgWin1, imgWin2, imgCamera, imgSelected;
    public static int CAMERA_REQUEST_CODE = 100;
    public BouncingBallInside(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BouncingBallInside(Context context) {
        super(context);
    }

    private void init(){
        Random rd = new Random();
        cont = 0;
        contBlue = 0;
        int speed = 5;
        int height = 1400;
        int width = 900;
        if (MainActivity.numberEasy) {
            cont = ThreadLocalRandom.current().nextInt(3, 6 + 1);
            speed = ThreadLocalRandom.current().nextInt(8, 12 + 1);
            diffEasy = true;
        }
        if (MainActivity.numberNormal) {
            cont = ThreadLocalRandom.current().nextInt(6, 10 + 1);
            speed = ThreadLocalRandom.current().nextInt(12, 18 + 1);
            diffNormal = true;
        }
        if (MainActivity.numberHard) {
            cont = ThreadLocalRandom.current().nextInt(10, 14 + 1);
            speed = ThreadLocalRandom.current().nextInt(18, 24 + 1);
            diffHard = true;
        }
        for (int i = 0; i < cont; i++) {
            //Add a new ball to the view
            int[] colors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW, Color.GRAY, Color.BLACK};
            int color = colors[rd.nextInt(colors.length)];
            balls.add(new Ball(rd.nextInt(width) + 50,rd.nextInt(height) + 80,100, color, speed));
            if (color == Color.BLUE){
                contBlue++;
            }
            if (color == Color.CYAN){
                contCyan++;
            }
            if (color == Color.GREEN){
                contGreen++;
            }
            if (color == Color.MAGENTA){
                contMagenta++;
            }
            if (color == Color.RED){
                contRed++;
            }
            if (color == Color.YELLOW){
                contYellow++;
            }
            if (color == Color.GRAY){
                contGray++;
            }
            if (color == Color.BLACK){
                contBlack++;
            }
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (MainActivity.numberEasy || MainActivity.numberNormal || MainActivity.numberHard) {
            init();
            //System.out.println(cont);
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
                    MainActivity.mep.stop();
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
        // Añadimos musica con MediaPlayer
        mep2 = MediaPlayer.create((Activity)getContext(), R.raw.mistery);
        mep2.setLooping(true);
        mep2.start();
        Title title2 = ((Activity)getContext()).findViewById(R.id.title2);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(title2, "translationY", 100, 1794);
        animation2.setDuration(2000);
        animation2.start();

        // Mostrar campos cuando termine la animación del titulo2
        animation2.addListener(new AnimatorListenerAdapter() {
            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imgWin1 = ((Activity) getContext()).findViewById(R.id.imgWin1);
                imgWin2 = ((Activity) getContext()).findViewById(R.id.imgWin2);
                imgCamera = ((Activity) getContext()).findViewById(R.id.imgCamera);
                imgSelected = ((Activity) getContext()).findViewById(R.id.imgSelected);
                if (diffEasy) {
                    setVisibilityEasy(true);
                    // Cuando se apriete el botón se comprobará si el numero de bolas coincide, ademas de controlar que no meta un campo vacío
                    btCount.setOnClickListener(view -> {
                        if (!etCount.getText().toString().isEmpty()) {
                            mep2.stop();
                            TextView tvWin = ((Activity) getContext()).findViewById(R.id.tvWin);
                            TextView tvCountingBalls = ((Activity) getContext()).findViewById(R.id.tvCountingBalls);
                            Button btRestart = ((Activity) getContext()).findViewById(R.id.btRestart);
                            if (etCount.getText().toString().equals(String.valueOf(cont))) {
                                setVisibilityEasy(false);
                                tvWin.setText(R.string.winText);
                                tvWin.setVisibility(VISIBLE);
                                mep2 = MediaPlayer.create((Activity) getContext(), R.raw.win);
                                mep2.start();
                            } else {
                                setVisibilityEasy(false);
                                imgWin1.setImageDrawable(getResources().getDrawable(R.drawable.lose));
                                imgWin2.setImageDrawable(getResources().getDrawable(R.drawable.lose));
                                tvWin.setText(R.string.loseText);
                                tvWin.setVisibility(VISIBLE);
                                mep2 = MediaPlayer.create((Activity) getContext(), R.raw.lose);
                                mep2.start();
                            }
                            // Una vez comprobado si ha acertado o no le mostramos el número de bolas y el botón para reiniciar el juego
                            tvCountingBalls.setText("There were " + cont + " balls");
                            tvCountingBalls.setVisibility(VISIBLE);
                            btRestart.setVisibility(VISIBLE);
                            imgWin1.setVisibility(View.VISIBLE);
                            imgWin2.setVisibility(View.VISIBLE);
                            btRestart.setOnClickListener(view1 -> {
                                mep2.stop();
                                ((Activity) getContext()).recreate();
                            });
                            imgBool = false;
                            imgCamera.setVisibility(View.VISIBLE);
                            imgCamera.setOnClickListener(view13 -> {
                                EasyPermissions.requestPermissions(MainActivity.mainActivity,"Request permission for the camera use.", CAMERA_REQUEST_CODE, Manifest.permission.CAMERA);
                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                                        == PackageManager.PERMISSION_GRANTED) {
                                    // Abrimos la camara para realizar la foto
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    MainActivity.mainActivity.startActivity(intent);
                                    imgBool = true;
                                }
                                btLoad.setVisibility(View.VISIBLE);
                                if (imgBool) {
                                    btLoad.setOnClickListener(view14 -> {
                                        imgSelected.setVisibility(View.VISIBLE);
                                        Glide.with((Activity) getContext())
                                                .load(((Activity) getContext()).getResources().getDrawable(R.drawable.img_selected))
                                                .placeholder(R.drawable.img_selected)
                                                .into(imgSelected);
                                        btLoad.setVisibility(View.GONE);
                                    });
                                } else {
                                    Toast.makeText((Activity) getContext(), "Upload an image first!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText((Activity) getContext(), R.string.etCountEmpty, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    setVisibilityHard(true);
                    checkColorsCont();
                    prepareEmptyFields();
                    btCheckHard.setOnClickListener(view -> {
                        if (checkEmptyFields()) {
                            mep2.stop();
                            TextView tvWin = ((Activity) getContext()).findViewById(R.id.tvWin);
                            TextView tvCountingBalls = ((Activity) getContext()).findViewById(R.id.tvCountingBalls);
                            Button btRestartHard = ((Activity) getContext()).findViewById(R.id.btRestartHard);
                            btLoad = ((Activity) getContext()).findViewById(R.id.btLoad);
                            if (checkFieldsCont()) {
                                setVisibilityHard(false);
                                tvWin.setText(R.string.winText);
                                tvWin.setVisibility(VISIBLE);
                                mep2 = MediaPlayer.create((Activity) getContext(), R.raw.win);
                                mep2.start();
                            } else {
                                setVisibilityHard(false);
                                imgWin1.setImageDrawable(getResources().getDrawable(R.drawable.lose));
                                imgWin2.setImageDrawable(getResources().getDrawable(R.drawable.lose));
                                tvWin.setText(R.string.loseText);
                                tvWin.setVisibility(VISIBLE);
                                mep2 = MediaPlayer.create((Activity) getContext(), R.raw.lose);
                                mep2.start();
                            }
                            // Una vez comprobado si ha acertado o no le mostramos el número de bolas de cada color y el botón para reiniciar el juego
                            tvCountingBalls.setText("There were " + contBlue + " blue balls\n\t\t\t\t" +
                                                contCyan + " cyan balls\n\t\t\t\t" +
                                                contGreen + " green balls\n\t\t\t\t" +
                                                contMagenta + " magenta balls\n\t\t\t\t" +
                                                contRed + " red balls\n\t\t\t\t" +
                                                contYellow + " yellow balls\n\t\t\t\t" +
                                                contGray + " gray balls\n\t\t\t\t" +
                                                contBlack + " black balls");
                            tvCountingBalls.setVisibility(VISIBLE);
                            btRestartHard.setVisibility(VISIBLE);
                            imgWin1.setVisibility(View.VISIBLE);
                            imgWin2.setVisibility(View.VISIBLE);
                            btRestartHard.setOnClickListener(view12 -> {
                                mep2.stop();
                                ((Activity) getContext()).recreate();
                            });
                            imgBool = false;
                            imgCamera.setVisibility(View.VISIBLE);
                            imgCamera.setOnClickListener(view13 -> {
                                EasyPermissions.requestPermissions(MainActivity.mainActivity,"Request permission for the camera use.", CAMERA_REQUEST_CODE, Manifest.permission.CAMERA);
                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                                        == PackageManager.PERMISSION_GRANTED) {
                                    // Abrimos la camara para realizar la foto
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    MainActivity.mainActivity.startActivity(intent);
                                    imgBool = true;
                                }
                                btLoad.setVisibility(View.VISIBLE);
                                if (imgBool) {
                                    btLoad.setOnClickListener(view14 -> {
                                        imgSelected.setVisibility(View.VISIBLE);
                                        Glide.with((Activity) getContext())
                                                .load(((Activity) getContext()).getResources().getDrawable(R.drawable.img_selected))
                                                .placeholder(R.drawable.img_selected)
                                                .into(imgSelected);
                                        btLoad.setVisibility(View.GONE);
                                    });
                                } else {
                                    Toast.makeText((Activity) getContext(), "Upload an image first!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText((Activity) getContext(), R.string.etCountEmpty, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void prepareEmptyFields() {
        if (!etBlue.isEnabled()) {
            etBlue.setText("0");
        }
        if (!etCyan.isEnabled()) {
            etCyan.setText("0");
        }
        if (!etGreen.isEnabled()) {
            etGreen.setText("0");
        }
        if (!etMagenta.isEnabled()) {
            etMagenta.setText("0");
        }
        if (!etRed.isEnabled()) {
            etRed.setText("0");
        }
        if (!etYellow.isEnabled()) {
            etYellow.setText("0");
        }
        if (!etGray.isEnabled()) {
            etGray.setText("0");
        }
        if (!etBlack.isEnabled()) {
            etBlack.setText("0");
        }
    }

    private boolean checkFieldsCont() {
        return etBlue.getText().toString().equals(String.valueOf(contBlue)) &&
                etCyan.getText().toString().equals(String.valueOf(contCyan)) &&
                etGreen.getText().toString().equals(String.valueOf(contGreen)) &&
                etMagenta.getText().toString().equals(String.valueOf(contMagenta)) &&
                etRed.getText().toString().equals(String.valueOf(contRed)) &&
                etYellow.getText().toString().equals(String.valueOf(contYellow)) &&
                etGray.getText().toString().equals(String.valueOf(contGray)) &&
                etBlack.getText().toString().equals(String.valueOf(contBlack));
    }

    private boolean checkEmptyFields() {
        boolean check = false;
        if (!etBlue.getText().toString().isEmpty() && etBlue.isEnabled()) {
            check = true;
        }
        if (!etCyan.getText().toString().isEmpty() && etCyan.isEnabled()) {
            check =  true;
        }
        if (!etGreen.getText().toString().isEmpty() && etGreen.isEnabled()) {
            check =  true;
        }
        if (!etMagenta.getText().toString().isEmpty() && etMagenta.isEnabled()) {
            check =  true;
        }
        if (!etRed.getText().toString().isEmpty() && etRed.isEnabled()) {
            check =  true;
        }
        if (!etYellow.getText().toString().isEmpty() && etYellow.isEnabled()) {
            check =  true;
        }
        if (!etGray.getText().toString().isEmpty() && etGray.isEnabled()) {
            check =  true;
        }
        if (!etBlack.getText().toString().isEmpty() && etBlack.isEnabled()) {
            check =  true;
        }
        return check;
    }

    private void checkColorsCont() {
        if (contBlue == 0) {
            etBlue.setEnabled(false);
        }
        if (contCyan == 0) {
            etCyan.setEnabled(false);
        }
        if (contGreen == 0) {
            etGreen.setEnabled(false);
        }
        if (contMagenta == 0) {
            etMagenta.setEnabled(false);
        }
        if (contRed == 0) {
            etRed.setEnabled(false);
        }
        if (contYellow == 0) {
            etYellow.setEnabled(false);
        }
        if (contGray == 0) {
            etGray.setEnabled(false);
        }
        if (contBlack == 0) {
            etBlack.setEnabled(false);
        }
    }


    private void setVisibilityEasy(Boolean visibility) {
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

    private void setVisibilityHard(Boolean visibility) {
        tvBlue = ((Activity)getContext()).findViewById(R.id.tvBlue);
        tvCyan = ((Activity)getContext()).findViewById(R.id.tvCyan);
        tvGreen = ((Activity)getContext()).findViewById(R.id.tvGreen);
        tvMagenta = ((Activity)getContext()).findViewById(R.id.tvMagenta);
        tvRed = ((Activity)getContext()).findViewById(R.id.tvRed);
        tvYellow = ((Activity)getContext()).findViewById(R.id.tvYellow);
        tvGray = ((Activity)getContext()).findViewById(R.id.tvGray);
        tvBlack = ((Activity)getContext()).findViewById(R.id.tvBlack);
        etBlue = ((Activity)getContext()).findViewById(R.id.etBlue);
        etCyan = ((Activity)getContext()).findViewById(R.id.etCyan);
        etGreen = ((Activity)getContext()).findViewById(R.id.etGreen);
        etMagenta = ((Activity)getContext()).findViewById(R.id.etMagenta);
        etRed = ((Activity)getContext()).findViewById(R.id.etRed);
        etYellow = ((Activity)getContext()).findViewById(R.id.etYellow);
        etGray = ((Activity)getContext()).findViewById(R.id.etGray);
        etBlack = ((Activity)getContext()).findViewById(R.id.etBlack);
        btCheckHard = ((Activity)getContext()).findViewById(R.id.btCheckHard);
        TextView tvCountH = ((Activity)getContext()).findViewById(R.id.tvCountHard);

        if (visibility) {
            btCheckHard.setVisibility(View.VISIBLE);
            tvBlue.setVisibility(View.VISIBLE);
            tvCyan.setVisibility(View.VISIBLE);
            tvGreen.setVisibility(View.VISIBLE);
            tvMagenta.setVisibility(View.VISIBLE);
            tvRed.setVisibility(View.VISIBLE);
            tvYellow.setVisibility(View.VISIBLE);
            tvGray.setVisibility(View.VISIBLE);
            tvBlack.setVisibility(View.VISIBLE);
            etBlue.setVisibility(View.VISIBLE);
            etCyan.setVisibility(View.VISIBLE);
            etGreen.setVisibility(View.VISIBLE);
            etMagenta.setVisibility(View.VISIBLE);
            etRed.setVisibility(View.VISIBLE);
            etYellow.setVisibility(View.VISIBLE);
            etGray.setVisibility(View.VISIBLE);
            etBlack.setVisibility(View.VISIBLE);
            tvCountH.setVisibility(View.VISIBLE);
        } else {
            btCheckHard.setVisibility(View.GONE);
            tvBlue.setVisibility(View.GONE);
            tvCyan.setVisibility(View.GONE);
            tvGreen.setVisibility(View.GONE);
            tvMagenta.setVisibility(View.GONE);
            tvRed.setVisibility(View.GONE);
            tvYellow.setVisibility(View.GONE);
            tvGray.setVisibility(View.GONE);
            tvBlack.setVisibility(View.GONE);
            etBlue.setVisibility(View.GONE);
            etCyan.setVisibility(View.GONE);
            etGreen.setVisibility(View.GONE);
            etMagenta.setVisibility(View.GONE);
            etRed.setVisibility(View.GONE);
            etYellow.setVisibility(View.GONE);
            etGray.setVisibility(View.GONE);
            etBlack.setVisibility(View.GONE);
            tvCountH.setVisibility(View.GONE);
        }
    }

}

