/**
 * 
 */
package bar_Hierarchy;

import processing.core.PApplet;
import org.apache.commons.lang3.*;
import java.awt.Color;
/**
 * @author Taehwan
 *
 */
@SuppressWarnings("serial")

public class HomeWork extends PApplet
{
	public HomeWork() {
		int m[] = new int[]{80,160,0,160}; // top right bottom left
		int w = 1280- m[1] - m[3]; // width
		int h = 800 - m[0] - m[2]; // height
		Range<Integer> x = Range.between(0, w);
		Range<Integer> z = Range.between(0x4682B4, 0xAAAAAA);
	}
	
	public void setup() 
	{
		size(960,640);
		background(255,255,255);
	}
	
	public void draw()
	{
		beginShape();
		vertex(30, 20);
		vertex(85, 20);
		vertex(85, 75);
		vertex(30, 75);
		endShape(CLOSE);
	}
	
	
}
