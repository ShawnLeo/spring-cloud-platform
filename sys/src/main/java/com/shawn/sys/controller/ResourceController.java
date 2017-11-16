package com.shawn.sys.controller;

import com.shawn.common.ComParams;
import com.shawn.common.RetCode;
import com.shawn.common.Response;
import com.shawn.sys.entity.Resource;
import com.shawn.sys.exception.ValidationException;
import com.shawn.sys.service.ResourceService;
import com.shawn.sys.util.ValidateUtils;
import com.shawn.sys.vo.JsTreeNode;
import com.shawn.sys.vo.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by wanglu-jf on 17/9/14.
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 获取资源树列表
     * @return
     */
    @RequestMapping("/list")
    public Response list() throws Exception{
        JsTreeNode<Resource> resourceJsTreeNode = this.resourceService.menuJsTree();
        return Response.success(resourceJsTreeNode);
    }

    /**
     * 根据Id获取 Resource
     * @return
     */
    @RequestMapping("/get/{id}")
    public Response get(@PathVariable("id") Long id) throws Exception{
        return Response.success(resourceService.findById(id));
    }

    /**
     * 保存资源菜单
     * @param resourceVO
     * @param loginUserId
     * @param result
     * @return
     */
    @PostMapping("/save")
    public Response saveResource(@Valid @RequestBody ResourceVO resourceVO, @RequestParam(ComParams.X_USERID)String loginUserId, BindingResult result)throws ValidationException{
        //验证表单数据
        ValidateUtils.validResource(resourceVO,result);
        if(result.hasErrors()){
            throw new ValidationException(RetCode.VALIDATEERROR.getCode(), ValidateUtils.addError(result));
        }
        this.resourceService.saveResource(resourceVO,loginUserId);
        return Response.success(null);
    }

    /**
     * 修改资源
     * @param resourceId
     * @param resourceVO
     * @param result
     * @return
     * @throws ValidationException
     */
    @RequestMapping("/update/{id}")
   public Response updateResourceById(@PathVariable("id") Long resourceId,@RequestParam(ComParams.X_USERID)String loginUserId,@Valid @RequestBody ResourceVO resourceVO,BindingResult result) throws ValidationException{
        //验证表单数据
        ValidateUtils.validResource(resourceVO,result);
        if(result.hasErrors()){
            throw new ValidationException(RetCode.VALIDATEERROR.getCode(), ValidateUtils.addError(result));
        }
        this.resourceService.updateResource(resourceVO,loginUserId,resourceId);
        return Response.success(null);
   }


    /**
     * 删除资源
     * @param resourceId
     * @pram loginUserId
     * @return
     */
    @RequestMapping("/delete/{id}")
   public Response deleteById(@PathVariable("id")Long resourceId,@RequestParam(ComParams.X_USERID)String loginUserId) throws ValidationException {
        this.resourceService.deleteById(resourceId);
        return Response.success(null);
   }

    /**
     * 改变资源节点
     * @param srcResourceId 原始资源id
     * @param newResourceId  改变后的资源id
     * @param loginUserId
     * @return
     */
//    TODO：
    @RequestMapping("/change/{id}")
    public Response changeResourceNode(@PathVariable("id")Long srcResourceId,@RequestParam("nId") Long newResourceId,@RequestParam(ComParams.X_USERID)String loginUserId)throws ValidationException{
        this.resourceService.changeResourceNode(srcResourceId,newResourceId,loginUserId);
        return Response.success(null);
    }

    @RequestMapping(value ="/get/all", method = RequestMethod.GET)
    public Response findAll(){
        return Response.success(resourceService.findAll());

    }

}
