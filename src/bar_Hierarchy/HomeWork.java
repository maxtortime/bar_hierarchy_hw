/**
 * 
 */
package bar_Hierarchy;

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
	static Vector<Integer> sum = new Vector<Integer>();
	static HashMap<String,Integer> sumTree = new HashMap<String,Integer>();
	static TreeMap<String, JSONObject> objTree = new TreeMap<String,JSONObject>();
	static ValueComparator bvc = new ValueComparator(sumTree);
	static TreeMap<String,Integer> sorted_map = new TreeMap<String, Integer>(bvc);
	
	boolean rectOver,buttonOver;
	int rectHighlight, buttonHighlight;
	int rectX;
	Vector<Integer> rectY = new Vector<Integer>();
	Vector<Integer> rectWidth = new Vector<Integer>();
	Vector<String> rectName = new Vector<String>();
	int rectHeight;
	int rectColor;
	int baseColor;
	int currentColor;
	
	JSONArray depth1;
	
	public void setup()
	{
		size(640,480);
		textSize(12);

		rectX = 80;
		rectY.add(0);
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
		
			depth1 = obj.getJSONArray("children");
			
			for (int i = 0; i < depth1.length() ; i++) {
				FlareData d = new FlareData(depth1.getJSONObject(i));
				sumTree.put(d.getObj().getString("name"),d.sum());
				objTree.put(d.getObj().getString("name"),d.getObj());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sorted_map.putAll(sumTree);
		
		for (Entry<String, Integer> entry : sorted_map.entrySet()) {
			rectWidth.add((int) map(entry.getValue(),0,500000,10,400));
			rectName.add(entry.getKey());
			
			rectY.add(rectY.lastElement()+40);
		}
		
		noLoop();
	}

	public void draw()
	{
		
		for (int i = 0 ; i < sorted_map.size() ; i++) {
			if(mouseY>rectY.get(i) && mouseY<=rectY.get(i)+40 && mouseX <= rectWidth.get(i)) {
				
				fill(255,40,40);
			}
			else {
				fill(50);
			}
			rect(rectX,rectY.get(i)+10,rectWidth.get(i),rectHeight);
			text(rectName.get(i),10,rectY.get(i)+25);
		}
	}
	
	public void mousePressed() {
		  loop();  // Holding down the mouse activates looping
		}
	
	public void mouseReleased() {
		  noLoop();  // Releasing the mouse stops looping draw()
		}
}
