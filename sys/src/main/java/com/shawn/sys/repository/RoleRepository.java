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
    public Role findByRoleCode(String roleCode);

    @Query(value = "select userId from sys_user_role where roleId = ?1", nativeQuery=true)
    public List<Long> findByRoleUserId(Long roleId);

	/*class DelegatingRoleSpecificationExecutor {
		private RoleRepository repository;

		public DelegatingRoleSpecificationExecutor(RoleRepository repository) {
			this.repository = repository;
		}

		public Page<Role> findAll(final Role filter, Pageable pageable) {
			return repository.findAll(new Specification<Role>() {
				@Override
				public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					if (filter == null) {
						return cb.conjunction();
					}

					return cb.and(merge(

					eq(cb, root.get(Role_.name), filter.getName()),
					like(cb, root.get(Role_.description), filter.getDescription())

					));
				}
			}, pageable);
		}
	}

	Role findByDescription(String description);*/

}
