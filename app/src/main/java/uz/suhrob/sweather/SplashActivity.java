package uz.suhrob.sweather;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {

    ImageView suhrobLogoView;
    LinearLayout splashIconLayout;
    Animation logoUpAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashIconLayout = findViewById(R.id.splash_icon_layout);
        suhrobLogoView = findViewById(R.id.suhrob_logo_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 4000);
        logoUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_up);
        suhrobLogoView.setVisibility(View.INVISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                suhrobLogoView.setVisibility(View.VISIBLE);
                AnimatedVectorDrawable anim = (AnimatedVectorDrawable) suhrobLogoView.getDrawable();
                anim.start();
                splashIconLayout.setVisibility(View.VISIBLE);
                splashIconLayout.startAnimation(logoUpAnimation);
            }
        }, 500);

    }
}
