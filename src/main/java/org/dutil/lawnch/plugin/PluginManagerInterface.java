package org.dutil.lawnch.plugin;

import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.serviceprovider.GenericServiceProvider;
import org.dutil.lawnch.model.serviceprovider.ServiceProviderRepositoryInterface;
import org.dutil.lawnch.system.PersistenceUnit;

public interface PluginManagerInterface {
	PersistenceUnit createPersistenceUnit();
	void registerClassLoader(ClassLoader classLoader, String packageName);
	void registerServiceProvider(GenericServiceProvider provider);
	<T extends Describable> RegistryInterface<T> createRegistry(ClassLoader classLoader, Class<T> type, String packageName);
	ServiceProviderRepositoryInterface serviceProviderRepository();
}
