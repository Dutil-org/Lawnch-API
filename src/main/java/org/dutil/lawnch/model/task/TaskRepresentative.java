package org.dutil.lawnch.model.task;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.result.Result;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Inheritance
public class TaskRepresentative implements TaskRepresentativeInterface {
		
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long m_id;
	
	@Transient
	protected Task m_task;
	
    @JsonProperty("configuration")
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Result.class)
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.MERGE)
	private Result m_configuration;
    
	@JsonProperty("descriptor")
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Descriptor.class)
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.ALL)
	Descriptor m_descriptor;
	
	private TaskRepresentative()
	{
	}
	
    public static TaskRepresentative create(Descriptor descriptor, Result configuration)
    {
    	TaskRepresentative newDep = new TaskRepresentative();
    	newDep.descriptor(descriptor);
    	newDep.configuration(configuration);
    	return newDep;
    }
	
	@Override
	public void task(Task task)
	{
		m_task = task;
		if(m_configuration != null)
			try 
			{
				m_task.configuration(m_configuration);
			} 
			catch (ConfigurationFailedException e) 
			{
				System.out.println("Service> Configuration Error: ");
				System.out.println("\t" + e.getMessage());
				e.printStackTrace();
			}
	}
	
	@Override
	public Task task()
	{
		return m_task;
	}
	
    @JsonProperty("descriptor")
	public void descriptor(Descriptor descriptor)
	{
    	m_descriptor = descriptor;
	}
	
    @JsonProperty("descriptor")
	public Descriptor descriptor()
	{
    	if(m_task != null)
    		return m_task.descriptor();
		return m_descriptor;
	}
	
	@Override 
	public boolean satisfied()
	{
		return m_task != null;
	}
	
	public void configuration(Result configuration)
	{
		m_configuration = configuration;
		
		if(m_task != null)
			try 
			{
				m_task.configuration(configuration);
			} 
			catch (ConfigurationFailedException e) 
			{
				System.out.println("TaskRepresentative> Configuration Error: ");
				System.out.println("\t" + e.getMessage());
				e.printStackTrace();
			}
	}
	
	public Result configuration()
	{
		if(m_task != null)
			return m_task.configuration();
		return m_configuration;
	}
}
