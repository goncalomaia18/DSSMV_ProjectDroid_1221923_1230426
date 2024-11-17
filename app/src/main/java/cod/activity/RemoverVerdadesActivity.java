package cod.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.projeto.R;
import cod.fragment.RemoverVerdades;

public class RemoverVerdadesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_verdades);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_remover_verdade_personalizado, new RemoverVerdades())
                    .commit();
        }
    }
}

