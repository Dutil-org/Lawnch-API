package org.dutil.lawnch.execution;

import org.dutil.lawnch.model.task.TaskEnvironment;

public interface Streamable {
	TaskEnvironment handleStream(TaskEnvironment env);
}
