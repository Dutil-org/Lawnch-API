package org.dutil.lawnch.model.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.result.Result;

import reactor.rx.broadcast.Broadcaster;

import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public abstract class Task <T extends Result> implements Runnable, Describable, Configurable{
	
	@JsonProperty("id")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long m_id;
	
    @JsonProperty("descriptor")
    @Transient
    protected Descriptor m_descriptor;
	
	@JsonProperty("state")
	private float m_state;
    
    @JsonProperty("configuration")
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Result.class)
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Result m_configuration;
    
    @OneToMany(targetEntity=TaskRepresentative.class, fetch = FetchType.EAGER)
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<TaskRepresentative> m_dependencies;
    
    @Transient
    Broadcaster<Task> m_hasFinished;
    @Transient 
    List<String> m_requirements;
	
	public abstract T result();
	public abstract void execute();
	
	public Task()
	{
    	m_descriptor = new Descriptor((Class<Task>)this.getClass());
    	m_descriptor.commonName(this.getClass().getName());
    	m_dependencies = new Vector<TaskRepresentative>();
	}
	
	public void run()
	{
		m_state = 0f;
		execute();
		m_state = 1f;
		finished();
	}
	
	public void setBroadcasters(Broadcaster<Task> hasFinished)
	{
		System.out.println("Task:" + this + "> Setting Broadcaster");
		m_hasFinished = hasFinished;
	}
	
	@JsonProperty("id")
	public long id()
	{
		return m_id;
	}
	
	@JsonProperty("id")
	public void id(long id)
	{
		m_id = id;
	}
	
	@JsonProperty("state")
	public float state()
	{
		return m_state;
	}
	
	@JsonProperty("state")
	public void state(float state)
	{
		m_state = state;
	}
	
	@JsonProperty("descriptor")
	public Descriptor descriptor()
	{
		return m_descriptor;
	}
	
	private final void configured()
	{
		setDependencies();
	}
	
    @JsonProperty("configuration")
    public Result configuration()
    {
    	return m_configuration;
    }
    
    @JsonProperty("configuration")
    public void configuration(Result configuration) throws ConfigurationFailedException
    {
    	m_configuration = configuration;
    	checkConfiguration();
    	   		
    	configured();
    }  
    
    public void requireConfiguration(String requirement)
    {
    	lazyInitializeRequirements();
    	m_requirements.add(requirement);
    }
    
    public void checkConfiguration() throws ConfigurationFailedException
    {
    	if(m_requirements == null)
    		return;
    	if(configuration() == null)
    		throw new ConfigurationFailedException(null);
    	for(String requirement: m_requirements)
    	{
    		if(configuration().value(requirement) == null)
    			throw new ConfigurationFailedException(requirement);
    	}
    }
    
    public void prepareSubTasks()
    {
    	
    }
    
    public List<Task> subTasks()
    {
    	return null;
    }
    
    public TaskQueueInterface extractSubtaskQueue()
    {
    	return null;
    }
    
    public TaskQueueInterface subTaskQueue()
    {
    	return null;
    }   
    
    public void subTaskQueue(TaskQueueInterface queue)
    {
    	
    }
    
    private void lazyInitializeRequirements()
    {
    	if(m_requirements == null)
    		m_requirements = new ArrayList<String>();
    }
	
	@JsonProperty("dependencies")
	public List<TaskRepresentative> dependencies()
	{
		return m_dependencies;
	}
	
	protected void addDependency(String serviceIdentifier, String serviceProviderIdentifier)
	{
		addDependency(serviceIdentifier, serviceProviderIdentifier, configuration());
	}
	
	protected void addDependency(String serviceIdentifier, String serviceProviderIdentifier, Result configuration)
	{
		Descriptor descriptor = new Descriptor();
		descriptor.identifier(serviceIdentifier);
		descriptor.providerIdentifier(serviceProviderIdentifier);
		addDependency(descriptor, configuration);
	}
	
	protected void addDependency(Descriptor descriptor, Result configuration)
	{
		m_dependencies.add(TaskRepresentative.create(descriptor, configuration));
	}
	
	protected void setDependencies()
	{
		
	}
	
	private void finished()
	{
		System.out.println("TaskExecutor:" + this + "> notifying finished");
		m_hasFinished.onNext(this);
	}
	
	public String eventTracker() throws NotYetPersistedException
	{
		if(id() == 0)
			throw new NotYetPersistedException(this);
		return descriptor().identifier() + ":" + id();
	}
	
	public String toString()
	{
		return descriptor().commonName() + ":" + id();
	}
}
