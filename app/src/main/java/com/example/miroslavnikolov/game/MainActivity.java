package com.example.miroslavnikolov.game;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        CanvasView view = new CanvasView(this);
        view.setBackgroundColor(Color.BLUE);


        setContentView(view);



    }

}