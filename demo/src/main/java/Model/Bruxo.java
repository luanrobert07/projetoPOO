package Model;

public class Bruxo {
    private int id;
    private int idUsuario;
    private String varinha;
    private String patrono;
    private String habilidadeMagica;

    // Construtor vazio
    public Bruxo() {
    }

    public Bruxo(int idUsuario, String varinha, String patrono, String habilidadeMagica) {
        this.idUsuario = idUsuario;
        this.varinha = varinha;
        this.patrono = patrono;
        this.habilidadeMagica = habilidadeMagica;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getVarinha() {
        return varinha;
    }

    public void setVarinha(String varinha) {
        this.varinha = varinha;
    }

    public String getPatrono() {
        return patrono;
    }

    public void setPatrono(String patrono) {
        this.patrono = patrono;
    }

    public String getHabilidadeMagica() {
        return habilidadeMagica;
    }

    public void setHabilidadeMagica(String habilidadeMagica) {
        this.habilidadeMagica = habilidadeMagica;
    }
}

