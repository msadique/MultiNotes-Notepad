package com.sadique.multinotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NotePadAbout extends AppCompatActivity {

    private static final String TAG = "NotePadAbout";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }

    public void doneClicked(View v) {
       finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
    }
}
