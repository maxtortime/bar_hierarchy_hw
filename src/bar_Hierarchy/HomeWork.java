/**
 * 
 */
package bar_Hierarchy;

import processing.core.PApplet;
/**
 * @author Taehwan
 *
 */
@SuppressWarnings("serial")

public class HomeWork extends PApplet
{
	private int m[] = new int[]{80,160,0,160};
	private float x,y;

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
