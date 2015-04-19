package bar_Hierarchy;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import net.foxtail.file.FTFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// { <- 하나의 단일 오브젝트   
// [ 배열 
	
public class FlareData {
	private String jsonFile;
	private JSONObject obj;
	private Vector<JSONObject> allObject = new Vector<JSONObject>();
	private static final String CHILDREN = "children";
	private static final String NAME = "name";
	private static final String SIZE = "size";
	public Stack<JSONObject> sumStack =  new Stack<JSONObject>();
	public int sum = 0;
	private HashMap <String,Boolean> visited = new HashMap<String,Boolean>();
	private int idx = 0;
	
	public FlareData(String fileName)
	{
		jsonFile = FTFile.Read(fileName);
		visited.put("flare", false);
		try {
			obj = new JSONObject(jsonFile);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void dfs(JSONObject object) throws JSONException {
		if(sumStack.peek() != object)
			sumStack.push(object).getString(NAME);
		
		if(object.has(CHILDREN)) {
			JSONArray arr = object.getJSONArray(CHILDREN);
			
			for (int i = 0; i<arr.length() ; i++) {
				String curName = arr.getJSONObject(i).getString(NAME);
				
				if(visited.containsKey(curName)) {
					if(visited.get(curName)) {
						this.idx=i+1;
					}
				}
			}
			
			if (this.idx == arr.length()) {
				JSONObject up = sumStack.pop();
				System.out.println(up.getString(NAME));
				dfs(sumStack.pop());
			}
			
			JSONObject down = arr.getJSONObject(idx);
			visited.put(down.getString(NAME), true);
			dfs(down);
		}
		else {
			JSONObject poped= sumStack.pop();
			System.out.println("pop "+poped.getString(NAME));
			
			this.sum+=poped.getInt(SIZE);
			
			visited.put(poped.getString(NAME),true);
			
			dfs(sumStack.peek());		
		}
	}
	
	public int sum(int d1)
	{
		int sum = 0;
		
		for (int i = 0 ; i < this.lenArray(d1) ; i++)
			for(int j = 0 ; j < this.lenArray(d1, i) ; j++)
				try {
					sum += this.getObject(d1, i, j).getInt("size");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		return sum;
	}
	
	public JSONObject get(int d) throws JSONException
	{
		
		if (d==0)
			return obj;
		else 
			return get(d-1).getJSONArray("children").getJSONObject(d);
	}
	
	
	public JSONObject getObject(int d1,int d2, int d3)
	{
		JSONObject result = new JSONObject();
		
		try {
			result = obj.getJSONArray("children").getJSONObject(d1).getJSONArray("children").getJSONObject(d2).getJSONArray("children").getJSONObject(d3);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public JSONObject getObject(int d1,int d2)
	{
		JSONObject result = new JSONObject();
		
		try {
			result = obj.getJSONArray("children").getJSONObject(d1).getJSONArray("children").getJSONObject(d2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return result;
	}
	
	public JSONObject getObject(int d1)
	{
		JSONObject result = new JSONObject();
		
		try {
			result = obj.getJSONArray("children").getJSONObject(d1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject getObject()
	{
		//to get upper object
		return obj;
	}
	
	public int lenArray() // for 문으로 데이터를 받기 위해 사이즈르 알아야 하므로
	{
		int length = 0;
		try {
			length = obj.getJSONArray("children").length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return length;
	}
	
	public int lenArray(int d1) // for 문으로 데이터를 받기 위해 사이즈르 알아야 하므로
	{
		int length = 0;
		try {
			length = obj.getJSONArray("children").getJSONObject(d1)
					.getJSONArray("children").length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return length;
	}
	
	public int lenArray(int d1,int d2) // for 문으로 데이터를 받기 위해 사이즈르 알아야 하므로
	{
		int length = 0;
		try {
			length = obj.getJSONArray("children").getJSONObject(d1)
					.getJSONArray("children").getJSONObject(d2)
					.getJSONArray("children").length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return length;
	}

	public Vector<JSONObject> getVec() {
		// TODO Auto-generated method stub
		return allObject;
	}
}
