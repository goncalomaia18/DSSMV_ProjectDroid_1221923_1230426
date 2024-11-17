package cod.fragment;

import cod.api.ApiClient;
import cod.api.ApiService;
import cod.model.Pergunta;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cod.activity.ConsequenciaActivity;
import com.example.projeto.databinding.VerdadeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.Random;

public class Verdade extends Fragment implements SensorEventListener {

    private VerdadeBinding binding;
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
        binding = VerdadeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configura o SensorManager para detecção de shake
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        // Inicializar valores de aceleração
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        fetchPerguntas();

        // Configura os botões
        binding.buttonConsequencia.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ConsequenciaActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        binding.buttonRespondeu.setOnClickListener(v -> fetchPerguntas());
    }

    public void fetchPerguntas() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Pergunta>> call = apiService.getPerguntas();

        call.enqueue(new Callback<List<Pergunta>>() {
            @Override
            public void onResponse(Call<List<Pergunta>> call, Response<List<Pergunta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pergunta> perguntas = response.body();

                    if (!perguntas.isEmpty()) {
                        int indexAleatorio = new Random().nextInt(perguntas.size());
                        String perguntaAleatoria = perguntas.get(indexAleatorio).getPergunta();
                        binding.textViewPerguntas.setText(perguntaAleatoria);
                    } else {
                        binding.textViewPerguntas.setText("Nenhuma pergunta disponível.");
                    }
                } else {
                    binding.textViewPerguntas.setText("Erro ao obter perguntas: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Pergunta>> call, Throwable t) {
                binding.textViewPerguntas.setText("Falha na requisição: " + t.getMessage());
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
            fetchPerguntas(); // Atualiza a pergunta ao detectar o shake
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Não é necessário implementar para este caso
    }

    @Override
    public void onResume() {
        super.onResume();
        // Registrar o listener do sensor
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Parar o listener para evitar o uso desnecessário de bateria
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}