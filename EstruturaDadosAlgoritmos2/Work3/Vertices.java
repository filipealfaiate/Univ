public class Vertices
{
	int sala;
	int distancia;
	Vertices predecessor;

	public void setValor(int sala, int distancia)
	{
		this.sala = sala;
		this.distancia = distancia;
		this.predecessor = null;
	}
}