package cod.model;

public class PerguntaPersonalizado {
    private String _id;
    private String perguntaspersonalizado;


    public PerguntaPersonalizado() {}

    // Getter para o ID
    public String getId() {
        return _id;
    }

    public String getPerguntaPersonalizado() {
        return perguntaspersonalizado;
    }


    public void setPerguntaPersonalizado(String perguntaPersonalizado) {
        this.perguntaspersonalizado = perguntaPersonalizado;
    }
}
