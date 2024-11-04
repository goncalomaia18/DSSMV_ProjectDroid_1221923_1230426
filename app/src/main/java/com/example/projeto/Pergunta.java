package com.example.projeto; // Verifique se o pacote está correto

public class Pergunta {
    private String _id; // Campo para o ID
    private String pergunta; // Campo para o texto da pergunta

    // Construtor padrão
    public Pergunta() {}

    // Getter para o ID
    public String getId() {
        return _id;
    }

    // Getter para o texto da pergunta
    public String getPergunta() {
        return pergunta;
    }

    // Setter para o texto da pergunta
    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }
}
