package org.dutil.lawnch.model.task;

import org.dutil.lawnch.model.result.Result;

public interface Configurable {

    Result configuration();
    public void configuration(Result configuration) throws ConfigurationFailedException;

}
