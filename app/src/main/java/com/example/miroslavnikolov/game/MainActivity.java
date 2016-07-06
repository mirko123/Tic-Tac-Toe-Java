package com.example.miroslavnikolov.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) this.findViewById(R.id.connect_button);

        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    EditText editText = (EditText) findViewById(R.id.name_text);
                    String text = editText.getText().toString();

                    if(text.length() > 0) {

                        Toast.makeText(getBaseContext(), "Your name is " + text, Toast.LENGTH_LONG).show();


                        NetworkManager2.Init(text, getBaseContext());
//                        final NetworkManager2 net = NetworkManager2.getInstance();
                        System.out.println("here ____1");


                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        finish();

//                        thread.start();
                    }

                }
            });
        }


//        startActivities(new Intent(new Intent(MainActivity.this, ListActivity.class)));


//        Intent intent = new Intent(this, ListActivity.class);
//        startActivity(intent);


//        CanvasView view = new CanvasView(this);
//        view.setBackgroundColor(Color.BLUE);
//
//
//        setContentView(view);



    }



}
