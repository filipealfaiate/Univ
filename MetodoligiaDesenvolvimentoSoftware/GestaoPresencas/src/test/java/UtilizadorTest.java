import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilizadorTest {

	@Test
	void testUserSettersAndGetters() throws NoSuchFieldException, IllegalAccessException
	{
		Utilizador testUser = new Utilizador("foo", "bar", "test");
		
		//Name isn't "Alfa" yet
		assertFalse(testUser.getNome().equals("Alfa"), "User name shouldn't be alfa (yet)");
		
		//Set name "Alfa"
		testUser.setNome("Alfa");
		
		assertEquals("Alfa", testUser.getNome(), "User name should be Alfa");
		
		testUser.setNR_CARTAO("43315");
		
		assertEquals("43315", testUser.getNR_CARTAO(), "User card should be 43315");
		
		testUser.setPapel("aluno");
		
		assertEquals("aluno", testUser.getPapel(), "User role should be aluno");
		
		assertFalse(testUser.getPresencas() == 1, "User presence should be 0");
		
		//Add 1 presence
		testUser.addPresenca();
		
		assertEquals(1, testUser.getPresencas(), "User presence should be 1");
	}

}
