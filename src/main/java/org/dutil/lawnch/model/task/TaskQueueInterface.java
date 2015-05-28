package org.dutil.lawnch.model.task;

import reactor.rx.broadcast.Broadcaster;

public interface TaskQueueInterface {
	public void start();
	public Task nextTask();
	public TaskQueueInterface nextQueue();
	public void enqueue(Task task);
	public void enqueue(TaskQueueInterface taskqueue);
	public Broadcaster<TaskQueueInterface> finishedBroadcaster();
	public Broadcaster<TaskQueueInterface> nextTaskBroadcaster();
	public Broadcaster<TaskQueueInterface> nextQueueBroadcaster();
	
	/*
	* inserts a taskqueue before a task, this function is used to resolve a service Job
	* Jobs will be executed before the service
	*/
	public void insertBeforeTask(Task task, TaskQueueInterface taskQueue);
	
	public int size();
	public String toString();
	public String toLeveledString(int currentLevel);
}
