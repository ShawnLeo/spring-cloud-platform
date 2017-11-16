package com.shawn.sys.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserAuth.class)
public abstract class UserAuth_ {

	public static volatile SingularAttribute<UserAuth, Date> lastLoginTime;
	public static volatile SingularAttribute<UserAuth, String> authPass;
	public static volatile SingularAttribute<UserAuth, Date> createTime;
	public static volatile SingularAttribute<UserAuth, String> updateBy;
	public static volatile SingularAttribute<UserAuth, Date> passTime;
	public static volatile SingularAttribute<UserAuth, Date> updateTime;
	public static volatile SingularAttribute<UserAuth, String> authType;
	public static volatile SingularAttribute<UserAuth, User> user;
	public static volatile SingularAttribute<UserAuth, String> authId;
	public static volatile SingularAttribute<UserAuth, String> loginCount;

}

