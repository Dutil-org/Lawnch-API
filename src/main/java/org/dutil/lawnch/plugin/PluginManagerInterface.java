package org.dutil.lawnch.plugin;

import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.serviceprovider.GenericServiceProvider;

public interface PluginManagerInterface {
	void registerServiceRepository(ClassLoader classLoader);
	void registerJobRepository(ClassLoader classLoader);
	void registerServiceProvider(GenericServiceProvider provider);
	<T extends Describable> RegistryInterface<T> createRegistry(ClassLoader classLoader, Class<T> type, String packageName);
}
