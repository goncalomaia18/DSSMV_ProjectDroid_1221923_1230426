package com.example.projeto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projeto.databinding.TelaPersonalizadoBinding;

import cod.model.PerguntaPersonalizado;
import cod.model.ClassConsequenciaPersonalizado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaPersonalizado extends Fragment {

    private TelaPersonalizadoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TelaPersonalizadoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configurando o botão "Adicionar Pergunta"
        binding.buttonAdicionarPergunta.setOnClickListener(v -> showDialogAdicionarPergunta());

        // Configurando o botão "Adicionar Consequência"
        binding.buttonAdicionarConsequencia.setOnClickListener(v -> showDialogAdicionarConsequencia());
    }

    private void showDialogAdicionarPergunta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Adicionar Pergunta de Verdade");

        final EditText input = new EditText(getContext());
        input.setHint("Digite a sua pergunta...");
        builder.setView(input);

        builder.setPositiveButton("Adicionar", (dialog, which) -> {
            String perguntaTexto = input.getText().toString().trim();

            if (!perguntaTexto.isEmpty()) {
                PerguntaPersonalizado novaPergunta = new PerguntaPersonalizado();
                novaPergunta.setPerguntaPersonalizado(perguntaTexto);

                adicionarPergunta(novaPergunta);
            } else {
                Toast.makeText(getContext(), "A pergunta não pode estar vazia!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void adicionarPergunta(PerguntaPersonalizado novaPergunta) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.adicionarPerguntaPersonalizado(novaPergunta);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Pergunta adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Erro ao adicionar pergunta: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogAdicionarConsequencia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Adicionar Consequência");

        final EditText input = new EditText(getContext());
        input.setHint("Digite a sua consequência...");
        builder.setView(input);

        builder.setPositiveButton("Adicionar", (dialog, which) -> {
            String consequenciaTexto = input.getText().toString().trim();

            if (!consequenciaTexto.isEmpty()) {
                ClassConsequenciaPersonalizado novaConsequencia = new ClassConsequenciaPersonalizado();
                novaConsequencia.setConsequenciaPersonalizado(consequenciaTexto);

                adicionarConsequencia(novaConsequencia);
            } else {
                Toast.makeText(getContext(), "A consequência não pode estar vazia!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void adicionarConsequencia(ClassConsequenciaPersonalizado novaConsequencia) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.adicionarConsequenciaPersonalizado(novaConsequencia);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Consequência adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Erro ao adicionar consequência: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
