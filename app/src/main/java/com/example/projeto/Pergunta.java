package com.example.projeto;

public class Pergunta {
    private String _id; // se você precisa do ID
    private String pergunta; // certifique-se de que este campo corresponda ao JSON

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    // Adicione um getter para _id se necessário
    public String getId() {
        return _id;
    }
}



