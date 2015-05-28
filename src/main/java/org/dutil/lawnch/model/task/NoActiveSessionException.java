package org.dutil.lawnch.model.task;

public class NoActiveSessionException extends Exception {

	Task m_task;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -668071831918103013L;
	
	public NoActiveSessionException(Task task)
	{
		m_task = task;
	}
	
	public String getMessage()
	{
		return "No active Session defined for Task: " + m_task;
	}

}
