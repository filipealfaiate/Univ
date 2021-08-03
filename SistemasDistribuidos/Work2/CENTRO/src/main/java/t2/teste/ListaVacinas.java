package t2.teste;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
// import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)

@XmlRootElement(name = "listaVacinas")
public class ListaVacinas {
    
    @XmlElement(required = true)
    protected ArrayList<Vacina> vacinas;


    public ListaVacinas() {
        vacinas = new ArrayList<Vacina>();
    }	


    public ArrayList<Vacina> getVacinas() {
        return this.vacinas;
    }
    

    protected void add( Vacina a ) {
	vacinas.add( a );
    }

    protected int size() {
	return vacinas.size();
    }
    
}
