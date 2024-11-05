
package com.example.projeto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Certifique-se de que este layout é o correto

        Button buttonVerdadeOuConsequencia = findViewById(R.id.verdadeconsequencia);
        buttonVerdadeOuConsequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, VerdadeActivity.class);
                startActivity(intent);
            }
        });
    }
}

