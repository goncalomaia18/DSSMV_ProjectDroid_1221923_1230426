package com.example.projeto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cod.model.ClassConsequenciaPersonalizado;
import com.example.projeto.databinding.RemoverConsequenciaBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoverConsequencias extends Fragment {

    private RemoverConsequenciaBinding binding;
    private List<ClassConsequenciaPersonalizado> listaConsequencias;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = RemoverConsequenciaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchConsequenciaspersonalizado();

        binding.listViewConsequencias.setOnItemClickListener((parent, view1, position, id) -> {
            ClassConsequenciaPersonalizado consequenciaSelecionada = listaConsequencias.get(position);
            removerConsequencia(consequenciaSelecionada.getId());
        });
    }

    private void fetchConsequenciaspersonalizado() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ClassConsequenciaPersonalizado>> call = apiService.getConsequenciaPersonalizado();
        call.enqueue(new Callback<List<ClassConsequenciaPersonalizado>>() {
            @Override
            public void onResponse(Call<List<ClassConsequenciaPersonalizado>> call, Response<List<ClassConsequenciaPersonalizado>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaConsequencias = response.body();

                    ArrayAdapter<ClassConsequenciaPersonalizado> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_list_item_1, listaConsequencias);
                    binding.listViewConsequencias.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Erro ao carregar consequências", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ClassConsequenciaPersonalizado>> call, Throwable t) {
                Toast.makeText(getContext(), "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removerConsequencia(String id) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.removerconsequencia(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Consequência removida com sucesso", Toast.LENGTH_SHORT).show();
                    fetchConsequenciaspersonalizado(); // Atualiza a lista após a remoção
                } else {
                    Toast.makeText(getContext(), "Erro ao remover a consequência", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
