package org.dutil.lawnch.model.task;

import reactor.rx.broadcast.Broadcaster;

public interface TaskQueueInterface {
	public void setBroadcasters(	Broadcaster<TaskQueueInterface> hasNextTask, 
			Broadcaster<TaskQueueInterface> hasNextQueue, 
			Broadcaster<Task> taskHasFinished,
			Broadcaster<TaskQueueInterface> queueHasFinished);
	public void start();
	public Task nextTask();
	public TaskQueueInterface nextQueue();
	public void enqueue(Task task);
	public void enqueue(TaskQueueInterface taskqueue);
	
	/*
	* inserts a taskqueue before a task, this function is used to resolve a service Job
	* Jobs will be executed before the service
	*/
	public void insertBeforeTask(Task task, TaskQueueInterface taskQueue);
	
	public int size();
	public String toString();
	public String toLeveledString(int currentLevel);

}
