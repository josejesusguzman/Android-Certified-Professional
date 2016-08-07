package com.example.josejesus.acpanimacionesgraficos;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button btnFrame;
    private Button btnTween;

    private ImageView imageFrameAnimation;
    private Animation animacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFrame = (Button) findViewById(R.id.main_btn_frameAnimation);
        btnTween = (Button) findViewById(R.id.main_btn_tweenAnimation);
        imageFrameAnimation = (ImageView) findViewById(R.id.main_img_frameAnimation);

        btnFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFrameAnimation.setBackgroundResource(R.drawable.frame_animation);
                AnimationDrawable animationDrawable = (AnimationDrawable) imageFrameAnimation.getBackground();
                animationDrawable.start();
                btnFrame.setBackgroundResource(R.drawable.shape_button);
                btnTween.setBackgroundResource(android.R.drawable.btn_default);
            }
        });

        btnTween.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animacion = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tween_animation);
                imageFrameAnimation.startAnimation(animacion);
                btnFrame.setBackgroundResource(android.R.drawable.btn_default);
                btnTween.setBackgroundResource(R.drawable.shape_button);
            }
        });

    }
}
