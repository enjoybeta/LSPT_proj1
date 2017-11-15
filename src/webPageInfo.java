import java.util.ArrayList;
import java.util.HashMap;

public class webPageInfo {
	String title;
	int titleIndex;
	HashMap<String, String> metadata = new HashMap<String, String>();
	ArrayList<HashMap<String,ArrayList<Integer>>> ngrams = new ArrayList<HashMap<String,ArrayList<Integer>>>();
}
