package com.wxy.dg.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.wxy.dg.common.config.Global;


/**
 * 分页类
 * @param <T>
 */
public class Page<T> {
	
	private int pageNo = 1; // 当前页码
	private int pageSize = Integer.valueOf(Global.getConfig("pageSize")); // 页面大小，设置为“-1”表示不进行分页（分页无效）
	private int pageCount;// 总页数
	private int count;// 总记录数
	private String orderBy = ""; // 排序参数
	private List<T> list = new ArrayList<T>();

	public Page(){

	}

	/**
	 * 构造方法
	 * @param request
	 * @param response
	 */
	public Page(HttpServletRequest request, HttpServletResponse response){
		this(request, response, 0);
	}

	/**
	 * 构造方法
	 * @param request
	 * @param response
	 * @param defaultPageSize 默认分页大小,如果传递 -1 则为不分页，返回所有数据
	 */
	public Page(HttpServletRequest request, HttpServletResponse response, int defaultPageSize){
		// 排序字段
		String sortName =new ParamEncoder("table").encodeParameterName(TableTagParameters.PARAMETER_SORT);
		// 排列顺序
		String sortOrder =new ParamEncoder("table").encodeParameterName(TableTagParameters.PARAMETER_ORDER);
		// 当前页码
		String currentPageNo =new ParamEncoder("table").encodeParameterName(TableTagParameters.PARAMETER_PAGE);

		if (defaultPageSize != 0){
			this.pageSize = defaultPageSize;
			if(request.getAttribute("pageNo") != null) {
				this.setPageNo(Integer.parseInt((String)request.getAttribute("pageNo")));
			}
		} else {
			if(request.getParameter(currentPageNo) != null) {
				this.setPageNo(Integer.parseInt(request.getParameter(currentPageNo)));
			}
			// 设置排序参数
			StringBuffer order = new StringBuffer();
			if(request.getParameter(sortName) != null) {
				order.append(request.getParameter(sortName));
				if("1".equals(request.getParameter(sortOrder))) {//降序
					order.append(" DESC");
				} else if("2".equals(request.getParameter(sortOrder))) {//升序
					order.append(" ASC");
				}
				this.setOrderBy(order.toString());
			}
		}
	}
	
	/**
	 * 获取设置总数
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 设置数据总数
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
		if (pageSize >= count){
			pageNo = 1;
		}
		int temp = 1;
		if(this.count % pageSize == 0) {
			temp = this.count / pageSize;
		} else {
			temp = this.count / pageSize + 1;
		}
		pageCount = temp;
	}
	
	/**
	 * 获取当前页码
	 * @return
	 */
	public int getPageNo() {
		return pageNo;
	}
	
	/**
	 * 设置当前页码
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	/**
	 * 获取页面大小
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置页面大小
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	/**
	 * 获取查询排序字符串
	 * @return
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置查询排序，标准查询有效
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 获取本页数据对象列表
	 * @return List<T>
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 设置本页数据对象列表
	 * @param list
	 */
	public Page<T> setList(List<T> list) {
		this.list = list;
		return this;
	}

	/**
	 * 分页是否有效
	 * @return this.pageSize==-1
	 */
	public boolean isDisabled() {
		return this.pageSize==-1;
	}
	
	/**
	 * 获取 Hibernate FirstResult
	 */
	public int getFirstResult(){
		int firstResult = (getPageNo() - 1) * getPageSize();
		if (firstResult >= getCount()) {
			firstResult = 0;
		}
		return firstResult;
	}
	
	/**
	 * 获取 Hibernate MaxResults
	 */
	public int getMaxResults(){
		return getPageSize();
	}

}