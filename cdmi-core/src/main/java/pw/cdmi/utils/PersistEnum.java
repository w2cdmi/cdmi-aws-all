package pw.cdmi.utils;

import java.util.Map;

/**
 * **********************************************************
 * 需要持久化的enum类，都需要实现的接口
 * 
 * @author NiXiaojun
 * @version iSoc Service Platform, 2015-3-16
 ***********************************************************
 */
public interface PersistEnum<E extends Enum<?>> {

	/**
	 * 获取被持久化字段的值
	 * 
	 * @return 被持久化字段的值
	 */
	int getPersistedValue();

	/**
	 * 由被持久化的字段的值获取枚举类型
	 * 
	 * @param persistedValue
	 * @return
	 */
	E returnEnum(int persistedValue);

	/**
	 * 获取枚举的所有枚举项
	 * 
	 * @return map
	 */
	Map<Integer, E> getAllValueMap();
}
