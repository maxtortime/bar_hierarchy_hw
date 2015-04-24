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
	
	public void setup()
	{
		size(640,480);
		String s = FTFile.Read("../flare.json");
		JSONObject obj;
		
		try {
			obj = new JSONObject(s);
		
			JSONArray depth1 = obj.getJSONArray("children").getJSONObject(0).getJSONArray("children");
			
			for (int i = 0; i < depth1.length() ; i++) {
				FlareData d = new FlareData(depth1.getJSONObject(i));
				sumTree.put(d.sum(), d.getObj().getString("name"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw()
	{
		int y=0;
		
		for(Entry<Integer,String> entry : sumTree.entrySet())
		{  
			textSize(12);
		
			if(mouseY>y && mouseY<=y+40) { 
				fill(0,0,255);
				// ���� depth �� �հ� ���
			}
			else {
				fill(50);
				// ���� depth�� �հ� ���
			}
				
	
		    float w = map(entry.getKey(), 0,500000,10,400);

		    rect(80,y+10,w,32);
		    text(entry.getValue(),10,y+25);
		     
		    y+=40;
		}  
	}	
}
