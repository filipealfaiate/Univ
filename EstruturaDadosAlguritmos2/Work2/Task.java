import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException
	{
		//declarar um buffer para puder ler inputs
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		
		//ler o num de tarefas existentes, num de regras e significado de uma semana m'a
		String[] tarefasRegrasHardWeeks = buffer.readLine().split(" ");

		int tarefas = Integer.parseInt(tarefasRegrasHardWeeks[0]);
		int regras = Integer.parseInt(tarefasRegrasHardWeeks[1]);
		int hardWeeks = Integer.parseInt(tarefasRegrasHardWeeks[2]);

		//declarar lista de adjacencias e fila
		//a primeira 'e a lista de adjacencias representar o grafo
		//em ultimo temos a fila
		ArrayList<Node>[] tarefasPrecedencias = new ArrayList[tarefas];
		Queue<Node> queue = new LinkedList<Node>();

		//criação dos n'os para o grafo
		for (int i = 0; i < tarefas; i++)
		{
			//criar uma lista para guardar todos as adjacencias
			tarefasPrecedencias[i] = new ArrayList<Node>();

			//criar o n'o
			Node node = new Node();

			//atribuir valores ao n'o
			node.setValores(i);

			//add o n'o 'a lista, onde o primeiro elemento tem de ser lido primeiro que todos os seus
				//precedentes
			tarefasPrecedencias[i].add(node);
		}

		//criação do grafo
		for (int i = 0; i < regras; i++)
		{
			//ler a intrepetação do arco (u,v)
			String[] regrasTask = buffer.readLine().split(" ");

			int task1 = Integer.parseInt(regrasTask[0]);
			int task2 = Integer.parseInt(regrasTask[1]);

			//indicar que o arco tem um sentido de u para v e em seguida indica que o n'o v tem um
				//antecessor
			tarefasPrecedencias[task1].add(tarefasPrecedencias[task2].get(0));
			tarefasPrecedencias[task2].get(0).antecessor++;
		}

		//verificar quais sao os n'os que não tem adjacencias e coloca na fila
		for (int i = 0; i < tarefas; i++)
		{
			if (tarefasPrecedencias[i].get(0).antecessor == 0)
			{
				tarefasPrecedencias[i].get(0).week++;
				queue.add(tarefasPrecedencias[i].get(0));
			}
		}

		int badWeeks = 0, worstestWeek = 0, semana = 1;

		//enquanto a fila nao tiver vazio vai sempre lendo o que esta na fila
		while (!queue.isEmpty())
		{
			//quando achar a comparacao significa que existe todas as tarefas dessa semana na fila
			if (queue.peek().week == semana)
			{
				//ve quantas tarefas existem para fazer nessa semana (tamanho da queue)
				int size = queue.size();

				//verificar se as tarefas que existem sao maior que o numero que tem o significado
					//de uma semana m'a
				if (size > hardWeeks)
					badWeeks++;

				//verifica se 'e a pior semana
				if (size > worstestWeek)
					worstestWeek = size;
				semana++;
			}

			Node atual = queue.remove();

			//verifica todas as adjacencias do n'o atual do grafo
			for (Node removido : tarefasPrecedencias[atual.valor])
			{
				//atualizar semana para o antecessor (como o n'o so vai para a fila quando todos
					//os antecessores forem visitados entao quando for a vez do n'o removido ir para
					//a fila o ultimo antecessor 'e o maior pois como 'e uma pesquisa em largura,
					//vimos por niveis)
				removido.week = atual.week;

				//diminuimos o numero de antecessores
				removido.antecessor--;

				//quando nao houver mais antecessores vai para a fila
				if (removido.antecessor == 0)
				{
					removido.week++;
					queue.add(removido);
				}
			}
		}
		System.out.println(worstestWeek + " " + badWeeks);
	}
}