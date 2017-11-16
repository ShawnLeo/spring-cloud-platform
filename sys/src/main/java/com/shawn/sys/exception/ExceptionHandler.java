package com.shawn.sys.exception;


import com.shawn.common.RetCode;
import com.shawn.common.Response;
import com.shawn.sys.util.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by wanglu-jf on 17/9/11.
 */
@RestControllerAdvice
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public Response exceptionHandler(Exception e){
        if(logger.isErrorEnabled()){
            logger.error("[=========exception message:{}]",e);
        }
        if(e instanceof ValidationException){
            ValidationException ce = (ValidationException)e;
            return Response.response(ce.getCode(),ce.getMessage());
        }

        if(e instanceof EditDomainException){
            ValidationException ce = (EditDomainException)e;
            return Response.response(ce.getCode(),ce.getMessage());
        }

        if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException ce = (MethodArgumentNotValidException)e;
            BindingResult bindingResult = ce.getBindingResult();
            return Response.response(RetCode.VALIDATEERROR.getCode(), ValidateUtils.addError(bindingResult));

        }

        return Response.response(RetCode.INTERNALEXCEP.getCode(),RetCode.INTERNALEXCEP.getMessage());
    }
}
