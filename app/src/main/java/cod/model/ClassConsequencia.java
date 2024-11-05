package cod.model;

public class ClassConsequencia {
    private String _id;
    private String consequencias;


    public ClassConsequencia() {}

    // Getter para o ID
    public String getId() {
        return _id;
    }

    public String getConsequencia() {
        return consequencias;
    }


    public void setConsequencia(String consequencia) {
        this.consequencias = consequencia;
    }
}
