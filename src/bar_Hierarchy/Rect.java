package bar_Hierarchy;
import java.util.Collections;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

import java.awt.Rectangle;

public class Rect {
	private final int x = 80;
	private final int h = 32;
	private int y = 30;
	private int MIN,MAX;
	private TreeMap<String,Integer> nameSum = new TreeMap<String, Integer>();
	private ValueComparator bvc  = new ValueComparator(nameSum);
	private TreeMap<String,Integer> sortedSum =  new TreeMap<String, Integer>(bvc);
	
	private int winW;
	private FlareData temp;
	private Integer values[];
	private String names[];
	private int textNumber;
	
	public float w = 0;
	public String name;
	public Rectangle r;
	public JSONArray cur;
	public JSONObject obj;
	public int index;
	public static int numberOfRect = -1;
	public boolean clicked = false;
	public boolean hasChild = false;
	
	Ani widthAni;
	
	public Rect(JSONArray arr,int width) {
		numberOfRect++;
		cur = arr; 

		y += numberOfRect*(h+10);
		
		winW = width;
		
		try {
			for (int i = 0 ; i < arr.length() ; i++) {
					temp = new FlareData(arr.getJSONObject(i));
					name = temp.getObj().getString("name");
					nameSum.put(name, temp.sum());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		sortedSum.putAll(nameSum);
		
		MAX = Collections.max(sortedSum.values());
		MIN = Collections.min(sortedSum.values());
		
		values = sortedSum.values().toArray(new Integer[0]);
		names =  sortedSum.keySet().toArray(new String[0]);
		
		w = PApplet.map(values[numberOfRect],MIN,MAX,(winW-200)/20,winW-200);
		
		Integer nameSumValues[] = nameSum.values().toArray(new Integer[0]);
		
		for (int i = 0 ; i <nameSumValues.length ; i++) {
			if (values[numberOfRect] == nameSumValues[i]) {
				index = i;
				
				try {
					obj = arr.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (obj.has("children")) {
			hasChild = true;
		}
		
		textNumber = numberOfRect;
		
		name = names[textNumber];
		
		r = new Rectangle(x,y,(int) w,h);
	}
	
	public void draw(PApplet p) {
		p.fill(0);
		
		p.text(name, x-75, (y*2+h)/2);
		
		if(hasChild)
			p.fill(70,130,180);
		else
			p.fill(170,170,170);
		
		if (r.contains(p.mouseX,p.mouseY) && p.mousePressed) {
			p.fill(255,0,0);
			clicked = true;
		}
		else {
			clicked = false;
		}
		p.rect(r.x,r.y,r.width,r.height);
	}
}