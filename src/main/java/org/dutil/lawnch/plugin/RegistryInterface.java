package org.dutil.lawnch.plugin;

import java.util.HashMap;

import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.descriptor.Descriptor;
import org.dutil.lawnch.model.task.TaskNotFoundException;

public interface RegistryInterface<T extends Describable> {
	HashMap<String, Descriptor<T>> allDescriptors();
	Descriptor<T> get(String name) throws TaskNotFoundException;
	boolean has(String name);
	Descriptor<T> descriptor(String identifier);
}
