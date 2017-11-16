package com.shawn.sys.repository;

import com.shawn.sys.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by wanglu-jf on 17/9/7.
 */
public interface UserAuthRepository extends JpaRepository<UserAuth,String> {

    /**
     * 根据用户登陆账号查询认证账户
     * @param authId 账号
     * @return
     */
//    @Query("FROM UserAuth WHERE authId = :authId OR authId = : phone OR authId = :email")
    public UserAuth findByAuthId(@Param("authId") String authId/*,@Param("phone")String phone,@Param("email")String email*/);


}
