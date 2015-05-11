package org.dutil.lawnch.model.result;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ro.fortsoft.pf4j.ExtensionPoint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

@Entity
@org.hibernate.annotations.DiscriminatorOptions(force=true)
public abstract class Result implements ExtensionPoint {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long m_id;
	
	public void id(long id)
	{
		m_id = id;
	}
	
	public long id()
	{
		return m_id;
	}
		
	public abstract void value(String key, String value);
	public abstract String value(String key);
	
	@JsonProperty("storage")
	@JsonRawValue
	public abstract String allJson();
	
	@JsonProperty("storage")
	public abstract void fromJsonString(String allData);
}
