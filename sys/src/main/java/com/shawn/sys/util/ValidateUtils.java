package com.shawn.sys.util;

import com.alibaba.fastjson.JSON;
import com.shawn.common.RetCode;
import com.shawn.sys.exception.ValidationException;
import com.shawn.sys.vo.ResourceVO;
import com.shawn.sys.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * vo验证
 * Created by wanglu-jf on 17/9/7.
 */
public class ValidateUtils {

    //手机号检测正则表达式
    private static final Pattern phonePattern = Pattern.compile("^1[34578]\\d{9}$");

    //密码检测正则表达式
    private static Pattern pwdPattern = Pattern.compile("^[0-9a-zA-Z]{6,18}$");

    //邮箱正则表达式
    private static Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    /**
     * 判断字符串有效性
     */
    public static boolean isValid(String str){
        if(StringUtils.isBlank(str.trim())){
            return false ;
        }
        return true ;
    }

    /**
     * 检测是否为合法手机号码
     * @return
     */
    public static boolean isMobileNO(String mobiles)  throws ValidationException{
        try {
            Assert.hasLength(mobiles,MessageUtils.NULLPHONE.getMessage());
        } catch (Exception e) {
            throw new ValidationException(RetCode.VALIDATEERROR.getCode(),e.getMessage());
        }
        Matcher m = phonePattern.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证密码
     */
    public static boolean validationPwd(String pwd) throws ValidationException{
        try {
            Assert.hasLength(pwd,MessageUtils.NULLPWD.getMessage());
        } catch (Exception e) {
            throw new ValidationException(RetCode.VALIDATEERROR.getCode(),e.getMessage());
        }
        Matcher m = pwdPattern.matcher(pwd);
        return m.matches();
    }

    /**
     * 验证邮箱
     */
    public static boolean isEmail(String email) throws ValidationException{
        try {
            Assert.hasLength(email,MessageUtils.NULLEMAIL.getMessage());
        } catch (Exception e) {
            throw new ValidationException(RetCode.VALIDATEERROR.getCode(),e.getMessage());
        }
        Matcher m = emailPattern.matcher(email);
        return m.matches();
    }

    /**
     * 验证登陆账号
     * @param authId
     * @param result
     */
    public static void isLoginName(String authId, BindingResult result) throws ValidationException{
        try {
            Assert.hasLength(authId.trim(),MessageUtils.NULLAUTHID.getMessage());
        } catch (Exception e) {
            throw new ValidationException(RetCode.VALIDATEERROR.getCode(),e.getMessage());
        }
        if(isMobileNO(authId) || isEmail(authId)){
            result.addError(new FieldError(result.getObjectName(),"authId",MessageUtils.ERRORAUTHID.getMessage()));
        }
    }

    /**
     * 验证模块类型及url链接
     * @param resourceVO
     * @param result
     * @throws ValidationException
     */
    public static void validResource(ResourceVO resourceVO, BindingResult result)throws ValidationException{
        if(null == resourceVO){
            throw new ValidationException(MessageUtils.REQUESTEXCEPTION.getCode()+"",MessageUtils.REQUESTEXCEPTION.getMessage());
        }
        String resType = resourceVO.getResType().trim();//资源类型
        if("1".equals(resType)){// 1:API资源
            String path = resourceVO.getPath();//url链接
            if(StringUtils.isBlank(path)){
               result.addError(new FieldError(result.getObjectName(),"path","url链接不能为空"));
            }
        }else if("2".equals(resType)){//2:功能模块
            String modType = resourceVO.getModType();//模块类型
            if(!isValid(modType)){
                int type = Integer.parseInt(modType);
                if(type < 1 || type > 3){
                    result.addError(new FieldError(result.getObjectName(),"modType","请选择模块类型"));
                }
            }
        }else{
            throw new ValidationException(MessageUtils.REQUESTEXCEPTION.getCode()+"",MessageUtils.REQUESTEXCEPTION.getMessage());
        }

    }

    /**
     * 组装错误信息
     * @param result
     * @return
     */
    public static String addError(BindingResult result){
        Map<String,String> map = new HashMap<String,String>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for(FieldError fe : fieldErrors){
//			System.out.println(fe.getField()+"========"+fe.getDefaultMessage());
            map.put(fe.getField(),fe.getDefaultMessage());
        }
        return JSON.toJSONString(map);
    }


    /**
     * 验证修改密码
     * @param userVO
     */
    public static void validationUpdatePwd(UserVO userVO,BindingResult result) throws ValidationException {
        if(null == userVO){
            throw new ValidationException(MessageUtils.REQUESTEXCEPTION.getCode()+"",MessageUtils.REQUESTEXCEPTION.getMessage());
        }
        String authPass = userVO.getAuthPass();//原密码
        String newPassword = userVO.getNewPassword();//新密码
        String confirmPassword = userVO.getConfirmPassword();//确认密码
        if(!validationPwd(newPassword)){
            result.addError(new FieldError(result.getObjectName(),"newPassword","新密码格式输入错误(6~18位字母数字的组合)"));
        }
        if(!validationPwd(confirmPassword)){
            result.addError(new FieldError(result.getObjectName(),"confirmPassword","确认密码格式输入错误(6~18位字母数字的组合)"));
        }
        if(authPass.equalsIgnoreCase(newPassword)){
            result.addError(new FieldError(result.getObjectName(),"confirmPassword","新密码与原密码不能一样"));
        }
        if(!newPassword.equals(confirmPassword)){
            result.addError(new FieldError(result.getObjectName(),"confirmPassword","新密码与确认密码不一致"));
        }
    }


    public static void validationAuthId(UserVO userVO,BindingResult result) throws ValidationException {
        if(null == userVO){
            throw new ValidationException(MessageUtils.REQUESTEXCEPTION.getCode()+"",MessageUtils.REQUESTEXCEPTION.getMessage());
        }
        if(!isValid(userVO.getAuthId())){
            throw new ValidationException(MessageUtils.REQUESTEXCEPTION.getCode()+"","请求参数缺失,"+MessageUtils.REQUESTEXCEPTION.getMessage());
        }
    }

}
