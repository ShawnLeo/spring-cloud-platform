package com.shawn.sys.service;

import com.shawn.sys.util.AppVars;
import com.shawn.sys.vo.PageContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageService {

	@Autowired
	private AppVars appVars;

	/**
	 * 创建分页请求。
	 *
	 * @param pageNumber   当前页
	 * @param pageSize  每页条数
	 * @param orderBys  排序字段 name asc,age desc
	 * @author inn
	 * @date 2017-10-27
	 */
	public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, String orderBys) {
		if (pageNumber == null || pageNumber <= 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = appVars.defaultPageSize;
		}
		if (pageSize > appVars.maxPageSize) {
			pageSize = appVars.maxPageSize;
		}
		if (StringUtils.isNotBlank(orderBys)) {
			String[] orders = orderBys.trim().split(",");
			if (orders != null && orders.length > 0) {
				List<Sort.Order> list = new ArrayList<Sort.Order>();
				for (String orderBy : orders) {
					if (StringUtils.isNotBlank(orderBy)) {
						String[] strs = orderBy.trim().split(" ");
						if (strs != null && strs.length == 2) {
							if (strs[1].trim().toLowerCase().equals("desc")) {
								Sort.Order order = new Sort.Order(Sort.Direction.DESC, strs[0].trim());
								list.add(order);
							} else {
								Sort.Order order = new Sort.Order(Sort.Direction.ASC, strs[0].trim());
								list.add(order);
							}
						}
						if (strs != null && strs.length == 1) {
							Sort.Order order = new Sort.Order(Sort.Direction.ASC, strs[0].trim());
							list.add(order);
						}
					}
				}
				if (list.size() > 0) {
					Sort sort = new Sort(list);
					return new PageRequest(pageNumber - 1, pageSize, sort);
				} else {
					return new PageRequest(pageNumber - 1, pageSize);
				}
			} else {
				return new PageRequest(pageNumber - 1, pageSize);
			}
		} else {
			return new PageRequest(pageNumber - 1, pageSize);
		}
	}

	/**
	 * 构造分页参数
	 *
	 * @param pageNumber
	 *            页码，0开始
	 * @param pageSize
	 *            页大小
	 * @param sort
	 *            排序
	 * @return
	 */
	public Pageable getPageable(Integer pageNumber, Integer pageSize, Sort sort) {
		if (pageNumber == null || pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = appVars.defaultPageSize;
		}
		if (pageSize > appVars.maxPageSize) {
			pageSize = appVars.maxPageSize;
		}

		return new PageRequest(pageNumber, pageSize, sort);
	}

	/**
	 * 计算连续显示页的起始页码
	 *
	 * @param page
	 * @return 起始页码，0-首页
	 */
	public int getPageNumberStart(Page<?> page) {
		return Math.max(0, page.getNumber() - appVars.displayPages / 2);
	}

	/**
	 * 计算连续显示页的结束页码
	 *
	 * @param page
	 * @return 结束页码，0-首页
	 */
	public int getPageNumberEnd(Page<?> page) {
		return Math.min(Math.max(page.getNumber() + appVars.displayPages / 2 - 1, appVars.displayPages - 1),
				page.getTotalPages() - 1);
	}

	/**
	 * 获得连续显示页的页码
	 *
	 * @param page
	 * @return 显示页的页码
	 */
	public List<Integer> getPages(Page<?> page) {
		Integer startPage = this.getPageNumberStart(page);
		Integer endPage = this.getPageNumberEnd(page);
		List<Integer> pages = new ArrayList<Integer>();
		for (int i = startPage + 1; i <= endPage + 1; i++) {
			pages.add(i);
		}
		return pages;
	}

	/**
	 * 构造分页上下文对象
	 *
	 * @param page
	 * @return
	 */
	public <T> PageContext<T> buildPageContext(Page<T> page) {
		PageContext<T> pageContext = new PageContext<T>();
		pageContext.setPage(page);
		pageContext.setPages(getPages(page));
		pageContext.setTotalPages(page.getTotalPages());
		pageContext.setPageSize(page.getSize());
		pageContext.setPageNumber(page.getNumber());
		return pageContext;
	}

}
