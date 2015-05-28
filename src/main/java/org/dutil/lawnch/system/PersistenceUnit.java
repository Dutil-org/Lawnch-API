package org.dutil.lawnch.system;

import java.util.List;


public interface PersistenceUnit {
	public <T> T persist(T instance);
	public <T> T update(T instance);
	public <T> T save(T instance);
	public <T> void delete(T instance);
	public <T> T find(Class<T> instantType, long id);
	public <T> List<T> find(Class<T> instantType);
	public void close();
}
