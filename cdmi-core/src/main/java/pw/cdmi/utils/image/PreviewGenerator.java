package pw.cdmi.utils.image;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.IOException;

/**
 * 
 * 预览图生成的策略接口
 * 
 * @author 梁飞 
 *
 */

public interface PreviewGenerator {
	
	/**
	 * 主要用于判断是否为大图，小图就copy
	 * @param source 源图片位置
	 * @return Dimension 图片的大小
	 * @throws IOException
	 */
	public Dimension getSize(String source) throws IOException;
	
	/**
	 * 处理生成预览图的策略方法
	 * 大小判定，比例调整已在PreivewManager，请直接使用width,height
	 * @param source 源图片位置
	 * @param target 保存预览图位置
	 * @param width 预览图宽度
	 * @param height 预览图高度
	 * @throws IOException
	 */
	public void generate(String source, String target, int width, int height) throws IOException;
	
	public void cropImage(String source, String target, Rectangle rect) throws IOException;
}
