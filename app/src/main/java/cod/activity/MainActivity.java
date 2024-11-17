
package cod.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projeto.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonVerdadeOuConsequencia = findViewById(R.id.verdadeconsequencia);
        buttonVerdadeOuConsequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, VerdadeActivity.class);
                startActivity(intent);
            }
        });

        Button buttonVerdadeOuConsequenciaPersonalizado = findViewById(R.id.verdadeconsequenciapersonalizado);
        buttonVerdadeOuConsequenciaPersonalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, TelaPersonalizadoActivity.class);
                startActivity(intent);
            }
        });


    }
}

