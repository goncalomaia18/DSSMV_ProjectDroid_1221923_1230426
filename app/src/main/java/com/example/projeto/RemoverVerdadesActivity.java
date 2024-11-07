package com.example.projeto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class RemoverVerdadesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_verdades);

        if (savedInstanceState == null) {
            // Adiciona o FirstFragment à nova Activity
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_remover_verdade_personalizado, new RemoverVerdades())
                    .commit();
        }
    }
}

