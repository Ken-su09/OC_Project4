package com.suonk.oc_project4.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;

import com.suonk.oc_project4.databinding.ActivityMainBinding;

enum WindowSizeClass {COMPACT, MEDIUM, EXPANDED}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        ViewGroup container = binding.container;
//        container.addView(new View(this) {
//            @Override
//            protected void onConfigurationChanged(Configuration newConfig) {
//                super.onConfigurationChanged(newConfig);
//                computeWindowSizeClasses();
//            }
//        });

        computeWindowSizeClasses();
    }

    private void computeWindowSizeClasses() {
        WindowMetrics metrics = WindowMetricsCalculator.getOrCreate()
                .computeCurrentWindowMetrics(this);

        float widthDp = metrics.getBounds().width() /
                getResources().getDisplayMetrics().density;
        WindowSizeClass widthWindowSizeClass;

        if (widthDp < 600f) {
            widthWindowSizeClass = WindowSizeClass.COMPACT;
        } else if (widthDp < 840f) {
            widthWindowSizeClass = WindowSizeClass.MEDIUM;
        } else {
            widthWindowSizeClass = WindowSizeClass.EXPANDED;
        }

        float heightDp = metrics.getBounds().height() /
                getResources().getDisplayMetrics().density;
        WindowSizeClass heightWindowSizeClass;

        if (heightDp < 480f) {
            heightWindowSizeClass = WindowSizeClass.COMPACT;
        } else if (heightDp < 900f) {
            heightWindowSizeClass = WindowSizeClass.MEDIUM;
        } else {
            heightWindowSizeClass = WindowSizeClass.EXPANDED;
        }
    }
}