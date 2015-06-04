package org.dutil.lawnch.system;

import org.dutil.lawnch.model.serviceprovider.ServiceProviderRepositoryInterface;
import org.dutil.lawnch.model.serviceprovider.StatelessServiceProviderRepositoryInterface;

import com.mongodb.client.MongoDatabase;

public interface SessionPersistenceInterface {
	public ServiceProviderRepositoryInterface serviceProviderRepository();
	public StatelessServiceProviderRepositoryInterface statelessServiceProviderRepository();
	public PersistenceUnit persistenceUnit();
	public MongoDatabase getMongoDb();
	public void destroy();
}
