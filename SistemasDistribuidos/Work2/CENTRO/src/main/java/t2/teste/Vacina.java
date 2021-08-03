package t2.teste;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vacina", propOrder = {
    "nome",
    "nVacinas"
})
@XmlRootElement(name = "vacina")
public class Vacina {
    
    @XmlElement(required = true)
    protected String nome;
    @XmlElement(required = true)
    protected int nVacinas;
    
    public Vacina(){
    }
    
    public Vacina(String nome, int nVacinas){
        
        this.nome = nome;
        this.nVacinas = nVacinas;
                
    }
    
    public void setNome(String nome){
        
        this.nome = nome;
    }
    
    public String getNome(){
        return nome;
    }

    public void setNumeroVacinas(int nVacinas){
        this.nVacinas = nVacinas;
    }
    
    public int getNumeroVacinas(){
        return nVacinas;
    }
    
}