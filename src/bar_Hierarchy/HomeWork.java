/**
 * 
 */
package bar_Hierarchy;

import java.util.ArrayList;
import java.util.Stack;

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
	Rect clickedRect;
	int clickWhat;
	int indexOf;
	int x = 25;
	
	Stack<ArrayList<Rect>> listHistory;
	ArrayList<Rect> rects;

	public void setup()
	{
		size(640,480);
		noStroke();
		textSize(12);
		background(255);
		
		String s = FTFile.Read("../flare.json");
		
		clickWhat = 0;
		indexOf = 0;
		
		rects =  new ArrayList<Rect>();
		clickedRect = null;
		
		listHistory = new Stack<ArrayList<Rect>>();
		
		
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
		
		listHistory.push(rects);
	}

	public void draw()
	{
		background(255);
		
		//���� �׸���
		Rect MAXr = listHistory.peek().get(0);
		int MAX = MAXr.getMax();
		
		for (int i = 0; i < 6; i++) {
			fill(0);
			int loc = MAXr.r.width+MAXr.r.x;
			text(round((MAX-(MAX/6)*i)/1000)+"K",loc-(loc/6)*i,20);
		}
		
		// �׷��� �׸���
		for(Rect r : listHistory.peek()) {
			r.draw(this);
			
			if(r.clicked) clickedRect = r; // �簢���� ������ �� � �簢������ �˱� ���ؼ���.
		}
	}
	public void mouseClicked() {
		if(clickedRect!=null && clickedRect.hasChild) { // �簢�� ������ ��
			Rect.numberOfRect = -1; // �׷��� �ִ� �簢���� �� �ʱ�ȭ
		
			try {
				JSONArray next = clickedRect.getObj().getJSONArray("children");
				ArrayList<Rect> nextList = new ArrayList<Rect>();
				
				for (int i = 0; i < next.length(); i++)
					nextList.add(new Rect(next,width));
				
				// ���� �� �׷��� �׸���
				listHistory.push(nextList); 
				redraw();
			} catch (JSONException e) { e.printStackTrace(); }
			
			//debug �� ���
			//println("Ŭ���� �簢�� �̸�: "+clickedRect.name);
			//println("�簢�� index: "+clickedRect.index);
			
			clickedRect = null; // ������ ������ �� ���ο� ���·� �ʱ�ȭ�ϱ� ���ؼ�
		}
		else if (clickedRect!=null && !clickedRect.hasChild) { // ���� depth�� ���� ���븦 ������ �ƹ��͵� �� �ϱ� ����
			redraw();
			
			println("�ƹ� �͵� �� ��");
			clickedRect = null; // ������ ������ �� ���ο� ���·� �ʱ�ȭ�ϱ� ���ؼ�
		}
		else if (clickedRect==null) { // ����� ������ ��
			if (listHistory.peek()!=rects) { // �� ó������ �ڷ� �� �ʿ䰡 ����
				listHistory.pop();
				redraw();
			}
		}
		redraw();
	}
	
	public static void main(String args[]) {
		 PApplet.main("HomeWork");
	}
}
