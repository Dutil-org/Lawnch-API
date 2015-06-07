package org.dutil.lawnch.model.task;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.dutil.lawnch.execution.Streamable;
import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.result.Result;
import org.dutil.lawnch.system.SessionInterface;

import reactor.rx.Stream;
import reactor.rx.broadcast.Broadcaster;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class StatelessTask<T extends Result> implements Describable, Streamable {

	private List<StatelessTaskRepresentative> m_dependencies;
	
    @JsonProperty("descriptor")
    @Transient
    protected Descriptor m_descriptor;
    
    Broadcaster<TaskEnvironment> m_broadcaster;
	
	public StatelessTask()
	{
    	m_descriptor = new Descriptor((Class<StatelessTask>)this.getClass());
    	m_descriptor.commonName(this.getClass().getName());
		m_dependencies = new ArrayList<StatelessTaskRepresentative>();
	}
	
	@JsonProperty("descriptor")
	public Descriptor descriptor()
	{
		return m_descriptor;
	}
	
	public List<StatelessTaskRepresentative> dependencies()
	{
		return m_dependencies;
	}
	
	protected void addDependency(String serviceIdentifier, String serviceProviderIdentifier, ConfigurationMaker configuration)
	{
		Descriptor descriptor = new Descriptor();
		descriptor.identifier(serviceIdentifier);
		descriptor.providerIdentifier(serviceProviderIdentifier);
		addDependency(descriptor, configuration);
	}
	
	protected void addDependency(Descriptor descriptor, ConfigurationMaker configuration)
	{
		StatelessTaskRepresentative dependency = new StatelessTaskRepresentative();
		dependency.descriptor = descriptor;
		dependency.configuration = configuration;
		m_dependencies.add(dependency);
	}

	public TaskEnvironment handleStream(TaskEnvironment env)
	{
		env.container = execute(env.container, env.session);
		return env;
	}
	
	public abstract T execute(Result configuration, SessionInterface session);
	
	public void setupStreams(Stream<TaskEnvironment> stream)
	{
		
	}
}
