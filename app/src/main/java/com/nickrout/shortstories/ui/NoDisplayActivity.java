package com.nickrout.shortstories.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class NoDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performPreFinishOperations();
        finish();
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
    }

    protected abstract void performPreFinishOperations();
}
