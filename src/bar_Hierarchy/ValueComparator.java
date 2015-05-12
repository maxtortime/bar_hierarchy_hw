package bar_Hierarchy;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<String> {
	Map<String,Integer> base;
	
	public ValueComparator(Map<String,Integer> base) {
		// TODO Auto-generated constructor stub
		this.base = base;
		
	}
	
	@Override
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
	}

}
