package org.dutil.lawnch.model.serviceprovider;

import java.util.HashMap;

import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.result.Result;
import org.dutil.lawnch.model.service.Service;
import org.dutil.lawnch.model.task.ConfigurationFailedException;
import org.dutil.lawnch.model.task.TaskNotFoundException;
import org.dutil.lawnch.plugin.RegistryInterface;

import ro.fortsoft.pf4j.ExtensionPoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceProvider implements ExtensionPoint, Provider<Service>
{	
	@JsonProperty("descriptor")
    private Descriptor m_descriptor;
    @JsonIgnore
	protected RegistryInterface<Service> m_serviceRegistry;
    
    public ServiceProvider()
    {
    	m_descriptor = new Descriptor<ServiceProvider>((Class<ServiceProvider>)this.getClass());
    	m_descriptor.commonName(this.getClass().getName());
    }
    
    @Override
    public void registry(RegistryInterface<Service> registry)
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
    public HashMap<String, Descriptor<Service>> services()
    {
    	return m_serviceRegistry.allDescriptors();
    }
    
    @Override
    public Service service(String serviceIdentifier, Result configuration) throws InstantiationException, IllegalAccessException, TaskNotFoundException
    {
    	Class<Service> serviceClass = m_serviceRegistry.get(serviceIdentifier).classDescriptor();
    	Service newService = (Service) serviceClass.newInstance();
    	
    	System.out.println("ServiceProvider> creating Service: " + newService.descriptor().commonName());
    	
		try 
		{
	    	newService.configuration(configuration);
		} 
		catch (ConfigurationFailedException e) 
		{
			System.out.println("Service> Configuration Error: ");
			System.out.println("\t" + e.getMessage());
			e.printStackTrace();
		}
    	
    	newService.descriptor().providerIdentifier(m_descriptor.identifier());    	
    	return newService;
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
