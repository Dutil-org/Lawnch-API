package org.dutil.lawnch.model.task;


public class TaskNotFoundException extends Exception {

	String m_task;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 127837728829988775L;
	
	public TaskNotFoundException(String task)
	{
		m_task = task;
	}
	
	public String getMessage()
	{
		return "Task not found: " + m_task;
	}

}
