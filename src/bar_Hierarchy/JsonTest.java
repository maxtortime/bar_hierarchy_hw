package bar_Hierarchy;

import org.json.JSONException;
import org.json.JSONObject;

import net.foxtail.file.FTFile;

public class JsonTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s = "./newFlare.json";
		FlareData json = new FlareData(s);
		json.sumStack.push(json.getObject());
		
		System.out.println(json.sum);
		try {
			json.dfs(json.getObject());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
