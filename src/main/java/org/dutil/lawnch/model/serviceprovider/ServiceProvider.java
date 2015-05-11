package org.dutil.lawnch.model.serviceprovider;

import java.util.HashMap;

import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.result.Result;
import org.dutil.lawnch.model.service.Service;

public interface ServiceProvider {
	// returns a hasmap cansisting of a preconfigured Service and the services description
	HashMap<String, Descriptor<Service>> services();
	Descriptor descriptor(String serviceIdentifier);
	
	// returns the service identified by its Classname
	public <T extends Result> Service<T> service(String serviceName, Result configuration) throws InstantiationException, IllegalAccessException;
}
