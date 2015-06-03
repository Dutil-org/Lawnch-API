package org.dutil.lawnch.model.task;

import java.util.HashMap;
import java.util.List;

import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.result.Result;

public abstract class StatelessTask<T extends Result> implements Describable{

	private HashMap<Descriptor, StatelessTask> m_dependencies;
	
	public HashMap<Descriptor, StatelessTask> dependencies()
	{
		return m_dependencies;
	}
	
	protected void addDependency(String serviceIdentifier, String serviceProviderIdentifier)
	{
		Descriptor descriptor = new Descriptor();
		descriptor.identifier(serviceIdentifier);
		descriptor.providerIdentifier(serviceProviderIdentifier);
		addDependency(descriptor);
	}
	
	protected void addDependency(Descriptor descriptor)
	{
		m_dependencies.put(descriptor, null);
	}
	
	public abstract T execute(Result configuration);
}
