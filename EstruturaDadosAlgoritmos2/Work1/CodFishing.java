import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CodFishing
{
	public static void main(String[] args) throws IOException
	{
		//declarar um buffer para puder ler inputs
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		
		//ler uma String com dois valores, numero de barcos e numeros de peixes
		String[] nB_nP = buffer.readLine().split(" ");

		//transformar os dois valores do input em inteiros e guardar em variaveis
		int nBarcos = Integer.parseInt(nB_nP[0]);
		int nPeixes = Integer.parseInt(nB_nP[1]);

		//declarar dois arrays para guardar todas as informacoes((x,y), rating/nPeixes) sobre
			//os barcos e os peixes
		Item[] barcos = new Item[nBarcos];
		Item[] peixes = new Item[nPeixes];

		//ler o input sobre informacoes do barco e guardando na instancian de class barco as informacoes
			//no sitio certo,
		//verificando se esta tudo dentro dos limites
		for (int i = 0; i < nBarcos; i++)
		{
			Item barco = new Item();

			String[] barcos_input = buffer.readLine().split(" ");

			barco.setCordenadas(Integer.parseInt(barcos_input[0]),
								Integer.parseInt(barcos_input[1]),
								Integer.parseInt(barcos_input[2]));

			barcos[i] = barco;
		}

		//ordenar o array de barcos do menor para o maior
		Arrays.sort(barcos, (a,b) -> {return a.atributo < b.atributo ? -1 : a.atributo > b.atributo ? 1 : 0;});

		//ler o input sobre informacoes dos peixes e guardando na instancia de class peixes as informacoes no
			//sitio certo,
		//verificando se esta tudo dentro dos limites		
		for (int i = 0; i < nPeixes; i++)
		{
			Item peixe = new Item();

			String[] peixes_intput = buffer.readLine().split(" ");

			peixe.setCordenadas(Integer.parseInt(peixes_intput[0]),
								Integer.parseInt(peixes_intput[1]),
								Integer.parseInt(peixes_intput[2]));

			peixes[i] = peixe;
		}

		//ordenar o array de peixes do menor para o maior
		Arrays.sort(peixes, (a,b) -> {return a.atributo < b.atributo ? -1 : a.atributo > b.atributo ? 1 : 0;});

		//matriz que guarda todas as solucoes
		Solution[][] matriz_sol = new Solution[peixes.length + 1][barcos.length + 1];

		//pesquisa para achar a melhor solucao
		pesquisaSeg(matriz_sol, barcos, peixes);
	}

	public static void pesquisaSeg(Solution[][] matriz_sol, Item[] barcos, Item[] peixes)
	{
		//percorre todos os peixes
		for (int p = 0; p <= peixes.length; p++)
		{
			//percorre todos os barcos
			for (int b = 0; b <= barcos.length; b++)
			{
				//criar um tuplo Solution na posicao [p][b]
				matriz_sol[p][b] = new Solution();
				//colocar na mesma posicao os valores iniciais (maxPeixes, dist, rating)
				matriz_sol[p][b].setSolution(0, 0, 0);

				//se nao tiver barcos para colocar ou sitios de peixe para atribuir nao entra no if
				if (b != 0 && p != 0)
				{
					//copia o valor da posicao [p - 1][b - 1] para a posicao atual
					matriz_sol[p][b].copy(matriz_sol[p - 1][b - 1]);
					//e incrementa com os valores do barco atual na posicao atual
					//(a posicao atual e [b - 1][p - 1] porque o array dos barcos e dos peixes
					//vai de 0 a barcos.length e peixes.length)
					matriz_sol[p][b].inc(barcos[b -1], peixes[p -1]);

					//verifica se tem naquela posicao mas com o barco anterior ha mais peixes,
					//menos distancia e menos rating (por esta ordem de prevalencia)
					//caso entre ele copia os valores para a posicao atual
					if (matriz_sol[p][b - 1].maxPeixes > matriz_sol[p][b].maxPeixes
						|| (matriz_sol[p][b - 1].maxPeixes == matriz_sol[p][b].maxPeixes 
							&& matriz_sol[p][b - 1].dist < matriz_sol[p][b].dist)
						|| (matriz_sol[p][b - 1].maxPeixes == matriz_sol[p][b].maxPeixes
							&& matriz_sol[p][b - 1].dist == matriz_sol[p][b].dist
							&& matriz_sol[p][b - 1].rating < matriz_sol[p][b].rating))
						matriz_sol[p][b].copy(matriz_sol[p][b - 1]);
				}
			}		
		}
		
		//mostra o melhor caso
		System.out.println(matriz_sol[peixes.length][barcos.length].maxPeixes + " "+
						   matriz_sol[peixes.length][barcos.length].dist + " " +
						   matriz_sol[peixes.length][barcos.length].rating);
	}
}