package cod.fragment;
import android.content.Intent;
import cod.activity.VerdadeActivity;
import cod.model.ClassConsequencia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cod.restdb.ApiClient;
import cod.restdb.ApiService;
import com.example.projeto.databinding.ConsequenciaBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.Random;

public class ConsequenciaFragment extends Fragment {

    private ConsequenciaBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ConsequenciaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchConsequencias();

        binding.buttonRespondeuConsequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(requireActivity(), VerdadeActivity.class);
                startActivity(intent);

                requireActivity().finish();
            }
        });

        binding.buttonnovaconsequencia.setOnClickListener(v ->
                fetchConsequencias()
        );
    }

    private void fetchConsequencias() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ClassConsequencia>> call = apiService.getConsequencia();

        call.enqueue(new Callback<List<ClassConsequencia>>() {
            @Override
            public void onResponse(Call<List<ClassConsequencia>> call, Response<List<ClassConsequencia>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ClassConsequencia> consequencias = response.body();
                    if (!consequencias.isEmpty()) {
                        int indexAleatorio = new Random().nextInt(consequencias.size());
                        String consequenciaAleatoria = consequencias.get(indexAleatorio).getConsequencia();
                        binding.textViewConsequencias.setText(consequenciaAleatoria);
                    } else {
                        binding.textViewConsequencias.setText("Nenhuma consequência disponível.");
                    }
                } else {
                    binding.textViewConsequencias.setText("Erro ao obter consequências: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ClassConsequencia>> call, Throwable t) {
                binding.textViewConsequencias.setText("Falha na requisição: " + t.getMessage());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}