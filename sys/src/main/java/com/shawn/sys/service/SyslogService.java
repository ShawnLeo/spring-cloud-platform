package com.shawn.sys.service;

import com.shawn.sys.entity.SysLog;
import com.shawn.sys.repository.SysLogRepository;
import com.shawn.sys.vo.PageContext;
import com.shawn.sys.vo.SysLogVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
@Component
public class SyslogService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
	private SysLogRepository logRepository;
    @Autowired
    private PageService pageService;

    /**
     * 保存
     * @param syslog
     */
	@Transactional
	public void save(SysLog syslog) {
		try {
            logRepository.save(syslog);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param log
     * @param orderBys
     * @return
     */
    public PageContext<SysLog> findLog(Integer pageNum, Integer pageSize, SysLogVO log, String orderBys) {
        Specification<SysLog> specification = new Specification<SysLog>() {
            @Override
            public Predicate toPredicate(Root<SysLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                //参数非空判断。不为空则加此条件
                if(log != null) {
                    if (StringUtils.isNotEmpty(log.getLogModule())) {
                        Predicate logModule = criteriaBuilder.equal(root.get("logModule"), log.getLogModule());
                        predicates.add(logModule);
                    }
                    if (StringUtils.isNotEmpty(log.getUserId())) {
                        Predicate userId = criteriaBuilder.equal(root.get("userId"), log.getUserId());
                        predicates.add(userId);
                    }
                    if (log.getBeginTime() != null) {
                        Predicate beginTime = criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("operTime"), log.getBeginTime());
                        predicates.add(beginTime);
                    }
                    if (log.getEndTime() != null) {
                        Predicate endTime = criteriaBuilder.lessThanOrEqualTo(root.<Date>get("operTime"), log.getEndTime());
                        predicates.add(endTime);
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return pageService.buildPageContext(logRepository.findAll(specification,pageService.buildPageRequest(pageNum,pageSize,orderBys)));

    }

}