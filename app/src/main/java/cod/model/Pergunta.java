package cod.model;

public class Pergunta {
    private String _id;
    private String perguntas;


    public Pergunta() {}

    // Getter para o ID
    public String getId() {
        return _id;
    }

    public String getPergunta() {
        return perguntas;
    }


    public void setPergunta(String pergunta) {
        this.perguntas = pergunta;
    }
}
