package com.shawn.sys.service;

import com.shawn.sys.entity.UserAuth;
import com.shawn.sys.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wanglu-jf on 17/9/7.
 */
@Service
public class UserAuthService {

    @Autowired
    private UserAuthRepository userAuthRepository;

    /**
     * 根据用户登陆账号查询认证账户
     * @param authId 账号
     * @return
     */
    public UserAuth findByAuthId(String authId){
        UserAuth userAuth = this.userAuthRepository.findByAuthId(authId);
        return userAuth;
    }


}
