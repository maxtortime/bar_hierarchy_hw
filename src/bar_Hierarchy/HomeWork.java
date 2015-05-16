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
	JSONArray curDepth,before;
	JSONObject obj;
	Rect clickedRect,clickedBack;
	int clickWhat;
	int indexOf;
	int x = 25;
	
	ArrayList<Rect> rects,beRect;

	public void setup()
	{
		size(640,480);
		textSize(12);
		background(255);
		
		String s = FTFile.Read("../flare.json");
		
		clickWhat = 0;
		indexOf = 0;
		
		rects =  new ArrayList<Rect>();
		beRect = null;
		clickedRect = null;
		clickedBack = null;
		try {
			obj = new JSONObject(s);

			curDepth = obj.getJSONArray("children");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0 ; i < curDepth.length() ; i++) {
			rects.add(new Rect(curDepth, width));
			
		}
		
	}

	public void draw()
	{

		for(Rect r : rects) {
			r.display(this);
			
			if (r.clicked && r.r.contains(mouseX,mouseY)) {
				clickedRect = r;
			}
			else if (r.clicked){
				clickedBack = r;
			}
		}
	}
	public void mouseClicked() {
		Rect.numberOfRect = -1;
		
		reload(clickedRect);

	}
	
	public void reload(Rect r) {
		if (r==null) { // 막대를 눌렀을 때
			if (!(clickedBack.cur == curDepth)) {
				background(255);
				rects.clear();
				
				rects = beRect;
				clickedBack = null;
				redraw();
			}
		}
		else if (r.state == 1){
			r.clicked = false;
			
			background(255);
			beRect = (ArrayList<Rect>) rects.clone();
			
			rects.clear();

			try {
				println(r.getObj().getString("name"));
				before = r.cur;
				
				JSONArray next = r.getObj().getJSONArray("children");
				for (int i = 0; i < next.length(); i++) {
					rects.add(new Rect(next,width));
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			println(r.state);
			
			clickedRect = null;
			
			redraw();
		}
	}
}
