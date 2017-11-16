package com.shawn.sys.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends com.shawn.sys.dialect.AbstractRole_ {

	public static volatile SingularAttribute<Role, String> createBy;
	public static volatile SingularAttribute<Role, Date> createTime;
	public static volatile SingularAttribute<Role, String> updateBy;
	public static volatile SingularAttribute<Role, String> roleCode;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile SetAttribute<Role, Resource> resources;
	public static volatile SingularAttribute<Role, Date> updateTime;
	public static volatile SingularAttribute<Role, String> dispOrder;
	public static volatile SingularAttribute<Role, String> remarks;

}

