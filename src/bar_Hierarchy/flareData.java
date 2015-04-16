package bar_Hierarchy;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import net.foxtail.file.FTFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// { <- �ϳ��� ���� ������Ʈ   
// [ �迭 
	
public class FlareData {
	private String jsonFile;
	private JSONObject obj;
	private Stack<JSONObject> dfs = new Stack<JSONObject>();
	
	public FlareData(String fileName)
	{
		jsonFile = FTFile.Read(fileName);
		try {
			obj = new JSONObject(jsonFile);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void calSum(JSONObject obj, String name) throws JSONException 
	{
		int sum = 0;
		
		if(!obj.has("children"))
			sum += dfs.pop().getInt("size");
		else 
		{
			int i = 0;
			
			do {
				dfs.push(obj.getJSONArray("children").getJSONObject(i));
				i++;
			}
			while (obj.getJSONArray("children").getJSONObject(i).getString("name")!=name);
			
			dfs.pop();
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
	
	public int lenArray() // for ������ �����͸� �ޱ� ���� ����� �˾ƾ� �ϹǷ�
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
	
	public int lenArray(int d1) // for ������ �����͸� �ޱ� ���� ����� �˾ƾ� �ϹǷ�
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
	
	public int lenArray(int d1,int d2) // for ������ �����͸� �ޱ� ���� ����� �˾ƾ� �ϹǷ�
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
}