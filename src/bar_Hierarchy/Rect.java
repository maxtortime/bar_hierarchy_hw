package bar_Hierarchy;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import processing.core.PApplet;
import seltar.motion.*;

public class Rect {
	private final int x = 150;
	public final int h = 32;
	private int y = 30;
	private int MIN,MAX;
	
	private LinkedHashMap<String,Integer> sum = new LinkedHashMap<String, Integer>(); 
	// TreeMap이 put할 때 이상하게 정렬되서 LinkedHashMap으로 함.
	private ValueComparator bvc  = new ValueComparator(sum);
	private TreeMap<String,Integer> sortedSum =  new TreeMap<String, Integer>(bvc);
	private int winW;
	private FlareData temp;	
	private Integer values[];
	private String names[];
	private int textNumber;
	
	private float w = 0;
	private String name;
	public Rectangle r;
	
	private JSONObject obj;
	private int index;
	
	public static int numberOfRect = -1;
	public boolean clicked = false;
	public boolean hasChild = false;
	
	Motion m;

	public Rect(JSONArray arr,int width) {
		numberOfRect++;
		
		y += numberOfRect*(h+10);
		
		winW = width;
		
		try {
			for (int i = 0 ; i < arr.length() ; i++) {
					temp = new FlareData(arr.getJSONObject(i));
					name = temp.getObj().getString("name");

					sum.put(name, temp.sum());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		sortedSum.putAll(sum);
		
		MAX = Collections.max(sortedSum.values());
		
		if (sum.size() != 1) // 원소가 1개이면 나중에 MIN과 MAX가 같아져서 w가 0이되므로
			MIN = Collections.min(sortedSum.values());
		else
			MIN = 0;
		
		values = sortedSum.values().toArray(new Integer[0]);
		names =  sortedSum.keySet().toArray(new String[0]);
		
		w = PApplet.map(values[numberOfRect],MIN,MAX,(winW-200)/20,winW-200);
		
		Integer nameSumValues[] = sum.values().toArray(new Integer[0]);
		
		for (int i = 0 ; i <nameSumValues.length ; i++) {
			if (values[numberOfRect] == nameSumValues[i]) {
				index = i;
				
				try {
					obj = arr.getJSONObject(index);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (obj.has("children")) hasChild = true;
		else hasChild = false;
		
		textNumber = numberOfRect;
		name = names[textNumber];
		
		r = new Rectangle(x,y,(int) w,h);
		
		m = new Motion(0,y);
	}
	
	public void draw(PApplet p) {
		move();
		p.fill(0);
		
		p.text(name, x-125, (y*2+h)/2);
		
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
		//p.rect(r.x,r.y,r.width,r.height);
		p.rect(r.x,r.y,m.getX(),r.height);
	}

	public JSONObject getObj() {
		// TODO Auto-generated method stub
		return obj;
	}
	
	public int getMax() {
		return MAX;
	}
	
	public void move() {
		m.followTo(r.width, r.y);
		m.move();
	}
}