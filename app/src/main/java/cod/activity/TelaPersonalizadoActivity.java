package cod.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.projeto.R;
import cod.fragment.TelaPersonalizado;

public class TelaPersonalizadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_personalizado);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_tela_personalizado, new TelaPersonalizado())
                    .commit();
        }
    }
}
