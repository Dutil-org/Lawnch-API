package org.dutil.lawnch.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.dutil.lawnch.model.job.Job;
import org.dutil.lawnch.model.result.Result;
import org.dutil.lawnch.model.task.Task;
import org.dutil.lawnch.model.task.TaskQueueInterface;
import org.dutil.lawnch.system.SessionInterface;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@org.hibernate.annotations.DiscriminatorOptions(force=true)
public abstract class Service<T extends Result> extends Task<T>
{            
    @JsonProperty("result")
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Result.class)
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private T m_result;
    
	protected String m_classLoader;
    
    @Transient
	private List<Task> m_jobs;
    @Transient
    private TaskQueueInterface m_jobQueue;
    @Transient
    private boolean m_jobsPrepared = false;
    
    
    public Service()
    {
    	m_classLoader = "Default";
    	m_jobs = new ArrayList<Task>();
    }    
    
    @Override
    protected void result(T result)
    {
    	m_result = result;
    }
    
    @Override
    public T result()
    {
    	return m_result;
    }
	
	@JsonProperty("jobs")
	public List<Task> jobs()
	{
		return m_jobs;
	}
	
	public List<Task> subTasks()
	{
		return jobs();
	}
	
	public final T execute()
	{
		System.out.println("Service:" + this + "> executing empty core execution method");
		return null;
	}
	
	// TODO: its necessary for the serviceexecutor that the subtaskqueue gets removed after extractions
	// maybe a state system is better
	public TaskQueueInterface extractSubtaskQueue()
    {
		TaskQueueInterface tmp = m_jobQueue;
		m_jobQueue = null;
    	return tmp;
    }
	
	public TaskQueueInterface subtaskQueue()
	{
    	return m_jobQueue;
    }
    
    public void subTaskQueue(TaskQueueInterface queue)
    {
    	m_jobQueue = queue;
    	m_jobs = null;
    }
	
	protected void addJob(Job job)
	{
		System.out.println(descriptor().commonName() + "> adding Job: " + job.descriptor().commonName());
		m_jobs.add(job);
	}
	
	// TODO: its necessary for the serviceexecutor that the jobs get just prepared once
	// maybe a state system is better
	public void prepareSubTasks()
	{
		if(!m_jobsPrepared)
		{
			prepareJobs();
			m_jobsPrepared = true;
		}
	}
	
	protected abstract void prepareJobs();
}
