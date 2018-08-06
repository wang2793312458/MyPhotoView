package com.example.myphotoview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String API_LOAD_IMAGE = "http://www.taoshunda.com:80/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        List<String> images = new ArrayList<>();
        images.add(API_LOAD_IMAGE + "20180721/15321625577659724/1532162639966.jpg");
        images.add(API_LOAD_IMAGE + "20180721/153215616916660623/1532156226793.jpg");
        images.add(API_LOAD_IMAGE + "20180624/152984758502628774/1529847600679.jpg");

    }
}
