package cod.fragment;

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

import cod.api.ApiClient;
import cod.api.ApiService;
import cod.activity.ConsequenciaFragmentPersonalizadoActivity;
import com.example.projeto.databinding.VerdadePersonalizadoBinding;

import java.util.List;
import java.util.Random;

import cod.model.PerguntaPersonalizado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerdadePersonalizado extends Fragment implements SensorEventListener {

    private VerdadePersonalizadoBinding binding;

    // Variáveis para o Sensor
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float acelVal;  // Valor atual da aceleração
    private float acelLast; // Última aceleração registrada
    private float shake;    // Aceleração acumulada

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = VerdadePersonalizadoBinding.inflate(inflater, container, false);
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

        // Inicializar os valores de aceleração
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        // Busca as perguntas personalizadas
        fetchPerguntasPersonalizado();

        // Configura os botões
        binding.buttonConsequenciaPersonalizado.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ConsequenciaFragmentPersonalizadoActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        binding.buttonRespondeuPersonalizado.setOnClickListener(v -> fetchPerguntasPersonalizado());
    }

    private void fetchPerguntasPersonalizado() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PerguntaPersonalizado>> call = apiService.getPerguntaPersonalizado();

        call.enqueue(new Callback<List<PerguntaPersonalizado>>() {
            @Override
            public void onResponse(Call<List<PerguntaPersonalizado>> call, Response<List<PerguntaPersonalizado>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PerguntaPersonalizado> perguntaspersonalizado = response.body();

                    if (!perguntaspersonalizado.isEmpty()) {
                        int indexAleatorio = new Random().nextInt(perguntaspersonalizado.size());
                        String perguntaAleatoria = perguntaspersonalizado.get(indexAleatorio).getPerguntaPersonalizado();
                        binding.textViewPerguntasPersonalizado.setText(perguntaAleatoria);
                    } else {
                        binding.textViewPerguntasPersonalizado.setText("Nenhuma pergunta disponível.");
                    }
                } else {
                    binding.textViewPerguntasPersonalizado.setText("Erro ao obter perguntas: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<PerguntaPersonalizado>> call, Throwable t) {
                binding.textViewPerguntasPersonalizado.setText("Falha na requisição: " + t.getMessage());
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
        shake = shake * 0.9f + delta; // Suavização da aceleração

        if (shake > 12) { // Limite ajustável para detecção de shake
            fetchPerguntasPersonalizado(); // Atualiza a pergunta personalizada
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Não é necessário implementar para esta funcionalidade
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
        // Cancelar o listener do sensor para economizar bateria
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
