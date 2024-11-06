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
        binding.buttonAdicionarPergunta.setOnClickListener(v -> {
            // Exibir a caixa de diálogo para adicionar pergunta
            showDialogAddPergunta();
        });
    }

    private void showDialogAddPergunta() {
        // Criando a caixa de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Adicionar Pergunta de Verdade");

        // Criando o campo de texto para a pergunta
        final EditText input = new EditText(getContext());
        input.setHint("Digite a sua pergunta...");
        builder.setView(input);

        builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String perguntaTexto = input.getText().toString().trim();

                if (!perguntaTexto.isEmpty()) {
                    // Criar objeto PerguntaPersonalizado
                    PerguntaPersonalizado novaPergunta = new PerguntaPersonalizado();
                    novaPergunta.setPerguntaPersonalizado(perguntaTexto);

                    // Enviar para o servidor via API
                    adicionarPergunta(novaPergunta);
                } else {
                    Toast.makeText(getContext(), "A pergunta não pode estar vazia!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void adicionarPergunta(PerguntaPersonalizado novaPergunta) {
        // Chamar o método da API para adicionar a pergunta
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
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
