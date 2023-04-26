public class Planetario {
    private int idPlanetario;
    private String nome;
    private String cep;
    private String complemento;
    private String urlFoto;
    private String descricao;

    public Planetario(int idPlanetario, String nome, String cep, String complemento, String urlFoto, String descricao) {
        this.idPlanetario = idPlanetario;
        this.nome = nome;
        this.cep = cep;
        this.complemento = complemento;
        this.urlFoto = urlFoto;
        this.descricao = descricao;
    }

    public int getIdPlanetario() {
        return idPlanetario;
    }

    public void setIdPlanetario(int id) {
        this.idPlanetario = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getUrlFoto() {
        return this.urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }


    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
