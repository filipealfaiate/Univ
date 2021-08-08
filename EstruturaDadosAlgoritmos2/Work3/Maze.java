import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Maze
{
	//definir infinito como o maior numero inteiro de 32 bits
	static int infinity = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException
	{
		//declarar um buffer para puder ler inputs
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

		String[] fistLine = buffer.readLine().split(" ");

		//numero de salas
		int nRooms = Integer.parseInt(fistLine[0]);
		//numero de corredores
		int nCorridors = Integer.parseInt(fistLine[1]);

		//lista com todas as salas
		Vertices[] listaSalas = new Vertices[nRooms];
		Arcos[] conjuntoCorredor = new Arcos[nCorridors];

		/*
		caso haja um saco de dinheiro (B), entao adiciona ao seu saldo (+),
		caso tenha um crocodilo (C) tera de gastar do seu saldo ou colocar em credito (-)
		verifica se a sala inicial e a sala final existem
		caso nao existam, cria uma com numero da sala inicial,
			outra com numero da sala final e ambas a uma distancia desconhecida (infinito),
			adicionando cada uma delas a lista de todas as salas
		criar um corredor dando a sala inicial, a sala final e o custo de atravessa-lo
		por fim adicionamos aos corredores que iniciam na sala inicial*/
		for (int i = 0; i < nCorridors; i++)
		{
			String[] corridorsInf = buffer.readLine().split(" ");

			int cost = Integer.parseInt(corridorsInf[3]);
			int salaInicial = Integer.parseInt(corridorsInf[0]);
			int salaFinal = Integer.parseInt(corridorsInf[1]);
			String crocadileBag = corridorsInf[2];

			if (crocadileBag.compareTo("C") == 0)
				cost = -cost;

			if(listaSalas[salaInicial] == null)
			{
				Vertices inicialRoom = new Vertices();

				inicialRoom.setValor(salaInicial, infinity);

				listaSalas[salaInicial] = inicialRoom;
			}

			if(listaSalas[salaFinal] == null)
			{
				Vertices finalRoom = new Vertices();

				finalRoom.setValor(salaFinal, infinity);

				listaSalas[salaFinal] = finalRoom;
			}

			Arcos corredor = new Arcos();

			corredor.setValor(cost, listaSalas[salaInicial], listaSalas[salaFinal]);

			conjuntoCorredor[i] = corredor;
		}

		//indicar que a sala de partida se encontra a distancia 0
		listaSalas[0].distancia = 0;

		//percorre todas as salas para encontrar o caminho mais curto ate a saida
		for (Vertices sala : listaSalas)
		{
			/*
			calcula se a distancia da sala inicial daquele corredor(se for diferente de infinito)
				e o custo de atravesar o corredor 'e menor que a distancia ate a sala final
			caso seja entao atualiza a distanca da sala final e o seu predecessor*/
			for (Arcos corredor : conjuntoCorredor)
			{
				if (corredor.salaInicial.distancia == infinity) continue;

				int distancia = corredor.salaInicial.distancia + corredor.peso;

				if (distancia < corredor.salaFinal.distancia)
				{
					corredor.salaFinal.distancia = distancia;
					corredor.salaFinal.predecessor = corredor.salaInicial;
				}
			}
		}

		/*
		indicador que sinaliza a existencia de um ciclo no labirinto que apresenta um custo negativo
			(soma total dos caminhos do ciclo)*/
		boolean flag = false;

		//percorrer todos os corredores para verificar se existe algum ciclo
		for (Arcos corredor : conjuntoCorredor)
			if (corredor.salaInicial.distancia + corredor.peso < corredor.salaFinal.distancia)
				flag = true;
		
		/*
		resposta ao problema
		caso tenha ciclos negativos ou a distancia da sala de saida seja menor que 0,
			entao a resposta 'e "yes", ficou a dever
		caso nao tenha ciclos negativos e a distancia da sala de saida seja maior ou igual que 0,
			entao a resposta 'e "no", nao ficou a dever*/
		System.out.println((flag || listaSalas[listaSalas.length - 1].distancia < 0)? "yes" : "no");
	}
}