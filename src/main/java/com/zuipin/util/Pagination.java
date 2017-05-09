package com.zuipin.util;
/**
 * 
 * @Title: Pagination
 * @Package: com.zuipin.util
 * @author: zengxinchao  
 * @date: 2017年3月17日 下午4:08:49
 * @Description: Pagination
 */
public class Pagination {
	/**页码*/
	private Integer pageNumber;
	/**页面大小*/
	private Integer pageSize = 10;
	/**总记录数*/
	private Integer	totalRecord	= 0;
	/**偏移*/
	@SuppressWarnings("unused")
	private Long offset;
	
	public Pagination(){
		
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getOffset() {
		if(pageNumber != null && pageSize != null){
			return (pageNumber - 1) * pageSize;
		}
		return null;
	}
	
	public Integer getTotalRecord() {
		return totalRecord;
	}
	
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}
}
