package com.example.projeto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cod.model.PerguntaPersonalizado;
import com.example.projeto.databinding.RemoverVerdadeBinding;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoverVerdades extends Fragment {

    private RemoverVerdadeBinding binding;
    private List<PerguntaPersonalizado> ListaPerguntas;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = RemoverVerdadeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchPerguntaspersonalizado();

        binding.listViewPerguntas.setOnItemClickListener((parent, view1, position, id) -> {
            PerguntaPersonalizado perguntaSelecionada = ListaPerguntas.get(position);
            removerPergunta(perguntaSelecionada.getId());
        });
    }

    private void fetchPerguntaspersonalizado() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PerguntaPersonalizado>> call = apiService.getPerguntaPersonalizado();
        call.enqueue(new Callback<List<PerguntaPersonalizado>>() {
            @Override
            public void onResponse(Call<List<PerguntaPersonalizado>> call, Response<List<PerguntaPersonalizado>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListaPerguntas = response.body();

                    ArrayAdapter<PerguntaPersonalizado> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_list_item_1, ListaPerguntas);
                    binding.listViewPerguntas.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Erro ao carregar perguntas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PerguntaPersonalizado>> call, Throwable t) {
                Toast.makeText(getContext(), "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removerPergunta(String id) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.deletePergunta(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Verdade removida com sucesso", Toast.LENGTH_SHORT).show();
                    fetchPerguntaspersonalizado();
                } else {
                    Toast.makeText(getContext(), "Erro ao remover a verdade", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

