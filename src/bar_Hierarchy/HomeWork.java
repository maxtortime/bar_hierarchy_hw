/**
 * 
 */
package bar_Hierarchy;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;
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
				sum.addElement(d.sum());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sum.sort(Collections.reverseOrder());
	}

	public void draw()
	{
		int y=0;
		
		for(int i=0;i<sum.size();i++)
		{  
			textSize(12);
		
			if(mouseY>y && mouseY<=y+40) 
				fill(0,0,255);
			else
				fill(50);
	
		    float w = map(sum.get(i), 0,500000,10,400);

		    rect(80,y+10,w,32);
		    //text(,10,y+25);
		     
		    y+=40;
		}  
	}	
}
