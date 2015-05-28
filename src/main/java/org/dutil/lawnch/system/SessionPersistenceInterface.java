package org.dutil.lawnch.system;

import org.dutil.lawnch.model.serviceprovider.ServiceProviderRepositoryInterface;

import com.mongodb.client.MongoDatabase;

public interface SessionPersistenceInterface {
	public ServiceProviderRepositoryInterface serviceProviderRepository();
	public PersistenceUnit persistenceUnit();
	public MongoDatabase getMongoDb();
	public void destroy();
}
