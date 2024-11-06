package com.example.projeto;
import cod.model.ClassConsequencia;
import cod.model.Pergunta;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import java.util.List;

public interface ApiService {
    @Headers({
            "Content-Type: application/json",
            "x-apikey: f9ac62cfdf5b449cd16ee1a1052d328b8e6b5"
    })
    @GET("perguntas")
    Call<List<Pergunta>> getPerguntas();


    @Headers({
            "Content-Type: application/json",
            "x-apikey: f9ac62cfdf5b449cd16ee1a1052d328b8e6b5"
    })
    @GET("consequencias")
    Call<List<ClassConsequencia>> getConsequencia();
}



