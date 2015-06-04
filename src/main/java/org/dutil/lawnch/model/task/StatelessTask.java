package org.dutil.lawnch.model.task;

import java.util.ArrayList;
import java.util.List;

import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.result.Result;
import org.dutil.lawnch.system.SessionInterface;

public abstract class StatelessTask<T extends Result> implements Describable{

	private List<StatelessTaskRepresentative> m_dependencies;
	
	public StatelessTask()
	{
		m_dependencies = new ArrayList<StatelessTaskRepresentative>();
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
	
	public abstract T execute(Result configuration, SessionInterface session);
}
