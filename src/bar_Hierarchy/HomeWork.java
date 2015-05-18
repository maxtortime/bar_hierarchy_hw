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
		
		//눈금 그리기
		Rect MAXr = listHistory.peek().get(0);
		int MAX = MAXr.getMax();
		
		for (int i = 0; i < 6; i++) {
			fill(0);
			int loc = MAXr.r.width+MAXr.r.x;
			text(round((MAX-(MAX/6)*i)/1000)+"K",loc-(loc/6)*i,20);
		}
		
		// 그래프 그리기
		for(Rect r : listHistory.peek()) {
			r.draw(this);
			
			if(r.clicked) clickedRect = r; // 사각형을 눌렀을 떄 어떤 사각형인지 알기 위해서임.
		}
	}
	public void mouseClicked() {
		if(clickedRect!=null && clickedRect.hasChild) { // 사각형 눌렀을 때
			Rect.numberOfRect = -1; // 그려져 있는 사각형의 수 초기화
		
			try {
				JSONArray next = clickedRect.getObj().getJSONArray("children");
				ArrayList<Rect> nextList = new ArrayList<Rect>();
				
				for (int i = 0; i < next.length(); i++)
					nextList.add(new Rect(next,width));
				
				// 다음 층 그래프 그리기
				listHistory.push(nextList); 
				redraw();
			} catch (JSONException e) { e.printStackTrace(); }
			
			//debug 용 출력
			//println("클릭한 사각형 이름: "+clickedRect.name);
			//println("사각형 index: "+clickedRect.index);
			
			clickedRect = null; // 다음에 눌렀을 때 새로운 상태로 초기화하기 위해서
		}
		else if (clickedRect!=null && !clickedRect.hasChild) { // 다음 depth가 없는 막대를 누르면 아무것도 안 하기 위해
			redraw();
			
			println("아무 것도 안 함");
			clickedRect = null; // 다음에 눌렀을 때 새로운 상태로 초기화하기 위해서
		}
		else if (clickedRect==null) { // 배경을 눌렀을 때
			if (listHistory.peek()!=rects) { // 맨 처음에는 뒤로 갈 필요가 없음
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
