package com.example.projeto;

import android.content.Intent;
import cod.model.ClassConsequencia;
import cod.model.ClassConsequenciaPersonalizado;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.projeto.databinding.ConsequenciaPersonalizadoBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.Random;

public class ConsequenciaFragmentPersonalizado extends Fragment {

    private ConsequenciaPersonalizadoBinding binding;

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
        fetchConsequenciasPersonalizado();

        binding.buttonnovaconsequenciaPersonalizado.setOnClickListener(v ->
                fetchConsequenciasPersonalizado()
        );

        binding.buttonRespondeuConsequenciaPersonalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria o Intent usando o contexto da Activity associada
                Intent intent = new Intent(requireActivity(), VerdadePersonalizadoActivity.class);
                startActivity(intent);

                // Finaliza a Activity anterior, se desejar fechar a MainActivity
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
