package org.dutil.lawnch.model.serviceprovider;

import org.dutil.lawnch.model.service.Service;

public interface ServiceProviderRepositoryInterface extends Repository<Provider<Service>> {
	
	Provider<Service> findByIdentifier(String identifier);

}
