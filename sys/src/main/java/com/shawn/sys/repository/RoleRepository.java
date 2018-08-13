package com.shawn.sys.repository;

import com.shawn.sys.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    /**
     * 根据角色名称查询角色
     * @param roleCode
     * @return
     */
    Role findByRoleCode(String roleCode);

    List<Role> findByRoleCodeIn(List<String> roleCodes);

    @Query(value = "select userId from sys_user_role where roleId = ?1", nativeQuery=true)
    List<Long> findByRoleUserId(Long roleId);

}
