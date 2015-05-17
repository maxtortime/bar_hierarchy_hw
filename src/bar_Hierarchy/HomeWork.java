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

import de.looksgood.ani.Ani;
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
		
		Ani.init(this);
		
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
		
		for(Rect r : listHistory.peek()) {
			r.draw(this);
			
			if(r.clicked) clickedRect = r; // 사각형을 눌렀을 떄 어떤 사각형인지 알기 위해서임.
		}
		
//		Rect MAX;
//		// 눈금 출력
//		if(rects!=null)
//			MAX = rects.get(0); // 가장 큰 사각형
//		else
//			MAX = beRect.get(0); 
//		
//		
//		fill(0);
//		text(0,80,20);
//		
//		for (int i = 0; i < 5; i++) {
//			text((MAX.w-(MAX.w/5)*i)+"K" //천자리로 끊어서 
//					,(MAX.r.width+MAX.r.x)-((MAX.r.width+MAX.r.x)/5)*i,
//					20);
//		}
	}
	public void mouseClicked() {
		if(clickedRect!=null && clickedRect.hasChild) { // 사각형 눌렀을 때
			// do something
			Rect.numberOfRect = -1;
		
			try {
				JSONArray next = clickedRect.obj.getJSONArray("children");
				ArrayList<Rect> nextList = new ArrayList<Rect>();
				
				for (int i = 0; i < next.length(); i++)
					nextList.add(new Rect(next,width));
				
				listHistory.push(nextList);
				println("PUSH");
				redraw();
			} catch (JSONException e) { e.printStackTrace(); }
			
			//debug 용 출력
			println("클릭한 사각형 이름: "+clickedRect.name);
			println("사각형 index: "+clickedRect.index);
			
			clickedRect = null; // 다음에 눌렀을 때 새로운 상태로 초기화하기 위해서
		}
		else if (clickedRect!=null && !clickedRect.hasChild) {
			redraw();
			println("아무 것도 안 함");
			clickedRect = null;
		}
		else if (clickedRect==null) { // 배경을 눌렀을 때
			//do something..
			
			if (listHistory.peek().get(0).cur!=curDepth) { // 처음에는 뒤로 갈 필요가 없음
				listHistory.pop();
				println("POP 위로");
				redraw();
			}
			
			//debug 용 출력
			println("사각형 클릭 안 함");
		}
		redraw();
	}
}
