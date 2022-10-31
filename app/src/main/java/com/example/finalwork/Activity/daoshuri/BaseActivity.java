package com.example.finalwork.Activity.daoshuri;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.example.finalwork.R;

public class BaseActivity extends com.example.finalwork.bottomnavigation.base.BaseActivity {

    protected int layoutId;
    protected String title;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
