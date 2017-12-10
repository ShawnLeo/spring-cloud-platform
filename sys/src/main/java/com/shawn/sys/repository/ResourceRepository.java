package com.shawn.sys.repository;

import com.shawn.sys.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, String> {

    /**
     * 根据资源名称及节点id,url查询资源菜单
     * @param name
     * @param parentId
     * @return
     */
    Resource findByParentIdAndNameAndPath(@Param("parentId")String parentId,@Param("name") String name,@Param("path")String path);

    /**
     * 根据资源名称及节点id查询资源菜单
     * @param parentId
     * @param name
     * @return
     */
    Resource findByParentIdAndName(@Param("parentId")String parentId,@Param("name") String name);

    /**
     * 根据资节点id查询资源菜单
     * @param parentId
     * @return
     */
    List<Resource> findByParentIdOrderByDispOrder(@Param("parentId")String parentId);

    List<Resource> findByPathIsNotNullOrderByIdDesc();

    List<Resource> findByModTypeAndParentIdOrderByDispOrder(String modType, String system);

    Resource findByModTypeAndNameOrderByDispOrder(String modType, String system);
}