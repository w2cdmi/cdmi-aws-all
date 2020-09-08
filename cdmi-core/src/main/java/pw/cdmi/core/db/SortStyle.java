package pw.cdmi.core.db;

import java.util.Locale;

/**
 * 变更排序方式，插入或交换
 * @author No.11
 *
 */
public enum SortStyle {
	Insert,		//插入两个对象之中		
	Exchange;	//对另外一个对象互换排序值
	
	public static SortStyle fromString(String value) {
		try {
			return SortStyle.valueOf(value.toUpperCase(Locale.US));
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format(
					"Invalid value '%s' for sort style given! ",
					value), e);
		}
	}
}
