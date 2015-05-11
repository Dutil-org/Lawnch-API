package org.dutil.lawnch.model.service;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

import org.dutil.lawnch.model.result.Result;

import ro.fortsoft.pf4j.ExtensionPoint;

@Entity
@Inheritance
public abstract class PluginService<T extends Result> extends Service<T> implements ExtensionPoint{
	
	public PluginService()
	{
		super();
    	m_classLoader = "Plugin";
    	descriptor().pluginIdentifier(true);
	}
}
