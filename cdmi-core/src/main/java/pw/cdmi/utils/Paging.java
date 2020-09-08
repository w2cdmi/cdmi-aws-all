package pw.cdmi.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * **********************************************************
 * 分页信息
 * 
 * @author wangbaijun
 * @version iSoc Service Platform, 2015-5-13
 ***********************************************************
 */
@Getter
@Setter
public class Paging {
	private int currPage = 0; // 当前页

	private int firstResult = 0; // 起始记录数

	private int maxResult = 20; // 每页最大记录

	private int totalResult = 0; // 总记录数

	private int totalPage = 0; // 总页数

	private int previous = 0; // 上一页

	private int next = 0; // 下一页


	/**
	 * 构建分页信息
	 * 
	 * @param totalResult
	 *            总记录数
	 * @param currPage
	 *            当前页码
	 * @param maxResult
	 *            该页最大记录
	 */
	public Paging(int totalResult, int currPage, int maxResult) {
		this.totalResult = totalResult;
		this.maxResult = maxResult;
		// 获取总页数
		int page = totalResult / maxResult;
		this.totalPage = totalResult % maxResult > 0 ? page + 1 : page;
		if (totalPage == 1) {
			this.currPage = 1;
			this.previous = totalPage;
			this.next = totalPage;
		} else {
			// 设置当前页码及获取上下页控制
			if (currPage <= 1) {
				this.currPage = 1;
			} else {
				if (currPage >= totalPage) {
					this.currPage = totalPage;
				} else {
					this.currPage = currPage;
				}
			}
		}
		previous=currPage-1>1?currPage-1:1;
		next=currPage+1<totalPage?currPage+1:totalPage;
		// 获取查询起始记录
		this.firstResult = this.currPage * maxResult - maxResult;
	}
}
