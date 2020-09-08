package pw.cdmi.core.db;

import java.util.Locale;

/**
 * 关联删除对象事件
 * 
 * @author No.11
 *
 */
public enum RelationDeleteEvent {
	NODEAL, 			// 有子对象不处理
	DELETE, 			// 同时删除子对象
	CLEAR, 				// 清除子对象的归属标识
	FORBIDWITHDATA; 	// 有子对象禁止删除，默认

	public static RelationDeleteEvent fromString(String value) {
		try {
			return RelationDeleteEvent.valueOf(value.toUpperCase(Locale.US));
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format(
					"Invalid value '%s' for relation delete event given! ",
					value), e);
		}
	}
}
