import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class helloworld {

	void readJSON(String fileName) {
		File file = new File(fileName);
		String jsonStr = null;
		try {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\\Z");
			jsonStr = scanner.next();
			scanner.close();			
		} catch (FileNotFoundException e) {
			System.err.println("ERROR");
			e.printStackTrace();
			return;
		}
		try {
            JSONObject jsonObject = new JSONObject(jsonStr); // Parse the JSON to a JSONObject
            Iterator<?> keys = jsonObject.keys();
            while(keys.hasNext()) {
            	String key = (String) keys.next();
                System.out.println(jsonObject.get(key));
            }
            
        } catch (JSONException e) {
        	System.err.println("ERROR");
            e.printStackTrace();// JSON Parsing error
            return;
        }
	}
}
