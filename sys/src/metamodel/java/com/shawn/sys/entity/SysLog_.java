package com.shawn.sys.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SysLog.class)
public abstract class SysLog_ extends com.shawn.sys.dialect.AbstractSyslog_ {

	public static volatile SingularAttribute<SysLog, String> logType;
	public static volatile SingularAttribute<SysLog, String> logModule;
	public static volatile SingularAttribute<SysLog, String> host;
	public static volatile SingularAttribute<SysLog, String> userId;
	public static volatile SingularAttribute<SysLog, String> remarks;
	public static volatile SingularAttribute<SysLog, Date> operTime;

}

