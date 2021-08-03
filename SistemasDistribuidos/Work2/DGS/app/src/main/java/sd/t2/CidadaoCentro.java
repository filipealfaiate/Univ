package sd.t2;

public class CidadaoCentro implements Comparable<CidadaoCentro>{

    String nome;
    Integer idade;
    String email;
    String codigo;
    String nomeCentro;
    int maxVac;

    public CidadaoCentro(String nome, Integer idade, String email, String codigo, String nomeCentro, int maxVac) {

        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.codigo = codigo;
        this.nomeCentro = nomeCentro;
        this.maxVac = maxVac;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade){
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }

    public String getCentro() {
        return nomeCentro;
    }
    
    public void setCentro(String nomeCentro) {
        this.nomeCentro = nomeCentro;
    }

    public int getMaxVac() {
        return maxVac;
    }
    
    public void setMaxVac(int maxVac){
        this.maxVac = maxVac;
    }
    
    @Override
    public String toString() {
        return "nome - "+nome+"\nidade - "+idade+"\nemail - "+email+"\ncodigo - "+codigo+"\nnomeCentro - "+nomeCentro+"\n";
    }

    @Override
    public int compareTo(CidadaoCentro o) {
        return this.getIdade().compareTo(o.getIdade());
    }

}
