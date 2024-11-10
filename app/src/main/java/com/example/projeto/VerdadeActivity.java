package com.example.projeto;

import android.os.Bundle;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;

public class VerdadeActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private float acelVal;
    private float acelLast;
    private float shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdade);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_verdade, new Verdade())
                    .commit();
        }
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        // Inicializar valores para a deteção de shake
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }

    // Listener do sensor para detectar o movimento
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calcular a aceleração
            acelLast = acelVal;
            acelVal = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;

            // Se o valor do shake exceder o limite, execute a ação
            if (shake > 12) {
                executarAcaoShake();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // para funcionar msm que nao faça nada
        }
    };

    // Metodo que será chamado quando o shake for detectado
    private void executarAcaoShake() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_verdade);
        if (fragment instanceof Verdade) {
            ((Verdade) fragment).fetchPerguntas();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Pausa a detecção do sensor quando a atividade está em segundo plano
        sensorManager.unregisterListener(sensorListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registra o listener novamente ao voltar para a atividade
        sensorManager.registerListener(sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
}

