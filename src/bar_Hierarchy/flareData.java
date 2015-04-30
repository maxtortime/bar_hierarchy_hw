package bar_Hierarchy;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
	private final String CHILDREN = "children";
	private final String NAME = "name";
	private final String SIZE = "size";
	private Stack<JSONObject> s =  new Stack<JSONObject>();
	private LinkedHashMap <String,Integer> each = new LinkedHashMap<String,Integer>();
	private Vector <JSONObject> nodes = new Vector<JSONObject>();

	
	public FlareData(String fileName)
	{
		jsonFile = FTFile.Read(fileName);
		
		try {
			obj = new JSONObject(jsonFile);
			s.push(obj);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public FlareData(JSONObject object) {
		this.obj = object;
		this.s.push(object);
	}
	

	public int sum() throws JSONException {
		dfs(this.obj);
		
		int sum = 0;
		Iterator<String> it = this.each.keySet().iterator(); // Iterator �� Key���� �̾Ƴ��� 

	    Object obj;
	    while (it.hasNext()) {  // Key�� �̾Ƴ� Iterator �� ��������
	      obj = it.next(); // Key �� �ϳ��� �̾�
	      sum += this.each.get(obj);
	    }
		
		return sum;
	}
	
	private void dfs(JSONObject object) throws JSONException {
		try {
			if(object.has(CHILDREN)) {
				int idx = 0;
				
				JSONArray arr = object.getJSONArray(CHILDREN);
				
				for (int i = 0; i < arr.length() ; i++ ) {
					JSONObject cur = arr.getJSONObject(i);
					String name = cur.getString(NAME);
					
					if (each.containsKey(name)) {
						idx = i+1;
					}
					else if(i == 0 && !each.containsKey(name)) {
						break;
					}
				}
				
				if (idx == arr.length()) { 
					// ���� object�� �迭�� ���� �� ���� �鷶�ٸ� ���� ���� �ö󰡱� ����
					s.pop();
					
					dfs(s.peek());
				}
				else {
					// ���� for ������ �������� ���� ���ÿ� �ְ� �ٽ� dfs ����
					s.push(arr.getJSONObject(idx));
					
					JSONObject cur = s.peek();
					String name = cur.getString(NAME);
					
					if (cur.has(SIZE)) {
						int size = cur.getInt(SIZE);
						this.each.put(name, size);
					}
					else {
						this.each.put(name, 0);
					}
					nodes.addElement(cur);
					dfs(cur);
				}
			}
			else {
				s.pop();
				
				dfs(s.peek());
			}
		}
		catch(EmptyStackException e) {
			// Depth First Search ends...
			// do nothing
		}
	}

	/**
	 * @return the obj
	 */
	public JSONObject getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void changeObj(JSONObject obj) {
		this.obj = obj;
	}

	/**
	 * @return the each
	 * @throws JSONException 
	 */
	public LinkedHashMap<String, Integer> getEach() throws JSONException {
		dfs(this.obj);
		return each;
	}

	/**
	 * @param each the each to set
	 */
	
}
