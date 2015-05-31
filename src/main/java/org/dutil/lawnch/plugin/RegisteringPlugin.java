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
		m_pluginManager.registerClassLoader(localPluginLoader, this.getClass().getPackage().getName());

	}

}
