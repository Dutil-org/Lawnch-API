package org.dutil.lawnch.plugin;

import java.util.HashMap;

import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.descriptor.Descriptor;

public interface RegistryInterface<T extends Describable> {
	HashMap<String, Descriptor<T>> allDescriptors();
	Descriptor<T> get(String name);
	boolean has(String name);
	Descriptor<T> descriptor(String identifier);
}
