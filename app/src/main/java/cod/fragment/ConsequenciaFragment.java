package cod.fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;

import cod.api.ApiClient;
import cod.api.ApiService;
import cod.model.ClassConsequencia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cod.activity.VerdadeActivity;
import com.example.projeto.databinding.ConsequenciaBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.Random;

public class ConsequenciaFragment extends Fragment implements SensorEventListener { // Implemente SensorEventListener

    private ConsequenciaBinding binding;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float acelVal;  // valor atual da aceleração
    private float acelLast; // última aceleração registrada
    private float shake;    // aceleração com base na diferença das leituras

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = ConsequenciaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configura o SensorManager para detecção de shake
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL); // Agora você pode passar 'this'
        }

        // Inicializar valores de aceleração
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        fetchConsequencias();

        binding.buttonRespondeuConsequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), VerdadeActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        binding.buttonnovaconsequencia.setOnClickListener(v -> fetchConsequencias());
    }

    private void fetchConsequencias() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ClassConsequencia>> call = apiService.getConsequencia();

        call.enqueue(new Callback<List<ClassConsequencia>>() {
            @Override
            public void onResponse(Call<List<ClassConsequencia>> call, Response<List<ClassConsequencia>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ClassConsequencia> consequencias = response.body();
                    if (!consequencias.isEmpty()) {
                        int indexAleatorio = new Random().nextInt(consequencias.size());
                        String consequenciaAleatoria = consequencias.get(indexAleatorio).getConsequencia();
                        binding.textViewConsequencias.setText(consequenciaAleatoria);
                    } else {
                        binding.textViewConsequencias.setText("Nenhuma consequência disponível.");
                    }
                } else {
                    binding.textViewConsequencias.setText("Erro ao obter consequências: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ClassConsequencia>> call, Throwable t) {
                binding.textViewConsequencias.setText("Falha na requisição: " + t.getMessage());
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Detecção de shake
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        acelLast = acelVal;
        acelVal = (float) Math.sqrt((x * x) + (y * y) + (z * z));
        float delta = acelVal - acelLast;
        shake = shake * 0.9f + delta; // Usar fator de suavização para shake

        if (shake > 12) { // Limite ajustável para detectar shake
            // Ação a ser executada quando o shake é detectado
            fetchConsequencias(); // Atualiza a pergunta ao detectar o shake
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Não é necessário implementar para este caso
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (sensorManager != null) {
            sensorManager.unregisterListener(this); // Desregistra o listener quando a view for destruída
        }
    }
}
