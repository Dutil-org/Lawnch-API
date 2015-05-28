package org.dutil.lawnch.system;

public interface SessionInterface {
	public SessionPersistenceInterface sessionPersistence();
	public void sessionPersistence(SessionPersistenceInterface persistenceUnit);
	public void destroy();
}
