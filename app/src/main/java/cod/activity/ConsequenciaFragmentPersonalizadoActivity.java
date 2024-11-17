package cod.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import cod.fragment.ConsequenciaFragmentPersonalizado;
import com.example.projeto.R;

public class ConsequenciaFragmentPersonalizadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consequencia_personalizado);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_consequencia_personalizado, new ConsequenciaFragmentPersonalizado())
                    .commit();
        }
    }

}
