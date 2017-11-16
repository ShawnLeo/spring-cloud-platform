package com.shawn.sys.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.shawn.sys.dialect.AbstractUser_ {

	public static volatile SingularAttribute<User, String> gender;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, Date> updateTime;
	public static volatile SingularAttribute<User, String> portraitPath;
	public static volatile SingularAttribute<User, String> realName;
	public static volatile SingularAttribute<User, String> createBy;
	public static volatile SingularAttribute<User, String> phone;
	public static volatile SingularAttribute<User, Date> createTime;
	public static volatile SingularAttribute<User, String> updateBy;
	public static volatile SingularAttribute<User, Date> deleteTime;
	public static volatile SetAttribute<User, UserAuth> userAuths;
	public static volatile SingularAttribute<User, String> deleteBy;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> remarks;
	public static volatile SingularAttribute<User, String> status;

}

