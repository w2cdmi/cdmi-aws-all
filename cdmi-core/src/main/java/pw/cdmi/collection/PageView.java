package pw.cdmi.collection;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/************************************************************
 * 用于封装后台分页数据向前端输出展示的工具类
 * 
 * @author 佘朝军
 * @version iSoc Service Platform, 2015-3-12
 ************************************************************/
@Getter
@Setter
public class PageView {
	
	@SuppressWarnings("rawtypes")
	private List list;		//返回当前查询页的记录列表
	private long totalRecord;		//待查询数据的总记录数

}
