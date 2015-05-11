package org.dutil.lawnch.model.serviceprovider;

import java.util.HashMap;

import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.result.Result;
import org.dutil.lawnch.model.service.Service;
import org.dutil.lawnch.model.task.ConfigurationFailedException;
import org.dutil.lawnch.plugin.RegistryInterface;

import ro.fortsoft.pf4j.ExtensionPoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GenericServiceProvider implements ExtensionPoint, ServiceProvider
{	
	@JsonProperty("descriptor")
    private Descriptor m_descriptor;
    @JsonIgnore
	private RegistryInterface<Service> m_serviceRegistry;
    
    public GenericServiceProvider()
    {
	    	m_descriptor = new Descriptor<GenericServiceProvider>((Class<GenericServiceProvider>)this.getClass());
	    	m_descriptor.commonName(this.getClass().getName());
    }
    
    public void registry(RegistryInterface<Service> registry)
    {
    	m_serviceRegistry = registry;
    }
    
    @JsonProperty("descriptor")
    public Descriptor descriptor()
    {
    	return m_descriptor;
    }
    
    @JsonProperty("services")
    @Override
    public HashMap<String, Descriptor<Service>> services()
    {
    	System.out.println("GenericServiceProvider> Classloader: "+ this.getClass().getClassLoader());
    	return m_serviceRegistry.allDescriptors();
    }
    
    @Override
    public <T extends Result> Service<T> service(String serviceIdentifier, Result configuration) throws InstantiationException, IllegalAccessException
    {
    	Class<Service> serviceClass = m_serviceRegistry.get(serviceIdentifier).classDescriptor();
    	Service newService = (Service) serviceClass.newInstance();
    	
    	System.out.println("GenericServiceProvider> creating Service: " + newService.descriptor().commonName());
    	
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
    
    public boolean hasService(String identifier)
    {
    	return m_serviceRegistry.has(identifier);	
    }
	
	@Override
	public Descriptor descriptor(String serviceIdentifier)
	{
		return m_serviceRegistry.descriptor(serviceIdentifier);
	}
    
}
