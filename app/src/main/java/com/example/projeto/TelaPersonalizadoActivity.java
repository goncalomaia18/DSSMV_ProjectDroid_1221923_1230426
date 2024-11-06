package com.example.projeto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TelaPersonalizadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_personalizado);

        if (savedInstanceState == null) {
            // Adiciona o FirstFragment à nova Activity
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_tela_personalizado, new TelaPersonalizado())
                    .commit();
        }
    }
}
