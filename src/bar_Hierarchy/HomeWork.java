/**
 * 
 */
package bar_Hierarchy;

import java.util.Collections;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import net.foxtail.file.FTFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import processing.core.PApplet;

/**
 * @author Taehwan
 *
 */
@SuppressWarnings("serial")

public class HomeWork extends PApplet
{	
	static Vector<Integer> sum = new Vector<Integer>();
	static TreeMap<Integer,String> sumTree = new TreeMap<Integer,String>(Collections.reverseOrder());
	static TreeMap<Integer,JSONObject> objTree = new TreeMap<Integer,JSONObject>();
	
	public void setup()
	{
		size(640,480);
		String s = FTFile.Read("../flare.json");
		JSONObject obj;
		
		try {
			obj = new JSONObject(s);
		
			JSONArray depth1 = obj.getJSONArray("children");
			
			for (int i = 0; i < depth1.length() ; i++) {
				FlareData d = new FlareData(depth1.getJSONObject(i));
				//sumTree.put(d.sum(), d.getObj().getString("name"));
				objTree.put(d.sum(), d.getObj());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw()
	{
		int y=0;
		
		for(Entry<Integer, JSONObject> entry : objTree.entrySet())
		{  
			textSize(12);
		
			if(mouseY>y && mouseY<=y+40) { 
				fill(0,0,255);
				try {
					for (int i = 0 ; i<entry.getValue().getJSONArray("children").length() ; i++) {
						FlareData d2 = new FlareData(entry.getValue().getJSONArray("children").getJSONObject(i));
											}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 다음 depth ㅢ 합계 출력
			}
			else {
				fill(50);
				// 이전 depth의 합계 출력
			}
				
	
		    float w = map(entry.getKey(), 0,500000,10,400);

		    rect(80,y+10,w,32);
		    
		    try {
				text(entry.getValue().getString("name"),10,y+25);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		    y+=40;
		}  
	}	
}
