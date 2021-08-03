package t2.teste;

public class Cidadao implements Comparable<Cidadao >{

    String nome;
    Integer idade;
    String email;
    String codigo;

    public Cidadao(String nome, Integer idade, String email, String codigo) {

        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.codigo = codigo;
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
    
    @Override
    public String toString() {
        return "nome - "+nome+"\nidade - "+idade+"\nemail - "+email+"\ncodigo - "+codigo+"\n";
    }

    @Override
    public int compareTo(Cidadao o) {
        return this.getIdade().compareTo(o.getIdade());
    }


    
}
