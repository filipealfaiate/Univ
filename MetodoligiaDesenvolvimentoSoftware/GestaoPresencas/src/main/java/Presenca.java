import java.util.ArrayList;
import java.util.HashMap;

/*
 * Presenca handles presences to save in map of presences
 */

public class Presenca {
	
	Utilizador user;
	Aula aula;
	
	Presenca(Utilizador user, Aula aula)
	{
		this.user = user;
		this.aula = aula;
	}
	
	public Utilizador getUser() 
	{
		return user;
	}
	
	public void setUser(Utilizador user)
	{
		this.user = user;
	}
	
	public Aula getAula()
	{
		return aula;
	}
	
	public void setAula(Aula aula)
	{
		this.aula = aula;
	}
	
	/*
	 * marcaPresenca gives presence to given user from given class
	 */
	public void marcaPresenca(Utilizador utilizador, Aula aula, HashMap<String, ArrayList<Utilizador>> mapOfPresencas)
	{
		//Gets value from map mapped with key(aula)
		ArrayList<Utilizador> presencas = mapOfPresencas.get(aula.getData());
		
		//If array doesn't exist
		if(presencas == null)
		{
			//Creates new one, saves a presence to user and saves in map
			presencas = new ArrayList<Utilizador>();
			presencas.add(utilizador);
			mapOfPresencas.put(aula.getData(), presencas);
		}
		else
		{
			//Just save presence and add to map
			presencas.add(utilizador);
			mapOfPresencas.put(aula.getData(), presencas);
		}
		
		//Increases number of presences in class and user
		aula.addPresenca();
		utilizador.addPresenca();
	}
	
	
	/*
	 * alterarPresenca -> given a user and a class, switch from lack to presence in class
	 */
	public void alterarPresenca(Utilizador tempUser, Aula tempAula, HashMap<String, ArrayList<Utilizador>> mapOfPresencas)
	{
		if(tempUser == null || tempAula == null)
		{
			System.out.println("Utilizador e/ou Data não encontrada");
			return;
		}
		
		//Gets array of presences (users) from map mapped to key tempAula
		ArrayList<Utilizador> presencas = mapOfPresencas.get(tempAula.getData());
		
		
		//If presences is null
		if(presencas == null)
		{
			presencas = new ArrayList<Utilizador>();
			tempUser.addPresenca();
			presencas.add(tempUser);
			mapOfPresencas.put(tempAula.getData(), presencas);
			return;
		}
		
		//Checks if given user has already a presence
		for(Utilizador s: presencas)
		{
			if(s.equals(tempUser))
			{
				System.out.println("Este aluno já tem presença nesta aula.");
				return;
			}
		}
		
		//if not, add a presence
		tempUser.addPresenca();
		presencas.add(tempUser);
		mapOfPresencas.put(tempAula.getData(), presencas);
	}
}

