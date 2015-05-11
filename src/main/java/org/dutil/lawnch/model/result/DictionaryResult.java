package org.dutil.lawnch.model.result;

import java.io.IOException;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Inheritance                                                                                                                                                 
@JsonTypeName("DictionaryResult")
public class DictionaryResult extends Result{
	
	@JsonIgnore
	HashMap<String, String> m_storage = new HashMap<String, String>();
	
	public static DictionaryResult fromMap(HashMap<String, String> map)
	{
		DictionaryResult newResult = new DictionaryResult();
		newResult.storage(map);
		return newResult;
	}

	public void value(String key, String value) {
		m_storage.put(key, value)	;	
	}
	
	public String value(String key) {
		return m_storage.get(key);	
	}
	
	@JsonIgnore
	public HashMap<String, String> storage()
	{
		return m_storage;
	}
	
	@JsonIgnore
	public void storage(HashMap<String, String> storage)
	{
		m_storage = storage;
	}

	@Override
	public String allJson() {
		Document result = new Document();
		
		result.putAll(m_storage);
		
		return result.toJson();
	}

	@Override
	public void fromJsonString(String allData) {
		ObjectMapper mapper = new ObjectMapper();
		
		try 
		{
			storage((HashMap<String, String>) mapper.readValue(allData, new TypeReference<HashMap<String, String>>(){}));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
