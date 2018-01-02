package com.shawn.sys.dialect;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String GENERATOR_NAME = "system-uuid";

	@Id
//	@GenericGenerator(name = GENERATOR_NAME, strategy = "uuid")
//	@GeneratedValue(generator = GENERATOR_NAME)
	@Column(name = "ID",length = 40)
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}