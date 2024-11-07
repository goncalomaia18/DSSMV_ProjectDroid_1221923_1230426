package com.example.projeto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class VerdadePersonalizadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdade_personalizado);

        if (savedInstanceState == null) {
            // Adiciona o Fragment VerdadePersonalizado à nova Activity
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_verdade_personalizado, new VerdadePersonalizado())
                    .commit();
        }
    }
}
