package cod.api;
import cod.model.ClassConsequencia;
import cod.model.ClassConsequenciaPersonalizado;
import cod.model.Pergunta;
import cod.model.PerguntaPersonalizado;

import retrofit2.Call;
import retrofit2.http.*;

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


    @Headers({
            "Content-Type: application/json",
            "x-apikey: f9ac62cfdf5b449cd16ee1a1052d328b8e6b5"
    })
    @GET("perguntaspersonalizado")
    Call<List<PerguntaPersonalizado>> getPerguntaPersonalizado();

    @Headers({
            "Content-Type: application/json",
            "x-apikey: f9ac62cfdf5b449cd16ee1a1052d328b8e6b5"
    })
    @POST("perguntaspersonalizado")
    Call<Void>adicionarPerguntaPersonalizado(@Body PerguntaPersonalizado novaPergunta);

    @Headers({
            "Content-Type: application/json",
            "x-apikey: f9ac62cfdf5b449cd16ee1a1052d328b8e6b5"
    })
    @DELETE("perguntaspersonalizado/{id}")
    Call<Void> deletePergunta(@Path("id") String id);


    @Headers({
            "Content-Type: application/json",
            "x-apikey: f9ac62cfdf5b449cd16ee1a1052d328b8e6b5"
    })
    @GET("consequenciaspersonalizado")
    Call<List<ClassConsequenciaPersonalizado>> getConsequenciaPersonalizado();

    @Headers({
            "Content-Type: application/json",
            "x-apikey: f9ac62cfdf5b449cd16ee1a1052d328b8e6b5"
    })
    @POST("consequenciaspersonalizado")
    Call<Void>adicionarConsequenciaPersonalizado(@Body ClassConsequenciaPersonalizado novaConsequencia);

    @Headers({
            "Content-Type: application/json",
            "x-apikey: f9ac62cfdf5b449cd16ee1a1052d328b8e6b5"
    })
    @DELETE("consequenciaspersonalizado/{id}")
    Call<Void> removerconsequencia(@Path("id") String id);



}




