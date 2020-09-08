package pw.cdmi.core.entities.model;

/****************************************************
 * 枚举类接口，系统中图片的大小。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2014
 ***************************************************/
public interface ImageSize {
	// 国旗的形状是一个矩形，该方法获得国旗图片的长度
	public int getWidth();

	// 国旗的形状是一个矩形，该方法获得国旗图片的高度
	public int getHeight();
}
