package cod.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import cod.fragment.RemoverConsequencias;
import com.example.projeto.R;

public class RemoverConsequenciasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_consequencia);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_remover_consequencias_personalizado, new RemoverConsequencias())
                    .commit();
        }
    }
}