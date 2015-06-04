package org.dutil.lawnch.model.serviceprovider;

import java.util.HashMap;

import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.result.Result;
import org.dutil.lawnch.model.service.Service;
import org.dutil.lawnch.model.service.StatelessService;
import org.dutil.lawnch.model.task.ConfigurationFailedException;
import org.dutil.lawnch.model.task.StatelessTask;
import org.dutil.lawnch.plugin.RegistryInterface;

import ro.fortsoft.pf4j.ExtensionPoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class StatelessServiceProvider implements ExtensionPoint, Provider<StatelessService>{
	
	private HashMap<String, StatelessService> m_instances;
	
	@JsonProperty("descriptor")
    private Descriptor m_descriptor;
	
    @JsonIgnore
	protected RegistryInterface<StatelessService> m_serviceRegistry;
    
    public StatelessServiceProvider()
    {
    	m_descriptor = new Descriptor<StatelessServiceProvider>((Class<StatelessServiceProvider>)this.getClass());
    	m_descriptor.commonName(this.getClass().getName());
    	m_instances = new HashMap<String, StatelessService>();
    }
	
    @Override
    public StatelessService service(String serviceIdentifier, Result configuration) throws InstantiationException, IllegalAccessException
    {
    	StatelessService task = m_instances.get(serviceIdentifier);
    	if(task != null)
    		return task;
    	
    	Class<StatelessService> serviceClass = m_serviceRegistry.get(serviceIdentifier).classDescriptor();
    	task = (StatelessService) serviceClass.newInstance();
    	
    	System.out.println("StatelessServiceProvider> creating Service: " + task.descriptor().commonName());
    	
		task.descriptor().providerIdentifier(m_descriptor.identifier());
    	m_instances.put(serviceIdentifier, task);
    	
    	return task;
    }

    @Override
    public void registry(RegistryInterface<StatelessService> registry)
    {
    	m_serviceRegistry = registry;
    }
    
    @JsonProperty("descriptor")
    @Override
    public Descriptor descriptor()
    {
    	return m_descriptor;
    }
    
    @JsonProperty("services")
    @Override
    public HashMap<String, Descriptor<StatelessService>> services()
    {
    	return m_serviceRegistry.allDescriptors();
    }
    
    @Override
    public boolean has(String identifier)
    {
    	return m_serviceRegistry.has(identifier);	
    }
	
	@Override
	public Descriptor descriptor(String serviceIdentifier)
	{
		return m_serviceRegistry.descriptor(serviceIdentifier);
	}
}
