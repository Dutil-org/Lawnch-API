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
import org.dutil.lawnch.system.GlobalState;
import org.dutil.lawnch.system.ObjectNotRegisteredException;
import org.dutil.lawnch.system.SessionInterface;

import reactor.Environment;
import reactor.rx.Stream;
import reactor.rx.broadcast.Broadcaster;
import reactor.core.processor.RingBufferProcessor;
import reactor.fn.Consumer;

import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public abstract class Task <T extends Result> implements Runnable, Describable, Configurable{
	
	public enum State 
	{
		CREATED, CONFIGURED, DEPENDENCIES_RESOLUTED, SUBTASKS_CREATED, SUBTASKLIST_CREATED, SUBTASKS_EXTRACTED, RUNNING, FINISHED
	}

	public class StateChange
	{
		public Task target;
		public State formerState;
		public State newState;
	}
	
	@JsonProperty("id")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long m_id;
	
    @JsonProperty("descriptor")
    @Transient
    protected Descriptor m_descriptor;
	
	@JsonProperty("state")
	private State m_state;
    
    @JsonProperty("configuration")
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Result.class)
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Result m_configuration;
    
    @OneToMany(targetEntity=TaskRepresentative.class, fetch = FetchType.EAGER)
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<TaskRepresentative> m_dependencies;
    
    @Transient
    private Broadcaster<StateChange> m_stateStream;
    @Transient 
    private List<String> m_requirements;
    @Transient 
	private SessionInterface m_session;
    
    @JsonProperty("result")
	public abstract T result();
    
    @JsonProperty("result")
    protected abstract void result(T result);
    
	public abstract T execute();
	
	public Task()
	{
    	m_descriptor = new Descriptor((Class<Task>)this.getClass());
    	m_descriptor.commonName(this.getClass().getName());
    	m_dependencies = new Vector<TaskRepresentative>();
    	
    	try 
    	{
			Environment env = GlobalState.get("EventReactorEnvironment");
			m_stateStream = Broadcaster.create(env);
			m_stateStream.when(Exception.class, errorHandler());
		} 
    	catch (ObjectNotRegisteredException e) 
    	{
			e.printStackTrace();
		}
    	
		m_state = State.CREATED;
	}
	
    public static Consumer<Exception> errorHandler() {
        return ev -> {
          System.out.println("Task> Task ERROR: " + ev.getMessage());
        };
    }
	
	public void session(SessionInterface session)
	{
		m_session = session;
		if(result() != null)
			try 
			{
				result().session(session());
			} 
			catch (NoActiveSessionException e) 
			{
				e.printStackTrace();
			}
	}
	
	public SessionInterface session() throws NoActiveSessionException
	{
		if(m_session == null)
			throw new NoActiveSessionException(this);
		return m_session;
	}
	
	public void run()
	{
		state(State.RUNNING);
//		if(result() != null)
//			result(execute());
		execute();
		state(State.FINISHED);
		finished();
	}
	
	public Stream<StateChange> stateStream()
	{
		return m_stateStream;
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
	public State state()
	{
		return m_state;
	}
	
	@JsonProperty("state")
	public void state(State state)
	{
		StateChange change = new StateChange();
		change.target = this;
		change.formerState = state();
		change.newState = state;
		
		System.out.println(	"Task:" + descriptor().commonName() + "> State changed from "
							+ change.formerState + " to " + change.newState);
		
		m_state = state;
		
		if(m_stateStream.downstreamSubscription() != null)
			m_stateStream.onNext(change);
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
    	state(State.CONFIGURED);
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
	}
	
	public String toString()
	{
		return descriptor().commonName() + ":" + id();
	}
}
