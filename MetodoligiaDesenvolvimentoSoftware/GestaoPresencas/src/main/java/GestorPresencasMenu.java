import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * MAIN PROGRAM
 * GestorPresencasMenu handles the menu in which the user interacts with
 */
public class GestorPresencasMenu {
	public static int nUsers = 0;
	public static int aulasN = 0;
	public static int faltasProf = 0;
	private static final String USER_DIRECTORY = "<INSERT YOUR USERS JSON FILE DIRECTORY HERE, WITHOUT THE FILENAME>";
	private static final String LECTURE_DIRECTORY = "<INSERT YOUR LECTURES JSON FILE DIRECTORY HERE, WITH THE FILENAME>";

	static List<Utilizador> utilizadores = new ArrayList<Utilizador>();
	static List<Aula> aulas = new ArrayList<Aula>();
	public static HashMap<String, ArrayList<Utilizador>> mapOfPresencas = new HashMap<String, ArrayList<Utilizador>>();

	public static void main(String[] args) throws Exception {
		import_classes_data(aulas); // Parses json file in LECTURE_DIRECTORY

		Scanner scanner = new Scanner(System.in);

		System.out.println(" \n Registo de presenças das aulas de MDS.   \n");

		while (true) {
			System.out.println("\n\n\nPara:\n");
			System.out.println("Ir a aplicação 1                - pressione 1");
			System.out.println("Importar dados dos utilizadores - pressione 2");
			System.out.println("Justificar Falta                - pressione 3");
			System.out.println("Mostrar relatório de faltas     - pressione 4");
			System.out.println("Consultar faltas por utilizador - pressione 5");
			System.out.println("Mostrar turma                   - pressione 6");
			System.out.println("Sair do programa                - pressiona X");

			char opcao = scanner.next().charAt(0);

			switch (opcao) {
			case '1':

				if (utilizadores.isEmpty()) {
					System.out.println("Lista dos utilizadores vazia, pode importar os dados na opção 2.");
					break;
				}

				// Vai a aplicação 1
				mostrarHorario(aulas);

				System.out.println("Selecione a data que pretende:");
				System.out.println("No modo AAAA-MM-DD");
				String date = scanner.next();
				boolean ok = false;

				for (Aula aula : aulas) {
					if (aula.getData().equals(date)) {
						ok = true;
						initApp1(aula);
						break;
					}
				}
				
				if(!ok)
				{
					System.out.println("Aula não encontrada");
				}
				
				break;

			case '2':
				System.out.println("Insira o nome do ficheiro: ");
				System.out.println("(O ficheiro tem que estar na mesma diretoria inserida)");
				String filename = scanner.next();

				import_user_data(utilizadores, filename);
				break;

			case '3':
				// Justificar falta
				System.out.println("Insira o número do cartão do utilizador a justificar falta:");
				String n_cartao = scanner.next();
				System.out.println("Insira a data da aula que quer justificar a falta:");
				String data = scanner.next();

				Utilizador tempUser = null;
				Aula tempAula = null;

				for (Utilizador u : utilizadores) {
					if (u.getNR_CARTAO().equals(n_cartao)) {
						tempUser = u;
						break;
					}
				}

				for (Aula a : aulas) {
					if (a.getData().equals(data)) {
						tempAula = a;
						break;
					}
				}

				Presenca p = new Presenca(tempUser, tempAula);

				p.alterarPresenca(tempUser, tempAula, mapOfPresencas);
				break;

			case '4':

				////
				System.out.println("Relatório de todas as aulas lecionadas:\n");
				
				
				for (String a : mapOfPresencas.keySet()) // Iterates through map
				{
					for(Aula aula : aulas)
					{
						if(a.equals(aula.getData()))
						{
							Aula.mostrarRelatorio(aula);
						}
					}
				}

				System.out.println("\n\n");
				break;

			case '5':
				System.out.println("Insira o numero do cartao que quer consultar:\n");
				String n = scanner.next();

				Utilizador temp = null;

				for (Utilizador user : utilizadores) {
					if (user.getNR_CARTAO().equals(n)) {
						temp = user;
						break;
					}
				}

				if (temp == null) {
					System.out.println("Utilizador não existe");
					break;
				}

				System.out.println(temp.getNome());

				if (temp.getPapel().equals("docente")) {
					System.out.println("Faltas: " + faltasProf);
				}

				else {
					System.out.println("Faltas: " + (aulasN - temp.getPresencas()));
				}

				break;

			case '6':
				showClass();
				break;

			case 'X':
				scanner.close();
				return;
			}
		}
	}

	/*
	 * parseUserObject parses a given JSON object and creates respective instance
	 */
	private static Utilizador parseUserObject(JSONObject user) {

		String name = (String) user.get("nome");
		String card = (String) user.get("cartao");
		String role = (String) user.get("papel");

		Utilizador newUser = new Utilizador(name, card, role);

		return newUser;
	}

	/*
	 * parseLectureObject parses a given JSON object and creates respective instance
	 */
	private static Aula parseLectureObject(JSONObject lecture) {

		String date = (String) lecture.get("data");
		String hour = (String) lecture.get("hora");

		Aula newAula = new Aula(date, hour);

		return newAula;
	}

	/*
	 * import_user_data imports all users from JSON file and saves them into a list
	 */
	static void import_user_data(List<Utilizador> utilizadores, String filename) throws ParseException, IOException {
		JSONParser jsonParser = new JSONParser();

		if (!utilizadores.isEmpty()) {
			System.out.println("Utilizadores já foram importados!");
			System.out.println("Se quiser importar novamente, reinicie o programa.");
			return;
		}
		// Parsing users json file
		try (FileReader reader = new FileReader(USER_DIRECTORY + filename)) {

			// Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray userList = (JSONArray) obj;
			// System.out.println(userList);

			// Iterate over users array

			for (Object user : userList) {
				// Insert each user in users array
				utilizadores.add(parseUserObject((JSONObject) user));
				nUsers++;
			}

		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
	}

	/*
	 * import_classes_data imports all classes/lectures from JSON file and saves
	 * them into a list
	 */
	static void import_classes_data(List<Aula> aulas) {
		JSONParser jsonParser = new JSONParser();

		// Parse lectures file
		try (FileReader reader = new FileReader(LECTURE_DIRECTORY)) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray lectureList = (JSONArray) obj;
			// System.out.println(userList);

			// Iterate over users array
			for (Object lecture : lectureList) {
				// Insert each user in users array
				aulas.add(parseLectureObject((JSONObject) lecture));
			}

			/*
			 * for(Aula aula : aulas) { System.out.println(aula.getData()); }
			 */

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/*
	 * mostrarHorario shows schedule from given list of lectures
	 */
	private static void mostrarHorario(List<Aula> aulas) {
		System.out.println("Horario: ");
		
		for (Aula aula : aulas) {
			if(!aula.isLecionada())
			{
				System.out.println("Aula " + aula.getData() + " as " + aula.getHora());
			}
		}
	}

	
	/*
	 * showClass() displays the entire class, except the teachers
	 */
	private static void showClass() {
		if (utilizadores.isEmpty()) {
			System.out.println("Utilizadores ainda não foram importados.");
			return;
		}

		System.out.println("N_Cartao   |        Nome");
		System.out.println("-----------+-------------------");

		for (Utilizador user : utilizadores) {
			if (user.getPapel().equals("docente")) {
				continue;
			}

			System.out.println("  " + user.getNR_CARTAO() + "      |   " + user.getNome());
			System.out.println("-----------+-------------------");
		}
	}

	/*
	 * initApp1 handles Application 1. Given a class, mark presence to alumni with
	 * card numbers 'X' to exit and mark the lecture as 'lectured'
	 */
	static Aula initApp1(Aula aula) {

		Scanner scanner = new Scanner(System.in); //Removing this causes the system to fail
		Leitor leitor = new Leitor(aula);

		System.out.println("Insira o número do cartão para ler no leitor de cartões:");
		System.out.println("(Numero do cartao no modo xxx, onde x é um número de 0 a 9)");
		System.out.println("Pressione X se pretende terminar o leitor / acabar a aula");

		while (true) {
			String n_cartao = scanner.next();

			if (n_cartao.equals("X")) {
				leitor.resetLeitor(aula);
				aula.setLecionada();
				return aula;
			}

			boolean registered = false;

			for (Utilizador user : utilizadores) {
				if (user.getNR_CARTAO().equals(n_cartao)) {
					leitor.regista(user, aula, mapOfPresencas);
					registered = true;
				}
			}

			if (!registered) {
				System.out.println("Cartão inválido");
			}
		}
	}
}