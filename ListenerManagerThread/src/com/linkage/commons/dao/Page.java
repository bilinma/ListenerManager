package com.linkage.commons.dao;

import java.io.Serializable;

/**
 * 分页对象,用来支持前台的分页查询

 * @author zhaoxin
 *
 */
public class Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_PAGE_SIZE=20;// 每页显示的记录数,默认20
	private int pageSize = DEFAULT_PAGE_SIZE;
	private int totalPage;// 总页数

	private long totalSize;// 总记录数
	private int curPage;// 目前页数,起始为1
	private boolean firstPage = false;
	private boolean lastPage = false;
	private int first; //首页
	private int next; //下一页

	private int previous; //上一页

	private int end; //尾页


	public Page(long totalSize) {
		this.totalSize = totalSize;
		init();
	}

	public Page() {
		curPage = 1;
	}

	/**
	 * 初始化totalPage和curPage.
	 */
	public void init() {
		if (pageSize != 0) {
			long totalPageLong = (totalSize + pageSize - 1) / pageSize;
			totalPage = Integer.parseInt(String.valueOf(totalPageLong));
			curPage = 1;
		}
	}

	/**
	 * get curPage.
	 * 
	 * @return int
	 */
	public int getCurPage() {
		return curPage;
	}

	/**
	 * get totalSize.
	 * 
	 * @return long
	 */
	public long getTotalSize() {
		return totalSize;
	}

	/**
	 * set totalSize.
	 * 
	 * @param totalSize
	 *            long
	 */
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
		init();
	}

	/**
	 * set curPage.
	 * 
	 * @param curPage
	 *            int
	 */
	public void setCurPage(int curPage) {
		if (curPage > totalPage) {
			this.curPage = totalPage;
		} else {
			if (curPage < 1) {
				this.curPage = 1;
			} else {
				this.curPage = curPage;
			}
		}
	}

	/**
	 * set totalPage.
	 * 
	 * @param totalPage
	 *            int
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * get totalPage.
	 * 
	 * @return int
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * get first.
	 * 
	 * @return int
	 */
	public int getFirst() {
		return 1;
	}

	/**
	 * get end.
	 * 
	 * @return int
	 */
	public int getEnd() {
		return totalPage;
	}

	/**
	 * get next.
	 * 
	 * @return int
	 */
	public int getNext() {
		if (curPage < getEnd()) {
			return curPage + 1;
		} else {
			curPage = getEnd();
			return getEnd();
		}
	}

	/**
	 * get Pre.
	 * 
	 * @return int
	 */
	public int getPrevious() {
		if (curPage > 1) {
			return curPage - 1;
		} else {
			curPage = getFirst();
			return getFirst();
		}
	}

	/**
	 * get pageSize.
	 * 
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * get pageSize.
	 * 
	 * @param pageSize
	 *            int
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		init();
	}

	/**
	 * @param firstPage
	 *            the firstPage to set
	 */
	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	/**
	 * @param lastPage
	 *            the lastPage to set
	 */
	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	/**
	 * @return the firstPage
	 */
	public boolean isFirstPage() {
		if (curPage == 1) {
			firstPage = true;
		} else {
			firstPage = false;
		}
		return firstPage;
	}

	/**
	 * @return the lastPage
	 */
	public boolean isLastPage() {
		if (curPage == totalPage) {
			lastPage = true;
		} else {
			lastPage = false;
		}
		return lastPage;
	}

	/**
	 * @param first
	 *            the first to set
	 */
	public void setFirst(int first) {
		this.first = first;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(int next) {
		this.next = next;
	}

	/**
	 * @param previous
	 *            the previous to set
	 */
	public void setPrevious(int previous) {
		this.previous = previous;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}

}
