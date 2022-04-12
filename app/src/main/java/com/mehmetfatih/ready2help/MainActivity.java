package com.mehmetfatih.ready2help;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView appLogo, appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        appLogo = findViewById(R.id.imageAppLogo);
        appName = findViewById(R.id.imageAppName);
        Animation fromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        appLogo.setAnimation(fromTop);
        appName.setAnimation(fromBottom);


        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, EntryActivity.class);
            Pair<View, String> logo = Pair.create(appLogo, ViewCompat.getTransitionName(appLogo));
            Pair<View, String> name = Pair.create(appName, ViewCompat.getTransitionName(appName));
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, logo, name);
            startActivity(intent, optionsCompat.toBundle());
        }, 2000);
    }
}