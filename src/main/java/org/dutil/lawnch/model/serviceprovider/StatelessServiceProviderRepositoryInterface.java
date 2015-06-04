package org.dutil.lawnch.model.serviceprovider;

import org.dutil.lawnch.model.service.StatelessService;

public interface StatelessServiceProviderRepositoryInterface extends Repository<Provider<StatelessService>>{

	Provider<StatelessService> findByIdentifier(String identifier);
	
}
