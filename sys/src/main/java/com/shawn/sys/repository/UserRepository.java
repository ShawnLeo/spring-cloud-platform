package com.shawn.sys.repository;

import com.shawn.sys.entity.Role_;
import com.shawn.sys.entity.User;
import com.shawn.sys.entity.UserAuth_;
import com.shawn.sys.entity.User_;
import io.jsonwebtoken.lang.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.*;
import java.util.Iterator;
import java.util.Set;

import static com.shawn.sys.util.JpaSpecUtils.*;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	/**
	 * 根据手机号码和邮箱查询用户
	 * @param phone
	 * @return
	 */
	 User findByPhoneOrEmail(@Param("phone") String phone, @Param("email") String email);

	/**
	 * 根据手机号码查询用户
	 * @param phone
	 * @return
	 */
	 User findByPhone(@Param("phone") String phone);

	/**
	 * 根据邮箱查询用户
	 * @param email
	 * @return
	 */
	 User findByEmail(@Param("email") String email);




	class DelegatingUserSpecificationExecutor {
		private UserRepository repository;

		public DelegatingUserSpecificationExecutor(UserRepository repository) {
			this.repository = repository;
		}

		public Page<User> findAll(final User filter, Pageable pageable) {
			return repository.findAll(new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					if (filter == null) {
						return cb.conjunction();
					}
					query.distinct(true);

					Path<Long> rolePath=null;
					if (!Collections.isEmpty(filter.getRoleIds())){
						rolePath = root.join(User_.roles).get(Role_.id);
					}

					Path<String> authPath=null;
					String authId=null;
					if (!Collections.isEmpty(filter.getAuthIds())){
						authPath = root.join(User_.userAuths).get(UserAuth_.authId);
						Set<String> aa = filter.getAuthIds();
						Iterator<String> it = aa.iterator();
						while (it.hasNext()) {
							authId = it.next();
							break;
						}
					}

					return cb.and(merge(eq(cb, root.get(User_.id), filter.getId()),
							eq(cb, root.get(User_.gender), filter.getGender()),
							like(cb, root.get(User_.realName), filter.getRealName()),
							like(cb, root.get(User_.phone), filter.getPhone()),
							in(cb, rolePath, filter.getRoleIds()),
							eq(cb, authPath, authId)
					));
				}
			}, pageable);
		}

	}

}
