package com.example.projeto;

import cod.model.Pergunta;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.projeto.databinding.VerdadeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.Random;


public class Verdade extends Fragment {

    private VerdadeBinding binding;

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
        fetchPerguntas();

        binding.buttonConsequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria o Intent usando o contexto da Activity associada
                Intent intent = new Intent(requireActivity(), ConsequenciaActivity.class);
                startActivity(intent);

                // Finaliza a Activity anterior, se desejar fechar a MainActivity
                requireActivity().finish();
            }
        });

        binding.buttonRespondeu.setOnClickListener(v ->
                fetchPerguntas() // Chama novamente para carregar uma nova pergunta
        );
    }

    private void fetchPerguntas() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Pergunta>> call = apiService.getPerguntas();

        call.enqueue(new Callback<List<Pergunta>>() {
            @Override
            public void onResponse(Call<List<Pergunta>> call, Response<List<Pergunta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pergunta> perguntas = response.body();

                    if (!perguntas.isEmpty()) {
                        // Seleciona uma pergunta aleatória
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
