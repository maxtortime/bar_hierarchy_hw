package bar_Hierarchy;

import java.util.Map;


import java.util.TreeMap;

import net.foxtail.file.FTFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class flareData {
	private final static String s =  FTFile.Read("flare2.json");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			JSONObject file = new JSONObject(s);
			System.out.println(file.getString("children"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
//		try {
//			JSONObject file = new JSONObject(s);
//			JSONArray flareArr =  file.getJSONArray("flare");
//			JSONObject flareObj = flareArr.getJSONObject(0);
//			JSONArray AnalyticsArr =  flareObj.getJSONArray("analytics");
//			JSONObject AnalyticsObj = AnalyticsArr.getJSONObject(0);
//			JSONArray ClusterArr =  AnalyticsObj.getJSONArray("cluster");
//			JSONObject ClusterObj = ClusterArr.getJSONObject(0);
//			
//			System.out.println(flareObj);
//			//System.out.println(ClusterObj.getInt("size"));
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
}
	
	public Map<String, Integer> getMapOfCluster() {
		
		return null;	
	}

}
