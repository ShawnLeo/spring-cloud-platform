package com.shawn.sys.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Resource.class)
public abstract class Resource_ extends com.shawn.sys.dialect.AbstractResource_ {

	public static volatile SingularAttribute<Resource, String> resType;
	public static volatile SingularAttribute<Resource, String> modType;
	public static volatile SetAttribute<Resource, Role> roles;
	public static volatile SingularAttribute<Resource, Date> updateTime;
	public static volatile SingularAttribute<Resource, String> parentId;
	public static volatile SingularAttribute<Resource, String> path;
	public static volatile SingularAttribute<Resource, String> createBy;
	public static volatile SingularAttribute<Resource, String> resLevel;
	public static volatile SingularAttribute<Resource, Date> createTime;
	public static volatile SingularAttribute<Resource, String> updateBy;
	public static volatile SingularAttribute<Resource, String> name;
	public static volatile SingularAttribute<Resource, String> style;
	public static volatile SingularAttribute<Resource, Integer> dispOrder;
	public static volatile SingularAttribute<Resource, String> remarks;

}

