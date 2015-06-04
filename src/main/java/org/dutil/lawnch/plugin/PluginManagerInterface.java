package org.dutil.lawnch.plugin;

import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.serviceprovider.ServiceProvider;
import org.dutil.lawnch.model.serviceprovider.ServiceProviderRepositoryInterface;
import org.dutil.lawnch.model.serviceprovider.StatelessServiceProviderRepositoryInterface;
import org.dutil.lawnch.system.PersistenceUnit;

public interface PluginManagerInterface {
	PersistenceUnit createPersistenceUnit();
	void registerClassLoader(ClassLoader classLoader, String packageName);
	void registerServiceProvider(ServiceProvider provider);
	<T extends Describable> RegistryInterface<T> createRegistry(ClassLoader classLoader, Class<T> type, String packageName);
	ServiceProviderRepositoryInterface serviceProviderRepository();
	StatelessServiceProviderRepositoryInterface statelessServiceProviderRepository();
}
