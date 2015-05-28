package org.dutil.lawnch.model.task;

import reactor.rx.Stream;

public interface TaskQueueInterface {
	public enum State 
	{
		CREATED, HAS_NEXT_TASK, HAS_NEXT_QUEUE, RUNNING, FINISHED
	}

	public class StateChange
	{
		public TaskQueueInterface target;
		public State formerState;
		public State newState;
	}		

	public void start();
	public void enqueue(Task task);
	public void enqueue(TaskQueueInterface taskqueue);
	public TaskQueueInterface nextQueue();
	public Task nextTask();
	public Stream<StateChange> stateStream();
	public Stream<Task> readyTaskStream();
	public Stream<TaskQueueInterface> readyQueueStream();
	
	/*
	* inserts a taskqueue before a task, this function is used to resolve a service Job
	* Jobs will be executed before the service
	*/
	public void insertBeforeTask(Task task, TaskQueueInterface taskQueue);
	
	public int size();
	public String toString();
	public String toLeveledString(int currentLevel);
}
