package cod.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import cod.fragment.VerdadePersonalizado;
import com.example.projeto.R;

public class VerdadePersonalizadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdade_personalizado);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_verdade_personalizado, new VerdadePersonalizado())
                    .commit();
        }
    }
}
