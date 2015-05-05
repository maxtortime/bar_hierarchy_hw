package bar_Hierarchy;

import org.json.JSONException;

public class JsonTest {

	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub
		
		String s = "./flare.json";
		FlareData json = new FlareData(s);
		FlareData j = new FlareData(json.getObj().getJSONArray("children").getJSONObject(1).getJSONArray("children").getJSONObject(0));
		System.out.println(j.sum());
		
	}

}
