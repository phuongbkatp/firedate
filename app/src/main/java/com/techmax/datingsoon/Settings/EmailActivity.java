package com.techmax.datingsoon.Settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.techmax.datingsoon.R;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_activity);



    }

    @Override
    protected void onStart() {
        super.onStart();

        Toast.makeText(this, "email", Toast.LENGTH_SHORT).show();


    }
}
