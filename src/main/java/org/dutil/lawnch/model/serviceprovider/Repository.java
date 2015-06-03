package org.dutil.lawnch.model.serviceprovider;

import java.util.List;

import org.dutil.lawnch.model.descriptor.Describable;
import org.dutil.lawnch.model.descriptor.Descriptor;

public interface Repository <T>{
	public void register(T instance);
    public T get(String identifier) throws InstantiationException, IllegalAccessException;
    public Descriptor descriptor(String identifier) throws InstantiationException, IllegalAccessException;
	public List<T> findAll();
	public List<Descriptor> findAllDescriptors();
}
