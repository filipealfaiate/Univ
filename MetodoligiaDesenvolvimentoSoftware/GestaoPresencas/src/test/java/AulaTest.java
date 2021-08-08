import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AulaTest {

	@Test
	void testAulaSettersAndGetters() {
		
		Aula aulaTest = new Aula("foo", "bar");
		
		assertFalse(aulaTest.getData().equals("2020-06-21"), "Date isn't 2020-06-21 yet");
		
		//Set class date to 2020-06-21
		aulaTest.setData("2020-06-21");
		
		assertEquals("2020-06-21", aulaTest.getData(), "Date should now be 2020-06-21");
		
		aulaTest.setHora("10:00h");
		
		assertEquals("10:00h", aulaTest.getHora(), "Hour should be 10:00h");
		
		aulaTest.setLecionada(); //Lecionada = true
		
		assertTrue(aulaTest.lecionada, "Class should be lectured");
		
		assertTrue(aulaTest.getNPresencas() == 0, "This class shouldn't have presences");
		
		aulaTest.addPresenca();
		
		assertFalse(aulaTest.getNPresencas() == 0, "This class should have presences");
	}

}
