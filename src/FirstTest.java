import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class FirstTest {

	@Test
    void junit() {
		assertEquals(0, 0);
		assertNotEquals(0, 1);
	}
	
	@Test
	void receiveIndex() {
		fail("Not yet implemented");
	}
	
	@Test
	void receiveRanking() {
		fail("Not yet implemented");
	}
	
	@Test
    void readJson() {
		parseJson test = new parseJson();
		test.readJSON("tests/text transformation/example0.json");
    }
	
	@Test
    void readEmptyJson() {
		parseJson test = new parseJson();
		test.readJSON("tests/text transformation/nothing.json");
    }
	
	@Test
    void verifyJson() {
		fail("Not yet implemented");
    }
	
	

}
