package org.dutil.lawnch.model.job;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import org.dutil.lawnch.model.result.Result;
import org.dutil.lawnch.model.task.Task;
import org.dutil.lawnch.system.SessionInterface;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@org.hibernate.annotations.DiscriminatorOptions(force=true)
//T specifies the jobs result type
public abstract class Job<T extends Result> extends Task<T>
{

	protected String m_classLoader;
    
    @JsonProperty("result")
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Result.class)
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.ALL)
    private T m_result;  
    
    public Job()
    {
    }
    
    @JsonProperty("result")
    public T result()
    {
    	return m_result;
    }
    
    @JsonProperty("result")
    protected void result(T result)
    {
    	m_result = result;
    } 
}
