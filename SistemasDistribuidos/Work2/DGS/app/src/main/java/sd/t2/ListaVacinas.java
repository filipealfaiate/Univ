/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd.t2;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author josemigueldasilvasantos
 */
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
