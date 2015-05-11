package org.dutil.lawnch.plugin;

import java.util.Set;

import org.dutil.lawnch.model.service.Service;
import org.dutil.lawnch.model.serviceprovider.GenericServiceProvider;
import org.dutil.lawnch.system.GlobalState;
import org.dutil.lawnch.system.ObjectNotRegisteredException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

public class RegisteringPlugin extends Plugin {
	
	PluginManagerInterface m_pluginManager;
	
	public RegisteringPlugin(PluginWrapper wrapper)
	{
		super(wrapper);
		try 
		{
			m_pluginManager = GlobalState.get("PluginManager");
		}
		catch (ObjectNotRegisteredException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void start() 
	{
		System.out.println("Registering Plugin");
		ClassLoader localPluginLoader = this.getClass().getClassLoader();
			
		//register Plugin Repositories
		m_pluginManager.registerServiceRepository(localPluginLoader);
		m_pluginManager.registerJobRepository(localPluginLoader);

    	// create scanner and disable default filters (that is the 'false' argument)
    	final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
    	provider.setResourceLoader(new PathMatchingResourcePatternResolver(localPluginLoader));
    	
    	// add include filters which matches all the classes (or use your own)
    	provider.addIncludeFilter((TypeFilter) new AssignableTypeFilter(GenericServiceProvider.class));
    	// get matching classes defined in the package
    	final Set<BeanDefinition> classes = provider.findCandidateComponents(this.getClass().getPackage().getName());
    	
    	for (BeanDefinition definition : classes) {
			try 
			{
				Class<GenericServiceProvider> registeredClass;
				registeredClass = (Class<GenericServiceProvider>) localPluginLoader.loadClass(definition.getBeanClassName());

				GenericServiceProvider instance = registeredClass.newInstance();
				instance.registry(m_pluginManager.createRegistry(	
						localPluginLoader, 
						(Class<Service>) Class.forName("org.dutil.lawnch.model.service.Service"), 
						instance.getClass().getPackage().getName()));
				m_pluginManager.registerServiceProvider(instance);
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			} 
			catch (IllegalArgumentException e) 
			{
				e.printStackTrace();
			} 
			catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    	}
	}

}
