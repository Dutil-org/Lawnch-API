package org.dutil.lawnch.system;

public class ObjectNotRegisteredException extends Exception {

	private String m_objectName;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6348863193500866732L;
	
	public ObjectNotRegisteredException(String name)
	{
		m_objectName = name;
	}
	
	@Override
	public String getMessage()
	{
		return "No Object registered under the identifier: " + m_objectName;
	}

}
