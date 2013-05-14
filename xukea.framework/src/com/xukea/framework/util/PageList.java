package com.xukea.framework.util;

import java.util.List;

/**
 * 分页处理
 * @author FishBoy
 *
 * @param <E>
 */
public class PageList<E> {
	public static int DEFAULT_PAGE_SIZE = 10;
	
	private int pageSize   = DEFAULT_PAGE_SIZE;

	private int pageNumber = 1;

	private int totalCount = 0;
	
	private List<E> result;
	
	public PageList() {
		this(1, DEFAULT_PAGE_SIZE, 0);
	}
	
	public PageList(int totalCount) {
		this(1, DEFAULT_PAGE_SIZE, totalCount);
	}
	
	public PageList(int pageNumber, int totalCount) {
		this(pageNumber, DEFAULT_PAGE_SIZE, totalCount);
	}
	
	public PageList(int pageNumber, int pageSize, int totalCount) {
		if(pageSize <= 0) throw new IllegalArgumentException("[pageSize] must great than zero");
		this.pageSize   = pageSize;
		this.totalCount = totalCount;
		this.pageNumber = pageNumber;
	}

	
    /**
     * 是否是首页（第一页），第一页页码为1
     *
     * @return 首页标识
     */
	public boolean isFirstPage() {
		return getThisPageNumber() == 1;
	}

    /**
     * 是否是最后一页
     *
     * @return 末页标识
     */
	public boolean isLastPage() {
		return getThisPageNumber() >= getLastPageNumber();
	}
	
    /**
     * 是否有下一页
     *
     * @return 下一页标识
     */
	public boolean hasNextPage() {
		return getLastPageNumber() > getThisPageNumber();
	}
	
    /**
     * 是否有上一页
     *
     * @return 上一页标识
     */
	public boolean hasPrevPage() {
		return getThisPageNumber() > 1;
	}

    /**
     * 获取当前页的页码
     *
     * @return 当前页的页码
     */
	public int getThisPageNumber() {
		pageNumber = pageNumber<1 ? 1 : pageNumber;
		int lastNum = getLastPageNumber();
		pageNumber = pageNumber<lastNum ? pageNumber : lastNum;
		return pageNumber;
	}
	
    /**
     * 获取最后一页页码，也就是总页数
     *
     * @return 最后一页页码
     */
	public int getLastPageNumber() {
		int result = (int)Math.ceil( (double)getTotalCount()/getPageSize() );
		result = result<=1 ? 1 : result;
		return result;
	}
	
    /**
     * 获取下一页编码
     *
     * @return 下一页编码
     */
	public int getNextPageNumber() {
		return getThisPageNumber() + 1;
	}
	
    /**
     * 获取上一页编码
     *
     * @return 上一页编码
     */
	public int getPrevPageNumber() {
		return getThisPageNumber() - 1;
	}
	
    /**
     * 总的数据条目数量，0表示没有数据
     *
     * @return 总数量
     */
	public int getTotalCount() {
		return totalCount;
	}
	
    /**
     * 每一页显示的条目数
     *
     * @return 每一页显示的条目数
     */
	public int getPageSize() {
		return pageSize;
	}
	
    /**
     * 获取当前页的首条数据的行编码
     *
     * @return 当前页的首条数据的行编码
     */
	public int getThisPageFirstElementNumber() {
		return (getThisPageNumber() - 1) * getPageSize() + 1;
	}
	
	/**
	 * 得到数据库的第一条记录号
	 * 
	 * @return
	 */
	public int getFirstResultNumber() {
		return (getThisPageNumber() - 1) * getPageSize();
	}

    /**
     * 设置每一页显示的条目数
     */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

    /**
     * 设置当前页码
     */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

    /**
     * 设置总的数据条目数量
     */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	 * 获取记录集合
	 * @return
	 */
	public List<E> getResult() {
		return result;
	}

	/**
	 * 设置记录集合
	 * @param result
	 */
	public void setResult(List<E> result) {
		this.result = result;
	}
}

