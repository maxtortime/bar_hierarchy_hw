package bar_Hierarchy;

import org.json.JSONException;
import org.json.JSONObject;

import net.foxtail.file.FTFile;

public class JsonTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s = "./flare.json";
		FlareData json = new FlareData(s);
		
		try {
			json.dfs(json.getObject(), 0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0 ; i<json.getVec().size() ; i++)
			try {
				System.out.println(json.getVec().get(i).getString("name"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
