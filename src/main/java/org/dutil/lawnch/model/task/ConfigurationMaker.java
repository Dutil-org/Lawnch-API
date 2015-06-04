package org.dutil.lawnch.model.task;

import org.dutil.lawnch.model.result.Result;
import org.dutil.lawnch.system.SessionInterface;

public interface ConfigurationMaker {
	Result makeConfiguration(Result superConfiguration, SessionInterface session);
}
