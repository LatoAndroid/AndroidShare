package com.lyb.besttimer.androidshare.activity.mix;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.widget.ImageView;

import com.lyb.besttimer.androidshare.R;

public class VectorDrawableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable);
        ImageView imageView = findViewById(R.id.iv);
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat = (AnimatedVectorDrawableCompat) imageView.getDrawable();
        animatedVectorDrawableCompat.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        animatedVectorDrawableCompat.start();
                    }
                });
            }
        });
        animatedVectorDrawableCompat.start();
    }
}
