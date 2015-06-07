package org.dutil.lawnch.model.task;

import org.dutil.lawnch.model.descriptor.Descriptor;

public class AllreadyRegisteredException extends Exception {

	Descriptor m_task;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3388131139249749925L;
	
	public AllreadyRegisteredException(Descriptor task)
	{
		m_task = task;
	}
	
	public String getMessage()
	{
		return "Allready registered: " + m_task;
	}

}
