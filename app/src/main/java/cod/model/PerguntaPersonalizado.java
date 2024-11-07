package cod.model;

public class PerguntaPersonalizado {
    private String _id;
    private String perguntaspersonalizado;


    public PerguntaPersonalizado() {}


    public String getId() {
        return _id;
    }

    public String getPerguntaPersonalizado() {
        return perguntaspersonalizado;
    }


    public void setPerguntaPersonalizado(String perguntaPersonalizado) {
        this.perguntaspersonalizado = perguntaPersonalizado;
    }

    public String toString() {
        return perguntaspersonalizado;
    }
}
