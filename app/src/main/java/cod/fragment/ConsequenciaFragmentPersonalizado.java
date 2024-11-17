package cod.fragment;

import android.content.Context;
import android.content.Intent;
import cod.api.ApiClient;
import cod.api.ApiService;
import cod.model.ClassConsequenciaPersonalizado;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cod.activity.VerdadePersonalizadoActivity;
import com.example.projeto.databinding.ConsequenciaPersonalizadoBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.Random;

public class ConsequenciaFragmentPersonalizado extends Fragment implements SensorEventListener {

    private ConsequenciaPersonalizadoBinding binding;
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
        binding = ConsequenciaPersonalizadoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
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

        fetchConsequenciasPersonalizado();

        binding.buttonnovaconsequenciaPersonalizado.setOnClickListener(v ->
                fetchConsequenciasPersonalizado()
        );

        binding.buttonRespondeuConsequenciaPersonalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), VerdadePersonalizadoActivity.class);
                startActivity(intent);

                requireActivity().finish();
            }
        });
    }

    private void fetchConsequenciasPersonalizado() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ClassConsequenciaPersonalizado>> call = apiService.getConsequenciaPersonalizado();

        call.enqueue(new Callback<List<ClassConsequenciaPersonalizado>>() {
            @Override
            public void onResponse(Call<List<ClassConsequenciaPersonalizado>> call, Response<List<ClassConsequenciaPersonalizado>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ClassConsequenciaPersonalizado> consequenciaspersonalizado = response.body();
                    if (!consequenciaspersonalizado.isEmpty()) {
                        int indexAleatorio = new Random().nextInt(consequenciaspersonalizado.size());
                        String consequenciaAleatoria = consequenciaspersonalizado.get(indexAleatorio).getConsequenciaPersonalizado();
                        binding.textViewConsequenciasPersonalizado.setText(consequenciaAleatoria);
                    } else {
                        binding.textViewConsequenciasPersonalizado.setText("Nenhuma consequência disponível.");
                    }
                } else {
                    binding.textViewConsequenciasPersonalizado.setText("Erro ao obter consequências: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ClassConsequenciaPersonalizado>> call, Throwable t) {
                binding.textViewConsequenciasPersonalizado.setText("Falha na requisição: " + t.getMessage());
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
            fetchConsequenciasPersonalizado(); // Atualiza a pergunta ao detectar o shake
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
    }
}
