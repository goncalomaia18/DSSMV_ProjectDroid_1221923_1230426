package com.example.projeto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.projeto.databinding.FragmentFirstBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchPerguntas();

        binding.buttonFirst.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
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
                    StringBuilder perguntasText = new StringBuilder();

                    for (Pergunta pergunta : perguntas) {
                        String perguntaTexto = pergunta.getPergunta();
                        perguntasText.append(perguntaTexto).append("\n");
                    }

                    binding.textViewPerguntas.setText(perguntasText.toString());
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
