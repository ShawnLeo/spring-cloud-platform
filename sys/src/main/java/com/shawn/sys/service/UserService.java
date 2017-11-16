package com.shawn.sys.service;

import com.shawn.common.RetCode;
import com.shawn.sys.entity.User;
import com.shawn.sys.entity.UserAuth;
import com.shawn.sys.exception.EditDomainException;
import com.shawn.sys.exception.ValidationException;
import com.shawn.sys.repository.UserAuthRepository;
import com.shawn.sys.repository.UserRepository;
import com.shawn.sys.util.MD5;
import com.shawn.sys.util.UserStatus;
import com.shawn.sys.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    /**
     * 保存用户
     * @param userVO
     * @return
     */
    @Transactional
    public void saveUser(UserVO userVO,String userId) throws ValidationException {
        if(logger.isInfoEnabled()){
            logger.info("========[保存用户]，userVO：{}，userId：{}",userVO,userId);
        }
        try {
            User user = new User();
            UserAuth userAuth = new UserAuth();
            user.setRoles(userVO.getRoleSet());
            BeanUtils.copyProperties(userVO,user);
            BeanUtils.copyProperties(userVO,userAuth);
            //验证认证用户唯一
            UserAuth userAuthByAuthId = this.userAuthRepository.findByAuthId(userAuth.getAuthId());
            if(null != userAuthByAuthId){
                throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"该登录帐号已被使用");
            }

            user.setCreateBy(userId);//创建者--当前登陆用户

            Set<UserAuth> userAuths =  user.getUserAuths();

            //认证方式 3：昵称
            userAuths.add(userAuth);
            userAuth.setAuthPass(MD5.encodeByMd5AndSalt(userAuth.getAuthPass()));//加密

            userAuth.setUser(user);
            userRepository.save(userAuth.getUser());
            this.userAuthRepository.save(userAuth);

        } catch (BeansException e) {
            if(logger.isErrorEnabled()){
                logger.error("[=======保存用户时属性copy赋值信息出现异常]，errorMSG:{}",e.getMessage());
            }
            throw new ValidationException(RetCode.INTERNALEXCEP.getCode(),RetCode.INTERNALEXCEP.getMessage());
        }catch (ValidationException e) {
            if(logger.isErrorEnabled()){
                logger.error("[=======保存用户信息出现异常]，errorMSG:{}",e.getMessage());
            }
            throw new ValidationException(e.getCode(),e.getMessage());
        }
    }


    /**
     * 保存用户
     * @param userVO
     * @return
     */
    @Transactional
    public void updateUser(UserVO userVO,String userId) throws ValidationException {
        if(logger.isInfoEnabled()){
            logger.info("========[保存用户]，userVO：{}，userId：{}",userVO,userId);
        }
        try {
            User user = new User();
            user.setRoles(userVO.getRoleSet());
            BeanUtils.copyProperties(userVO,user);
            userRepository.save(user);
        } catch (BeansException e) {
            if(logger.isErrorEnabled()){
                logger.error("[=======保存用户时属性copy赋值信息出现异常]，errorMSG:{}",e.getMessage());
            }
            throw new ValidationException(RetCode.INTERNALEXCEP.getCode(),RetCode.INTERNALEXCEP.getMessage());
        }
    }

    /**
     * 根据主键查询用户
     * @param userId
     * @return
     */
    public User findById(Long userId) throws EditDomainException{
        if(logger.isInfoEnabled()){
            logger.info("========[根据主键查询用户]，userId：{}",userId);
        }
        try {
            return this.userRepository.findOne(userId);
        } catch (Exception e) {
            if(logger.isErrorEnabled()){
                logger.error("[=======根据主键查询用户出现异常]，errorMSG:{}",e.getMessage());
            }
            throw new EditDomainException(RetCode.INTERNALEXCEP.getCode(),RetCode.INTERNALEXCEP.getMessage());
        }
    }

    /**
     * 修改密码
     * @param userVO
     * @param loginUserId
     */
    public void updatePwdById(UserVO userVO, String loginUserId) throws ValidationException {
        if(logger.isInfoEnabled()){
            logger.info("========[修改密码]，userVO：{},loginUserId",userVO,loginUserId);
        }
        try {
            User user = userRepository.findOne(Long.valueOf(loginUserId));
            if(null == user){
                throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"该用户不存在");
            }
            UserAuth userAuth = userAuthRepository.findOne(userVO.getAuthId());
            if(null == userAuth){
                throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"该用户未认证");
            }
            if(!userAuth.getAuthPass().equalsIgnoreCase(MD5.encodeByMd5AndSalt(userVO.getAuthPass()))){
                    throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"原登录密码输入有误");
            }
            userAuth.setAuthPass(MD5.encodeByMd5AndSalt(userVO.getNewPassword()));
            userAuth.setPassTime(new Date());
            userAuth.setUpdateBy(loginUserId);
            userAuth.setUpdateTime(new Date());
            this.userAuthRepository.saveAndFlush(userAuth);
        } catch (ValidationException e) {
           if(logger.isErrorEnabled()){
               logger.info("========[修改密码出现异常]，errorMSG：{}",e.getMessage());
           }
           throw new ValidationException(e.getCode(),e.getMessage());
        }

    }

    /**
     * 重置密码
     * @param userVO
     * @param loginUserId
     */
    public void resetPwd(UserVO userVO, String loginUserId,Long userId) throws ValidationException {
        if(logger.isInfoEnabled()){
            logger.info("========[重置密码]，userVO：{},loginUserId",userVO,loginUserId);
        }
        try {
            User user = userRepository.findOne(userId);
            if(null == user){
                throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"该用户不存在");
            }
            UserAuth userAuth = userAuthRepository.findOne(userVO.getAuthId());
            if(null == userAuth){
                throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"该用户未认证");
            }
            if(!user.getId().equals(userAuth.getUser().getId())){
                throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"该用户未认证");
            }
            if(userAuth.getAuthPass().equalsIgnoreCase(MD5.encodeByMd5AndSalt("111111"))){
                if(logger.isInfoEnabled()){
                    logger.info("========[重置密码,原始密码与新密码一致]");
                }
                throw new RuntimeException("密码重置失败,原始密码与新密码一致！");
            }
            userAuth.setAuthPass(MD5.encodeByMd5AndSalt("111111"));
            userAuth.setPassTime(new Date());
            userAuth.setUpdateBy(loginUserId);
            userAuth.setUpdateTime(new Date());
            this.userAuthRepository.saveAndFlush(userAuth);
        } catch (Exception e) {
            if(logger.isErrorEnabled()){
                logger.info("========[重置密码出现异常]，errorMSG：{}",e.getMessage());
            }
            throw new RuntimeException(e.getMessage(),e);
        }

    }

    /**
     * 锁定、解锁账户
     * @param loginUserId
     * @param userId
     */
    public void lockUser(String loginUserId, Long userId) throws ValidationException {
        if(logger.isInfoEnabled()){
            logger.info("========[锁定、解锁账户]，loginUserId,userId",loginUserId,userId);
        }
        String operate=null;
        try {
            User user = userRepository.findOne(userId);
            if(null == user){
                throw new ValidationException(RetCode.VALIDATEERROR.getCode(),"该用户不存在");
            }
            if(UserStatus.LOCKED.getCode().equals(user.getStatus())){
                user.setStatus(UserStatus.NORMAL.getCode());
                operate=UserStatus.NORMAL.getText();
            }else if (UserStatus.NORMAL.getCode().equals(user.getStatus())){
                user.setStatus(UserStatus.LOCKED.getCode());
                operate=UserStatus.LOCKED.getText();
            }else {
                throw new RuntimeException("用户状态异常！");
            }

            user.setUpdateTime(new Date());
            user.setUpdateBy(loginUserId);
            this.userRepository.saveAndFlush(user);
        } catch (ValidationException e) {
            if(logger.isErrorEnabled()){
                logger.info("========[{}出现异常]，errorMSG：{}",operate,e.getMessage());
            }
            throw new ValidationException(e.getCode(),e.getMessage());
        }
    }

    @Autowired
    private PageService pageService;
    @Autowired
    private UserRepository repos;



    public Page<User> findUserPageContext(Integer pageNumber, Integer pageSize, final User criteria) {

        try {
            UserRepository.DelegatingUserSpecificationExecutor executor = new UserRepository.DelegatingUserSpecificationExecutor(repos);
            Pageable pageable = new PageRequest(pageNumber, pageSize, Sort.Direction.ASC, new String[]{"id"});
            return executor.findAll(criteria, pageable);
        } catch (Exception e) {
            if(logger.isErrorEnabled()){
                logger.info("========[用户分页查询异常]，errorMSG：{}",e.getMessage());
            }
            throw new RuntimeException(e.getMessage(),e);
        }




    }








	/*

	@Autowired
	private PageService pageService;
	@Autowired
	private UserRepository repos;
	@Autowired
	private RoleRepository roleRepos;
	@Autowired
	private OrganRepository organRepos;
	@Autowired
	private AppVars appVars;

	public User findByLoginName(String loginName) {
		return repos.findByLoginName(loginName);
	}

	@Transactional
	public void lockUser(String loginName) {
		repos.updateStatusByLoginName(loginName, UserStatus.LOCKED.getCode());
	}

	public PageContext<User> getUserPageContext(Integer pageNumber, Integer pageSize) {
		Sort sort = new Sort(new Order(Sort.Direction.DESC, User_.disporder.getName()));
		Pageable pageable = pageService.getPageable(pageNumber, pageSize, sort);
		return pageService.buildPageContext(repos.findAll(pageable));
	}

	public PageContext<User> findUserPageContext(Integer pageNumber, Integer pageSize, final User criteria) {
		logger.info("查询操作员列表");
		DelegatingUserSpecificationExecutor executor = new DelegatingUserSpecificationExecutor(repos);
		Sort sort = new Sort(new Order(Sort.Direction.DESC, User_.disporder.getName()));
		Pageable pageable = pageService.getPageable(pageNumber, pageSize, sort);
		return pageService.buildPageContext(executor.findAll(criteria, pageable));
	}

	public List<User> findUser(final User criteria) {
		DelegatingUserSpecificationExecutor executor = new DelegatingUserSpecificationExecutor(repos);
		return executor.findUser(criteria);
	}

	@Transactional
	public User userAdd(User user) {
		if (StringUtils.isEmpty(user.getLoginName())) {
			logger.info("登录ID必须设置");
			throw new RuntimeException("0");
		} else if (!userIsExists(user.getLoginName(), "loginName")) {
			logger.info("登录ID重复，请重新定义");
			throw new RuntimeException("1");
		}
		String plainPassword = user.getPlainPassword();
		if (!StringUtils.isEmpty(plainPassword)) {
			user.setPassword(encodeUserPassword(plainPassword, user));
		}
		repos.save(user);
		for (String roleName : user.getRoleNames()) {
			Role role = roleRepos.findByName(roleName);
			user.getRoleSet().add(role);
		}

		if (!StringUtils.isEmpty(user.getOrganId())) {
			Organ organ = organRepos.findOne(user.getOrganId());
			user.setOrgan(organ);
			int code = 0;
			for (User u : organ.getUsers()) {
				try {
					code = Math.max(code, G.i(u.getEmployeeNumber().substring(u.getEmployeeNumber().length() - 3)));
				} catch (Exception e) {
					logger.warn(e.getMessage(), e);
				}
			}
			user.setEmployeeNumber(organ.getDeptCode() + String.format("%04d", code + 1));
		}

		return repos.save(user);
	}

	public void userDelete(Long id) {
		repos.delete(id);
	}

	@Transactional
	public User userUpdate(User user) {
		if (StringUtils.isEmpty(user.getLoginName())) {
			logger.info("登录ID必须设置");
			throw new RuntimeException("0");
		} else if (repos.findByLoginNameAndIdNot(user.getLoginName(), user.getId()) != null) {
			logger.info("登录ID重复，请重新定义");
			throw new RuntimeException("1");
			// } else if
			// (!repos.findByEmployeeNumberAndIdNot(user.getEmployeeNumber(),
			// user.getId()).isEmpty()) {
			// logger.info("用户编号重复，请重新定义");
			// throw new RuntimeException("2");
		}
		User user_ = repos.findOne(user.getId());
		user.setPassword(user_.getPassword());
		for (String roleName : user.getRoleNames()) {
			Role role = roleRepos.findByName(roleName);
			user.getRoleSet().add(role);
		}

		if (!StringUtils.isEmpty(user.getOrganId())) {
			Organ organ = organRepos.findOne(user.getOrganId());
			user.setOrgan(organ);
			if (!user_.getOrganId().equals(user.getOrganId())) {
				int code = 0;
				for (User u : organ.getUsers()) {
					if (!u.getId().equals(user.getId())) {
						try {
							code = Math.max(code,
									G.i(u.getEmployeeNumber().substring(u.getEmployeeNumber().length() - 3)));
						} catch (Exception e) {
							logger.warn(e.getMessage(), e);
						}
					}
				}
				user.setEmployeeNumber(organ.getDeptCode() + String.format("%04d", code + 1));
			} else {
				user.setEmployeeNumber(user_.getEmployeeNumber());
			}
		}

		return repos.save(user);
	}

	@Transactional
	public User updateUserProfile(User user, User oldUser) {
		oldUser.setBirthday(user.getBirthday());
		oldUser.setEmail(user.getEmail());
		oldUser.setPhone(user.getPhone());
		return repos.save(oldUser);
	}

	*//*
	 * (non-Javadoc)
	 * 
	 * @see net.eagle.cfp.mgt.service.UserService#userReset(java.lang.Long)
	 *//*
	public User userReset(Long id) {
		if (!StringUtils.isEmpty(id)) {
			User user = repos.findOne(id);
			user.setPassword(encodeUserPassword(appVars.resetPassword, user));
			user.setStatus(UserStatus.UNLOCK.getCode()); // 重置密码后解锁
			return repos.save(user);
		}
		return null;
	}

	private String encodeUserPassword(String plainPassword, User user) {
		// TODO
//		SimpleHash hash = new SimpleHash(appVars.hashAlgorithmName, plainPassword, user.getSalt(),
//				appVars.hashIterations);
//		return hash.toString();
		return null;
	}

	@Transactional
	public ResponseMessage changeLoginUserPassword(String plainOldPassword, String plainNewPassword,
												   ResponseMessage response, String loginName) {

		if (StringUtils.isEmpty(plainOldPassword) || StringUtils.isEmpty(plainNewPassword)) {
			response.fail("您输入有误！");
			return response;
		}

		User user = findByLoginName(loginName);
		if (!encodeUserPassword(plainOldPassword, user).equalsIgnoreCase(user.getPassword())) {
			response.fail("您输入的密码有误！");
			return response;
		}
		if (encodeUserPassword(plainNewPassword, user).equalsIgnoreCase(user.getPassword())) {
			response.fail("旧密码与新密码不可相同！");
			return response;
		}

		user.setPassword(encodeUserPassword(plainNewPassword, user));
		user.setStatus(UserStatus.NORMAL.getCode()); // 重置密码后，状态修改为正常
		user = repos.save(user);
		response.setStatusCode(1);
		return response;
	}

	public User getByUserId(Long userId) {
		return repos.findOne(userId);
	}

	public Boolean userIsExists(String userId, String type) {
		if ("loginName".equals(type)) {
			return repos.findByLoginName(userId) == null;
		} else if ("employeeNumber".equals(type)) {
			return repos.findByEmployeeNumber(userId) == null;
		}
		return true;
	}

	public List<User> findUserByRole(final Role role) {
		final User filter = new User();
		filter.getRoleSet().add(role);
		DelegatingUserSpecificationExecutor executor = new DelegatingUserSpecificationExecutor(repos);
		return executor.findUser(filter);
	}*/

}
