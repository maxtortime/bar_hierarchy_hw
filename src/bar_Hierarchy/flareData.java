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

// { <- 하나의 단일 오브젝트   
// [ 배열 
	
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
		Iterator<String> it = this.each.keySet().iterator(); // Iterator 로 Key들을 뽑아낸다 

	    Object obj;
	    while (it.hasNext()) {  // Key를 뽑아낸 Iterator 를 돌려가며
	      obj = it.next(); // Key 를 하나씩 뽑아
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
					// 만약 object의 배열을 전부 한 번씩 들렀다면 상위 노드로 올라가기 위해
					s.pop();
					
					dfs(s.peek());
				}
				else {
					// 위의 for 문에서 결정해준 값을 스택에 넣고 다시 dfs 수행
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
