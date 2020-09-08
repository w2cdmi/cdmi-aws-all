package pw.cdmi.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * **********************************************************
 * 视图对象，用于保存页面返回数据
 * 
 * @author wangbaijun
 * @version iSoc Service Platform, 2015-5-13
 ***********************************************************
 */
@Setter
@Getter
public class ViewObject<E> {
	/**
	 * 返回状态：操作成功
	 */
	public static final int SUCCESS = 1;

	/**
	 * 返回状态：失败，如数据验证错误等可重试的操作
	 */
	public static final int FAILURE = 2;

	/**
	 * 返回状态：错误，如文件、数据库操作异常
	 */
	public static final int ERROR = 3;

	private int status; // 返回状态

	private String msg; // 返回信息

	private Paging paging; // 分页信息

	private E object; // 数据对象

	private List<E> datas = new ArrayList<E>(); // 数据集合

	public ViewObject() {

	}

	public ViewObject(int status) {
		this.status = status;
	}

	public ViewObject(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public ViewObject(int status, String msg, E object) {
		this.status = status;
		this.msg = msg;
		this.object = object;
	}

	public ViewObject(int status, String msg, List<E> datas) {
		this.status = status;
		this.msg = msg;
		this.datas = datas;
	}

	public ViewObject(int status, String msg, List<E> datas, Paging paging) {
		this.status = status;
		this.msg = msg;
		this.datas = datas;
		this.paging = paging;
	}
}
