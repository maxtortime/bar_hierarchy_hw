package bar_Hierarchy;

import org.json.JSONException;
import org.json.JSONObject;

import net.foxtail.file.FTFile;

public class JsonTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s = FTFile.Read("./flare.json");
		JSONObject json = null;
		
		try {
			json = new JSONObject(s);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (json.has("name"))
		{
			System.out.println("Oh yeah!");
			System.out.println(json.length());
		}
		else
		{
			System.out.println("...");
		}
	}

}
