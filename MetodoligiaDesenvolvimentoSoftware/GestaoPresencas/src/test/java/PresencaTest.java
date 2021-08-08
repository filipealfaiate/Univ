import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

class PresencaTest {

	Utilizador tempUser = new Utilizador("Alfa", "43315", "aluno");
	Aula tempAula = new Aula("2020-06-01", "10:00h");
	Presenca testPresenca = new Presenca(tempUser, tempAula);

	HashMap<String, ArrayList<Utilizador>> mapOfPresencas = new HashMap<String, ArrayList<Utilizador>>();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	void testPresencaSettersAndGetters() {
		assertEquals(tempUser, testPresenca.getUser(), "Presence should be to tempUser");
		assertEquals(tempAula, testPresenca.getAula(), "Presence should be to tempAula");
	}

	@Test
	void testMarcaPresencaFromNullData() {
		// User and class won't be null because of Leitor.regista
		exception.expect(NullPointerException.class);

		Utilizador nullUser = null;
		Aula nullAula = null;

		try {

			testPresenca.marcaPresenca(nullUser, nullAula, mapOfPresencas);

			fail("Failed to assert :No exception thrown");

		} catch (NullPointerException ex) {

			assertNotNull("Failed to assert", ex.getMessage());
		}
	}

	@Test
	void testMarcaPresenca() {
		assertFalse(mapOfPresencas.containsKey(tempAula.getData()), "Map should not have the key aula");

		testPresenca.marcaPresenca(tempUser, tempAula, mapOfPresencas);

		assertFalse(!mapOfPresencas.containsKey(tempAula.getData()), "Map should not have the key aula");
	}

	@Test
	void testAlterarPresenca() {
		assertFalse(mapOfPresencas.containsKey(tempAula.getData()), "Map shouldn't contain key aula");

		testPresenca.alterarPresenca(tempUser, tempAula, mapOfPresencas);

		assertFalse(!mapOfPresencas.containsKey(tempAula.getData()), "Map should contain key aula");
	}

}
