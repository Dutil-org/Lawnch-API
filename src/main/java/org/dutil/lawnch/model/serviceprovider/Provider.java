package org.dutil.lawnch.model.serviceprovider;

import java.util.HashMap;

import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.result.Result;
import org.dutil.lawnch.model.service.Service;
import org.dutil.lawnch.plugin.RegistryInterface;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Provider<T extends Describable> extends Describable{
	// returns a hasmap cansisting of a preconfigured Service and the services description
	HashMap<String, Descriptor<T>> services();
	
	Descriptor descriptor(String serviceIdentifier);
	   
    public void registry(RegistryInterface<T> registry);
    
    @JsonProperty("descriptor")
    public Descriptor descriptor();
    
    public boolean has(String identifier);
	
	// returns the service identified by its Classname
	public  T service(String serviceName, Result configuration) throws InstantiationException, IllegalAccessException;
}
