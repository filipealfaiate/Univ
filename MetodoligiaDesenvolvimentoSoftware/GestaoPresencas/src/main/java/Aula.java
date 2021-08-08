public class Aula {
	
	String data;
	String hora;
	int nPresencas = 0;
	boolean lecionada = false;
	
	Aula(String data, String hora)
	{
		this.data = data;
		this.hora = hora;
	}
	
	//Getters
	public String getData()
	{
		return this.data;
	}
	
	public String getHora()
	{
		return this.hora;
	}
	
	public int getNPresencas()
	{
		return this.nPresencas;
	}
	
	public boolean isLecionada()
	{
		return lecionada;
	}
	
	//Setters
	public void setData(String data)
	{
		this.data = data;
	}
	
	public void setHora(String hora)
	{
		this.hora = hora;
	}
	
	public void addPresenca()
	{
		this.nPresencas++;
	}
	
	public void setLecionada()
	{
		this.lecionada = true;
	}
	
	/*
	 * mostrarRelatorio shows the "graph" of presences and lacks of given class
	 */
	public static void mostrarRelatorio(Aula aula)
	{
		System.out.println("Aula : " + aula.getData());
		System.out.println("Faltas : " + (GestorPresencasMenu.nUsers - aula.getNPresencas() + 1));
	}
}
