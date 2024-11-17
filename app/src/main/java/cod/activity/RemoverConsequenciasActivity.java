package cod.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.projeto.R;
import cod.fragment.RemoverConsequencias;

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