package pw.cdmi.msm.geo.model;

import pw.cdmi.open.model.ImageSize;


/****************************************************
 * 枚举类，国旗图片的大小。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2014
 ***************************************************/
public enum FlagImageSize implements ImageSize {
	
	SIZE16(16),

	SIZE24(24),

	SIZE32(32),

	SIZE48(48),

	SIZE64(64);

	private int height;

	private FlagImageSize(int size) {
		this.height = size;
	}

	public static FlagImageSize fromValue(int height) {
		for (FlagImageSize imagesize : FlagImageSize.values()) {
			if (imagesize.height == height) {
				return imagesize;
			}
		}
		return null;
	}
	
	/**
	 * 国旗的形状是一个矩形，该方法获得国旗图片的长度
	 */
	@Override
	public int getWidth() {
		return this.height * 2;
	}

	/**
	 * 国旗的形状是一个矩形，该方法获得国旗图片的高度
	 */
	@Override
	public int getHeight() {
		return this.height;
	}

}
