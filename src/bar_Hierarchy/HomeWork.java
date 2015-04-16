/**
 * 
 */
package bar_Hierarchy;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

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
	static FlareData json = new FlareData("../flare.json");
	
	
	Integer[] sizeEach =  new Integer[json.lenArray()];
	HashMap<Integer,String> map = new HashMap<Integer,String>();
	
	public void setup()
	{
		size(960,480);
		
		for (int i = 0 ; i<json.lenArray() ; i++)
		{
			sizeEach[i] = json.sum(i);
			
			try {
				map.put(sizeEach[i],json.getObject(i).getString("name"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		Arrays.sort(sizeEach, Collections.reverseOrder());
		
	}

	public void draw()
	{
		int y=0;
		
		for(int i=0;i<sizeEach.length;i++)
		{  
			textSize(12);
			
			if(mouseY>y && mouseY<=y+40) 
				fill(0,0,255);
			else
				fill(50);
			
		    float w = map(sizeEach[i], 4000,450000,80,width);

		    rect(80,y+10,w,32);
		    text(map.get(sizeEach[i]),10,y+25);
		     
		    y+=40;
		}  
	}	
}
