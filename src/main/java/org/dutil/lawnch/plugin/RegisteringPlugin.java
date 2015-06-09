package org.dutil.lawnch.plugin;

	import org.dutil.lawnch.model.serviceprovider.ServiceProvider;
import org.dutil.lawnch.model.serviceprovider.StatelessServiceProvider;
import org.dutil.lawnch.model.task.AllreadyRegisteredException;
import org.dutil.lawnch.model.task.TaskEnvironment;
import org.dutil.lawnch.model.task.TaskNotFoundException;
import org.dutil.lawnch.system.GlobalState;
import org.dutil.lawnch.system.ObjectNotRegisteredException;

import reactor.rx.Stream;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

public class RegisteringPlugin extends Plugin {
	
	PluginManagerInterface m_pluginManager;
	ServiceProvider m_services;
	StatelessServiceProvider m_statelessServices;
	
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
		ProviderContainer container = m_pluginManager
				.registerClassLoader(localPluginLoader, this.getClass().getPackage().getName());
		
		m_services = container.serviceProvider;
		m_statelessServices = container.statelessServiceProvider;
		setupStreams();
	}
	
	/**
	 * @param identifier 
	 * @return a stream that receives events when the specific service is called via the backend
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws TaskNotFoundException 
	 */
	protected Stream<TaskEnvironment> entryStream(String identifier) throws InstantiationException, IllegalAccessException, TaskNotFoundException
	{
		try 
		{
			return m_pluginManager.stream(identifier);
		} 
		catch (TaskNotFoundException e) 
		{
			try 
			{
				return m_pluginManager.registerStream(m_statelessServices.service(identifier, null));
			} 
			catch (AllreadyRegisteredException e1) 
			{
				e1.printStackTrace();
			}
		}
		
		return null;
	}
	
	protected void setupStreams()
	{
	}

}
