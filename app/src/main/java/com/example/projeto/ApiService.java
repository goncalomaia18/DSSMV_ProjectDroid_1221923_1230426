package com.example.projeto;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {

    // Insira sua chave de API aqui
    private static final String API_KEY = "f9ac62cfdf5b449cd16ee1a1052d328b8e6b5";
    private static final String BASE_URL = "https://verdadeconsequencia-3d59.restdb.io/rest/perguntas";

    public static String fetchPergunta() {
        try {
            // Constrói a URL para a requisição
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configura o método da requisição e os cabeçalhos
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-apikey", API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");

            // Verifica o código de resposta
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lê a resposta da API
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Fecha a conexão e retorna a resposta
                in.close();
                connection.disconnect();

                return content.toString();
            } else {
                System.out.println("Erro: Código de resposta da API: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

