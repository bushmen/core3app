package com.buszko.core3app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

public abstract class BaseActivity extends AppCompatActivity {
    protected FirebaseAnalytics _firebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _firebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }
}
