/**
 * 
 */
package bar_Hierarchy;

import java.util.ArrayList;

import net.foxtail.file.FTFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import processing.core.PApplet;

@SuppressWarnings("serial")

public class HomeWork extends PApplet
{	

	JSONArray curDepth;
	JSONObject obj;
	int idx;
	ArrayList<Rect> rects;
	int a;
	public void setup()
	{
		size(640,480);
		textSize(12);
		background(255);
		
		String s = FTFile.Read("../flare.json");
		
		rects =  new ArrayList<Rect>();
		
		try {
			obj = new JSONObject(s);

			curDepth = obj.getJSONArray("children");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0 ; i < curDepth.length() ; i++) {
			rects.add(new Rect(curDepth, i, width));
		}
		
		idx = 0;
		a= 0;
	}

	public void draw()
	{
		idx = -1;
		
		for (int i = 0 ; i< rects.size() ; i++) {
			rects.get(i).display(this);
		}
	}
	
	public void mousePressed() {
		redraw();
		
	
	}
	
	private JSONArray nextDraw(JSONArray nD) {
		rects.clear();
		
		for (int i = 0 ; i < nD.length() ; i++) {
			rects.add(new Rect(nD, i, width));
		}
		
		return nD;
		
	}
}
