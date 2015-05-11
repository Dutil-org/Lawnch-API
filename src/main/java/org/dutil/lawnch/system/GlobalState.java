package org.dutil.lawnch.system;

import java.util.HashMap;

public class GlobalState {
	
	static HashMap<String, Object> m_container = new HashMap<String, Object>();
	
	//TODO: raise exception if not found
	public static <T> T get(String key) throws ObjectNotRegisteredException
	{
		if(!m_container.containsKey(key))
			throw new ObjectNotRegisteredException(key);
		return (T) m_container.get(key);
	}
	
	public static void set(String key, Object value)
	{
		m_container.put(key, value);
	}
}
