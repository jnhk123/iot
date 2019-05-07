package common;

import java.util.HashMap;

import org.apache.commons.collections4.map.ListOrderedMap;


public class LowerKey extends ListOrderedMap{
	public Object put(Object key, Object value) {
		return super.put( ( (String)key ).toLowerCase(), value );
	}
}
