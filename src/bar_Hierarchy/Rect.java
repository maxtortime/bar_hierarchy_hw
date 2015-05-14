package bar_Hierarchy;
import java.util.Collections;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import processing.core.PApplet;

import java.awt.Rectangle;

public class Rect extends FlareData {
	private final int x,h;
	private int MIN;
	private int MAX;
	private int y = 10;
	
	private float w = 0;

	private String name;
	private TreeMap<String,Integer> nameSum = new TreeMap<String, Integer>();
	private ValueComparator bvc  = new ValueComparator(nameSum);
	private TreeMap<String,Integer> sortedSum =  new TreeMap<String, Integer>(bvc);
	
	private FlareData temp;
	private Integer values[];
	private String keys[];
	private Rectangle r;
	private JSONArray cur,before,next;

	private int index ,winW;
	private boolean hasChild;
	
	boolean flag;
	
	public Rect(org.json.JSONArray arr,int idx,int width) {
		super(arr,idx);

		x = 80;
		h = 32;
		index = idx;
		cur = arr; 
		hasChild = true;
		flag = false;
		next = null;
		before = null;
		winW = width;
		
		y += idx*(h+10);
		
		for (int i = 0 ; i < arr.length() ; i++) {
			try {
				temp = new FlareData(arr.getJSONObject(i));
				
				if (!temp.getObj().has("children"))
					hasChild = false;
				
				name = temp.getObj().getString("name");
				nameSum.put(name, temp.sum());
			} catch (JSONException e) { e.printStackTrace(); }
		}
		
		sortedSum.putAll(nameSum);
		
		MAX = Collections.max(sortedSum.values());
		MIN = Collections.min(sortedSum.values());
		
		values = sortedSum.values().toArray(new Integer[0]);
		keys =  sortedSum.keySet().toArray(new String[0]);
		
		w = PApplet.map(values[idx],MIN,MAX,(width-200)/20,width-200);
		
		r = new Rectangle(x,y,(int) w,h);
	}
	
	void display(PApplet p) {
		
		p.fill(0);
		p.text(keys[index], x-75, (y*2+h)/2);
		
		if(hasChild)
			p.fill(0,0,255);
		else
			p.fill(0);
		
		try {
			if (r.contains(p.mouseX,p.mouseY) && p.mousePressed && cur.getJSONObject(index).has("children")) {
				p.fill(255,0,0);
				flag = true;
			}
			else if (p.mousePressed) {
				p.fill(0,255,0);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		p.rect(r.x,r.y,r.width,r.height);
	}
}
