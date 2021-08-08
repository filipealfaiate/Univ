import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class LeitorTest {

	Aula tempAula = new Aula("2020-06-01", "10:00h");

	Utilizador testUser = new Utilizador("Alfa", "43315", "aluno");
	Utilizador testDoc = new Utilizador("Duckinson", "00000", "docente");

	Utilizador testUserNull = null;
	Aula testAulaNull = null;

	HashMap<String, ArrayList<Utilizador>> mapOfPresencas = new HashMap<String, ArrayList<Utilizador>>();

	Leitor testLeitor = new Leitor(tempAula);

	@Test
	void testIfUserEntersAndIsInTemporaryArray() {

		assertFalse(testLeitor.verificaPresenca(testUser), "User wasn't inserted yet");

		testLeitor.temp.add(testUser);

		assertFalse(!testLeitor.verificaPresenca(testUser), "User was already inserted");
	}

	@Test
	void testRegistaNullValues() {

		assertThrows(NullPointerException.class, () -> {

			testLeitor.regista(testUserNull, testAulaNull, mapOfPresencas);
		});

	}

	@Test
	void testRegista() {
		assertFalse(testLeitor.verificaPresenca(testUser), "User wasn't inserted yet");
		
		testLeitor.regista(testUser, tempAula, mapOfPresencas);
		
		assertTrue(testLeitor.verificaPresenca(testUser), "User was already inserted");
	}

	@Test
	void testIfLeitorIsResetCorrectly() {
		testLeitor.regista(testDoc, tempAula, mapOfPresencas);

		testLeitor.resetLeitor(tempAula);

		assertEquals(1, GestorPresencasMenu.aulasN, "Aula wasn't lectured");

		assertTrue(tempAula.lecionada, "Aula should already be lectured");
	}
}
