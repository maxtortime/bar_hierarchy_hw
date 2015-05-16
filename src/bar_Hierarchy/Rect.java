package bar_Hierarchy;
import java.util.Collections;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import processing.core.PApplet;

import java.awt.Rectangle;

public class Rect {
	private final int x,h;
	private int MIN;
	private int MAX;
	private int y = 10;
	
	private float w = 0;

	private String name;
	private TreeMap<String,Integer> nameSum = new TreeMap<String, Integer>();
	private ValueComparator bvc  = new ValueComparator(nameSum);
	private TreeMap<String,Integer> sortedSum =  new TreeMap<String, Integer>(bvc);
	private boolean hasChild;
	private int winW;
	
	private FlareData temp;
	private Integer values[];
	private String names[];
	public Rectangle r;
	
	public JSONArray cur;
	public JSONObject obj;

	public int index;
	private int textNumber;
	
	static public int numberOfRect = -1;
	
	int state; // 0이 평상시 1이 클릭 2가 배경을 눌렀을 때
	boolean clicked;
	
	public Rect(JSONArray arr,int width) {
		numberOfRect++;
		x = 80;
		h = 32;
		cur = arr; 
		hasChild = true;
		clicked = false;
		y += numberOfRect*(h+10);
		
		winW = width;
		
		state = 0;
		
		try {
			for (int i = 0 ; i < arr.length() ; i++) {
					temp = new FlareData(arr.getJSONObject(i));
					
					if (!temp.getObj().has("children"))
						hasChild = false;
					
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
		
		textNumber = numberOfRect;
		
		r = new Rectangle(x,y,(int) w,h);
	}
	
	public void display(PApplet p) {
		p.fill(0);
		
		p.text(names[textNumber], x-75, (y*2+h)/2);
		
		//PApplet.println("number: "+index);
		
		if(hasChild)
			p.fill(0,0,255);
		else
			p.fill(0);
		
		try {
			if (r.contains(p.mouseX,p.mouseY) && p.mousePressed && cur.getJSONObject(index).has("children")) {
				p.fill(255,0,0);
				clicked = true;
				state = 1;
			}
			else if (p.mousePressed) {
				p.fill(0,255,0);
				clicked = true;
				//state = 2;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		p.rect(r.x,r.y,r.width,r.height);
	}
	
	public JSONObject getObj() {
		return obj;
	}
}