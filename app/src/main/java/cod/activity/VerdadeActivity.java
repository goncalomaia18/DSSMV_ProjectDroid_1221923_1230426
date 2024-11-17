package cod.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import cod.fragment.Verdade;
import com.example.projeto.R;

public class VerdadeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdade);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_verdade, new Verdade())
                    .commit();
        }
    }
}