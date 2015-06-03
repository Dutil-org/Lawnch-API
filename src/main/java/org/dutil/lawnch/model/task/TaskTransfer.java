package org.dutil.lawnch.model.task;

import java.util.HashMap;

import org.dutil.lawnch.model.descriptor.Descriptor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskTransfer {
	@JsonProperty("descriptor")
	Descriptor m_descriptor;
	
	@JsonProperty("configuration")
	HashMap<String, String> m_configuration;

	public Descriptor descriptor() {
		return m_descriptor;
	}

	public void descriptor(Descriptor m_descriptor) {
		this.m_descriptor = m_descriptor;
	}

	public HashMap<String, String> configuration() {
		return m_configuration;
	}

	public void configuration(HashMap<String, String> m_configuration) {
		this.m_configuration = m_configuration;
	}
}
