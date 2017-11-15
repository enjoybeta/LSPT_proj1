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
    void readJSON() {
		helloworld test = new helloworld();
		test.readJSON("tests/text transformation/1.json");
    }
	
	@Test
    void verifyJSON() {
		fail("Not yet implemented");
    }
	
	

}
