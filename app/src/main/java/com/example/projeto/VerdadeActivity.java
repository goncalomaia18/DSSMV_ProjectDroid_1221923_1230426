package com.example.projeto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class VerdadeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdade);

        if (savedInstanceState == null) {
            // Adiciona o FirstFragment à nova Activity
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_verdade, new Verdade())
                    .commit();
        }
    }
}

