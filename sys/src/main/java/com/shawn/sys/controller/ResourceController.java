package com.shawn.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.shawn.common.ComParams;
import com.shawn.common.RetCode;
import com.shawn.common.Response;
import com.shawn.sys.entity.Resource;
import com.shawn.sys.exception.EditDomainException;
import com.shawn.sys.exception.ValidationException;
import com.shawn.sys.service.ResourceService;
import com.shawn.sys.util.ValidateUtils;
import com.shawn.sys.vo.JsTreeNode;
import com.shawn.sys.vo.ResourceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    private static Logger logger = LoggerFactory.getLogger(ResourceController.class);

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
    public Response get(@PathVariable("id") String id) throws EditDomainException {
        return Response.success(resourceService.findById(id));
    }

    /**
     * 根据系统编号获取 菜单
     * @return
     */
    @RequestMapping("/menu/list")
    public Response menuList(@RequestParam("system") String system) throws EditDomainException {
        return Response.success(resourceService.menuList(system));
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
    public Response updateResourceById(@PathVariable("id") String resourceId,@RequestParam(ComParams.X_USERID)String loginUserId,@Valid @RequestBody ResourceVO resourceVO,BindingResult result) throws ValidationException{
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
    public Response deleteById(@PathVariable("id")String resourceId) throws ValidationException {
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
    @RequestMapping("/change/{id}")
    public Response changeResourceNode(@PathVariable("id")String srcResourceId,@RequestParam("nId") String newResourceId,@RequestParam(ComParams.X_USERID)String loginUserId)throws ValidationException{
        this.resourceService.changeResourceNode(srcResourceId,newResourceId,loginUserId);
        return Response.success(null);
    }

    @RequestMapping(value ="/get/all", method = RequestMethod.GET)
    public Response findAll(){
        return Response.success(resourceService.findAll());

    }

    /**
     * 导出某节点下资源文件
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value ="/export/{id}", method = RequestMethod.GET)
    public void export(@PathVariable("id")String id, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("resources.txt", "utf-8"));
        response.getOutputStream().write(resourceService.export(id).getBytes());
    }

    @RequestMapping(value ="/import", method = RequestMethod.POST)
    public Response importResource(@RequestParam("file")MultipartFile file) throws IOException {
        resourceService.importResource(JSONArray.parseArray(new String(file.getBytes()), Resource.class));
        return Response.success(null);
    }

}