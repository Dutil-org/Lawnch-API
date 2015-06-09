package org.dutil.lawnch.plugin;

import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.serviceprovider.ServiceProviderRepositoryInterface;
import org.dutil.lawnch.model.serviceprovider.StatelessServiceProviderRepositoryInterface;
import org.dutil.lawnch.model.task.AllreadyRegisteredException;
import org.dutil.lawnch.model.task.StatelessTask;
import org.dutil.lawnch.model.task.TaskEnvironment;
import org.dutil.lawnch.model.task.TaskNotFoundException;
import org.dutil.lawnch.system.PersistenceUnit;

import reactor.rx.Stream;

public interface PluginManagerInterface {
	PersistenceUnit createPersistenceUnit();
	ProviderContainer registerClassLoader(ClassLoader classLoader, String packageName);
	<T extends Describable> RegistryInterface<T> createRegistry(ClassLoader classLoader, Class<T> type, String packageName);
	ServiceProviderRepositoryInterface serviceProviderRepository();
	StatelessServiceProviderRepositoryInterface statelessServiceProviderRepository();
	Stream<TaskEnvironment> stream(Descriptor task) throws TaskNotFoundException;
	Stream<TaskEnvironment> stream(String taskIdentifier) throws TaskNotFoundException;
	Stream<TaskEnvironment> registerStream(StatelessTask task) throws AllreadyRegisteredException;
	void signalStream(TaskEnvironment env);
}
