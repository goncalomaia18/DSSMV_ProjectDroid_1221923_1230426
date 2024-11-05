package com.example.projeto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ConsequenciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consequencia);

        if (savedInstanceState == null) {
            // Adiciona o FirstFragment à nova Activity
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_consequencia, new ConsequenciaFragment())
                    .commit();
        }
    }
}
