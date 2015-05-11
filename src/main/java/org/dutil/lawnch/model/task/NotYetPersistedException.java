package org.dutil.lawnch.model.task;

public class NotYetPersistedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Task m_task;
	
	public NotYetPersistedException(Task task)
	{
		m_task = task;
	}
	
	public String getMessage()
	{
		return "Task" + m_task.descriptor().commonName() + "is not yet persisted and has thus no ID";
	}

}
