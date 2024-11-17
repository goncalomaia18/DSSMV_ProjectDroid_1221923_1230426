package cod.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import cod.fragment.ConsequenciaFragment;
import com.example.projeto.R;

public class ConsequenciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consequencia);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_consequencia, new ConsequenciaFragment())
                    .commit();
        }
    }
}
