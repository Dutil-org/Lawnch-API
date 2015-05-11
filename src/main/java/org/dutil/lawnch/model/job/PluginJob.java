package org.dutil.lawnch.model.job;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

import org.dutil.lawnch.model.result.Result;

import ro.fortsoft.pf4j.ExtensionPoint;

@Entity
@Inheritance
public abstract class PluginJob <T extends Result> extends Job<T> implements ExtensionPoint{
	
	public PluginJob()
	{
		super();
    	m_classLoader = "Plugin";
    	descriptor().pluginIdentifier(true);
	}
}
