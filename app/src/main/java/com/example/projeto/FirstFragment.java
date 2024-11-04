package com.example.projeto;

import android.os.Bundle;
import android.util.Log;
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
        return binding.getRoot(); // Retorna a raiz do layout
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
                Log.d("API", "Código da resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    List<Pergunta> perguntas = response.body();
                    StringBuilder perguntasText = new StringBuilder();
                    for (Pergunta pergunta : perguntas) {
                        perguntasText.append("Pergunta: ").append(pergunta.getPergunta()).append("\n");
                    }

                    binding.textViewPerguntas.setText(perguntasText.toString());
                } else {
                    Log.e("API", "Erro: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Pergunta>> call, Throwable t) {
                Log.e("API", "Erro de requisição: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Limpa a referência para evitar vazamentos de memória
    }
}

