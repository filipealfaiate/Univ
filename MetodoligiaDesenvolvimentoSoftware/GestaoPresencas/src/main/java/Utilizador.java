public class Utilizador
{
    private String nome;
    private String nr_cartao;
    private String papel;
    public boolean presenca_bool;
    private int presencas;
    
    //construtor
    public Utilizador(String nome, String nr_cartao, String papel)
    {
        this.nome = nome;
        this.nr_cartao = nr_cartao;
        this.papel = papel;
        this.presencas = 0;
    }

    //getters
    public String getNome()
    {
        return nome;
    }

    public String getNR_CARTAO()
    {
        return nr_cartao;
    }
    
    public String getPapel()
    {
        return papel;
    }
    
    public int getPresencas()
    {
    	return presencas;
    }

    //setters
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public void setNR_CARTAO(String nr_cartao)
    {
        this.nr_cartao = nr_cartao;
    }

    public void setPapel(String papel)
    {
        this.papel = papel;
    }
    
    public void addPresenca()
    {
    	this.presencas++;
    }
}