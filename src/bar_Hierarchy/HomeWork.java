/**
 * 
 */
package bar_Hierarchy;

import java.util.Collections;
import java.util.HashMap;
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
	HashMap<String,Integer> sumTree = new HashMap<String,Integer>();
	
	ValueComparator bvc = new ValueComparator(sumTree);
	
	TreeMap<String,Integer> sorted_map = new TreeMap<String, Integer>(bvc);
	TreeMap<String,Integer> before_sorted_map = new TreeMap<String, Integer>();
	
	Vector<Integer> rectY = new Vector<Integer>();
	Vector<Integer> rectWidth = new Vector<Integer>();
	Vector<String> rectName = new Vector<String>();
	
	boolean rectOver,buttonOver;
	int rectHighlight, buttonHighlight;
	int rectX;
	int rectHeight;
	int rectColor;
	int baseColor;
	int currentColor;
	
	JSONArray curDepth;
	
	public void setup()
	{
		size(1024,860);
		textSize(12);
		background(255);
		
		rectX = 80;
		rectHeight = 32;
		rectHighlight = color(0,0,255);
		rectColor = color(0,0,0);
		rectOver = false;
		
		baseColor = color(245);
		currentColor = baseColor;
		
		String s = FTFile.Read("../flare.json");
		JSONObject obj;
		
		try {
			obj = new JSONObject(s);

			curDepth = obj.getJSONArray("children");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		makeMap(curDepth);
	}

	public void draw()
	{
		for (int i = 0 ; i < sorted_map.size() ; i++) {
			fill(0);
			
			if(mouseY>rectY.get(i) && mouseY<=rectY.get(i)+40 && mouseX <= rectWidth.get(i) && mousePressed == true) {
				nextDepth(i);
				break;
			}
			
			rect(rectX,rectY.get(i)+10,rectWidth.get(i),rectHeight);
			text(rectName.get(i),10,rectY.get(i)+25);
		}
	}
	 
	private void nextDepth(int idx) {
		String keys[] = sorted_map.keySet().toArray(new String[0]);
		
		for (int i = 0; i < curDepth.length() ; i++) {
			try {
				if (curDepth.getJSONObject(i).getString("name") == keys[idx]) {
					makeMap(curDepth.getJSONObject(i).getJSONArray("children"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		background(255);
		redraw();
	}

	private void makeMap(JSONArray arr) {
		sumTree.clear();
		
		for (int i = 0; i < arr.length() ; i++) {
			try {
				FlareData d = new FlareData(arr.getJSONObject(i));
				sumTree.put(d.getObj().getString("name"),d.sum());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		before_sorted_map.putAll(sorted_map);
		
		sorted_map.clear();
		sorted_map.putAll(sumTree);
		
		rectWidth.clear();
		rectName.clear();
		rectY.clear();
		
		rectY.add(0);
		
		final int MIN = Collections.min(sorted_map.values());
		final int MAX = Collections.max(sorted_map.values());
		
		for (Entry<String, Integer> entry : sorted_map.entrySet()) {
			rectWidth.add((int) map(entry.getValue(),MIN,MAX,(width-200)/20,width-200));
			rectName.add(entry.getKey());
			
			rectY.add(rectY.lastElement()+40);
		}
	}
	
}
