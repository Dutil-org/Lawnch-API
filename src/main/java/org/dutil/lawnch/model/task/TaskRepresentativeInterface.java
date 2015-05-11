package org.dutil.lawnch.model.task;


public interface TaskRepresentativeInterface {
	void task(Task task);
	Task task();
	boolean satisfied();
}
