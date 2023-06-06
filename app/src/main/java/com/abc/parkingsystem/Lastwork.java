package com.abc.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Lastwork extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastwork);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button btn_goopen = findViewById(R.id.btn_goopen);
        btn_goopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_mycode = new Intent(Lastwork.this,Mycode.class);
                startActivity(intent_mycode);
            }
        });
    }
}