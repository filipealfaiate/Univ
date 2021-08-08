import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Leitor
{
    Aula aula;
    public boolean docente_presente = false;
    public ArrayList<Utilizador> temp;
    
    Leitor(Aula aula)
    {
    	this.aula = aula;
    	this.temp = new ArrayList<Utilizador>();
    }
    
    /*
     * regista registers/marks presence a given user to a given class
     * Students who arrive before the teacher are put in a temporary memory array until the teacher arrives
     * If teacher arrives, every student in temporary memory will be marked a presence (students who come after the teacher too)
     */
    public void regista(Utilizador utilizador, Aula aula, HashMap<String, ArrayList<Utilizador>> mapOfPresencas)
    {
    	//System.out.println("Registando " + utilizador.getNome() + " na aula " + aula.getData() + "\n");
    	if(utilizador == null || aula == null)
    	{
    		throw new NullPointerException();
    	}
    	
    	if(verificaPresenca(utilizador))
    	{
    		System.out.println("Utilizador " + utilizador.getNome() + " ja esta presente.");
    		return;
    	}
    	
        if(utilizador.getPapel().equals("docente"))
        {
            this.docente_presente = true;
            
            this.aula.setLecionada();
            
            Presenca presenca = new Presenca(utilizador, aula);
            
            presenca.marcaPresenca(utilizador, aula, mapOfPresencas);
            
            for(Utilizador user : this.temp)
            {
                Presenca presente_guardado = new Presenca(user, aula);
                
                presente_guardado.marcaPresenca(user, aula, mapOfPresencas);
            }
            
            return;
        }
        
        else
        {
            if(!this.docente_presente)
            {
                this.temp.add(utilizador);
            }
            
            else
            {
                Presenca aluno_presente = new Presenca(utilizador, aula);
                aluno_presente.marcaPresenca(utilizador, aula, mapOfPresencas);
                this.temp.add(utilizador);
            }
        }
        
        /*
        System.out.println("MAP: ");
        for (Aula name: GestorPresencasMenu.mapOfPresencas.keySet()){
            System.out.println(name.getData());
        } 
        */
    }
    
    /*
     * verificaPresenca verifies if given user was already present in class
     */
    public boolean verificaPresenca(Utilizador user)
    {
    	for(Utilizador u : this.temp)
    	{
    		if(u.equals(user))
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /*
     * resetLeitor resets the card reader
     */
    public void resetLeitor(Aula aula)
    {
    	//If teacher arrived, lectured classes increase
    	if(aula.isLecionada())
    	{
        	GestorPresencasMenu.aulasN++;
    	}
    	else // else teacher lacks increase
    	{
    		GestorPresencasMenu.faltasProf++;
    	}
    	
    	//Puts in map of presences
    	GestorPresencasMenu.mapOfPresencas.put(aula.getData(), this.temp);
    	//Clears the temporary array
    	this.temp.clear();
    }
}