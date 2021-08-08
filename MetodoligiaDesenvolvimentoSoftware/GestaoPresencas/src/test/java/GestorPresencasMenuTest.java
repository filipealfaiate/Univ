import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

class GestorPresencasMenuTest {
    
    @Rule
	public ExpectedException exception = ExpectedException.none();
    
    Utilizador tempUser = new Utilizador("Alfa", "43315", "aluno");
	Aula tempAula = new Aula("2020-06-01", "10:00h");
    
    String filename = "dados.json";
	ArrayList<Utilizador> utilizadores = new ArrayList<Utilizador>();
	HashMap<String, ArrayList<Utilizador>> mapOfPresencas = new HashMap<String, ArrayList<Utilizador>>();
    
	@Test
	void testImport_user_data_with_wrong_filename() throws ParseException, IOException {
		
		exception.expect(FileNotFoundException.class);
		
		try {
			
			GestorPresencasMenu.import_user_data(utilizadores, "dadosa.json");
			
			fail("Failed to assert :No exception thrown");
			
		} catch (FileNotFoundException ex) {
		
			assertNotNull("Failed to assert", ex.getMessage());
		}
	}
	
	@Test
	void testInitApp1()
	{
		assertFalse(mapOfPresencas.containsKey(tempAula.getData()), "App 1 didn't start yet for a class to finish");
		
		InputStream sysInBackup = (InputStream) System.in; // backup System.in to restore it later
		
		ByteArrayInputStream in = new ByteArrayInputStream("X".getBytes()); //Set input to X
		
		System.setIn(in);

		assertEquals(tempAula, GestorPresencasMenu.initApp1(tempAula), "Returned class should be given class");
		
		assertTrue(tempAula.isLecionada(), "Lecionada should be true");
		
		//reset System.in to its original
		System.setIn(sysInBackup);
	}

}
