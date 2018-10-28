package com.hencoder.a07_change_camera_animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.hencoder.a07_change_camera_animation.ljuns.CameraView;

public class MainActivity extends AppCompatActivity {

    private CameraView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCameraView = findViewById(R.id.view);

        ObjectAnimator bottomAnimator = ObjectAnimator.ofFloat(mCameraView, "deg", 45f);
        bottomAnimator.setDuration(1500);

        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(mCameraView, "rotateWithZ", 270f);
        rotateAnimator.setDuration(1500);

        ObjectAnimator topAnimator = ObjectAnimator.ofFloat(mCameraView, "degTop", -45f);
        topAnimator.setDuration(1500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(bottomAnimator, rotateAnimator, topAnimator);
        animatorSet.setStartDelay(1000);
        animatorSet.start();
    }
}
