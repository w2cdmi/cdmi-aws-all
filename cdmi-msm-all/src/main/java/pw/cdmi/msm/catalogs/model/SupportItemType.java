package pw.cdmi.msm.catalogs.model;

import java.util.Locale;

public enum SupportItemType {
	FILE,		//文件
	ALBUM;		//场景视图
	
	public static SupportItemType fromString(String value) {
		try {
			return SupportItemType.valueOf(value.toUpperCase(Locale.US));
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format(
					"Invalid value '%s' for supportitem type given! Has to be either 'file' or 'album' (case insensitive).", value), e);
		}
	}
}
