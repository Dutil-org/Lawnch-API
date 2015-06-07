package org.dutil.lawnch.model.task;

import org.dutil.lawnch.model.descriptor.Descriptor;

public class TaskNotFoundException extends Exception {

	Descriptor m_task;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 127837728829988775L;
	
	public TaskNotFoundException(Descriptor task)
	{
		m_task = task;
	}
	
	public String getMessage()
	{
		return "Task not found: " + m_task;
	}

}
