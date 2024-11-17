package cod.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import cod.activity.ConsequenciaFragmentPersonalizadoActivity;
import cod.api.ApiClient;
import cod.api.ApiService;
import cod.model.PerguntaPersonalizado;
import com.example.projeto.databinding.VerdadePersonalizadoBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.Random;

public class VerdadePersonalizado extends Fragment {

    private VerdadePersonalizadoBinding binding;

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
        fetchPerguntasPersonalizado();

        binding.buttonConsequenciaPersonalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(requireActivity(), ConsequenciaFragmentPersonalizadoActivity.class);
                startActivity(intent);

                requireActivity().finish();
            }
        });

        binding.buttonRespondeuPersonalizado.setOnClickListener(v ->
                fetchPerguntasPersonalizado()
        );


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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
