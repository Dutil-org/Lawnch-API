package org.dutil.lawnch.model.serviceprovider;

import java.util.List;

import org.dutil.lawnch.model.descriptor.Descriptor;

public interface ServiceProviderRepositoryInterface {
	public void registerServiceProvider(GenericServiceProvider provider);
    public GenericServiceProvider serviceProvider(String identifier) throws InstantiationException, IllegalAccessException;
    public Descriptor serviceProviderDescriptor(String identifier) throws InstantiationException, IllegalAccessException;
	public List<GenericServiceProvider> findAll();
	public GenericServiceProvider findByServiceIdentifier(String identifier);
	public List<Descriptor> findAllDescriptors();
}
