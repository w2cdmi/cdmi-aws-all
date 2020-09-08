package pw.cdmi.utils.image;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import magick.CompressionType;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 此类负责与调用者打交道
 * 
 * @author 梁飞
 *
 */
public class PreivewManager {
	
	protected static final Log log = LogFactory.getLog(PreivewManager.class);
	
	private PreviewGenerator previewGenerator;
	
	public PreivewManager(PreviewGenerator previewGenerator) {
		this.previewGenerator = previewGenerator;
	}
	
	/**
	 * 一些通过的处理就在这先做了
	 * 
	 * @param source
	 * @param target
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public void generatePreview(String source, String target, int width, int height) throws IOException {
		
		generatePreview(source, target, width, height,ZoomMode.ZOOM_SCALE_MODE);
		
	}
	
	public void generatePreview(String source, String target, int width, int height,int zoom) throws IOException {
		if (source == null || ! new File(source).exists()) {
			return;
		}
		checkDir(target);
		
		Dimension d = previewGenerator.getSize(source);
		int w = (int)d.getWidth();
		int h = (int)d.getHeight();
		int newWidth = width;
		int newHeight= height;
		if (w <= width && h <= height) {
			copy(source, target); //如果图片较小，就直接copy过去
		} else {
			//从中间部分剪辑
			if(zoom == ZoomMode.CUT_MODE){
				int w1 = (w>width) ? ((w - width) / 2) : 0;
				int h1 = (h>height) ? ((h - height) / 2) : 0;
				Rectangle rect = new Rectangle(w1, h1, width, height);
				System.out.println("width:" + width + " - " + "height:" + height);
				previewGenerator.cropImage(source, target, rect);
			}else if(zoom == ZoomMode.ZOOM_CUT_MODE){
				if (w > width || h > height) {
					if(w * height > h * width){
						newWidth =  width * h / height;
					}else{
						newHeight = height * w / width;
					}
					try {
						ImageInfo info = new ImageInfo(source);
						MagickImage image = new MagickImage(info);
						System.out.println("newWidth:" + newWidth + " - " + "newHeight:" + newHeight);
						MagickImage scaleImage = image.scaleImage(newWidth, newHeight);
						
						int w1 = (newWidth>width) ? ((newWidth - width) / 2) : 0;
						int h1 = (newHeight>height) ? ((newHeight - height) / 2) : 0;
						System.out.println("width:" + width + " - " + "height:" + height);
						Rectangle rect = new Rectangle(w1, h1, width, height);
						
						MagickImage canvasImage = scaleImage.cropImage(rect);
						canvasImage.setCompression(CompressionType.JPEGCompression);
						canvasImage.setFileName(target);
						canvasImage.writeImage(new ImageInfo());
					} catch (MagickException e) {
						e.printStackTrace();
					}

				}else{
					int w1 = (w>width) ? ((w - width) / 2) : 0;
					int h1 = (h>height) ? ((h - height) / 2) : 0;
					Rectangle rect = new Rectangle(w1, h1, width, height);
					System.out.println("width:" + width + " - " + "height:" + height);
					previewGenerator.cropImage(source, target, rect);
				}
			}else{//同比缩放
				if (w > width || h > height) {
					if(w * height > h * width){
						newHeight = h * width / w;
					}else{
						newWidth = w * height / h;	//同比缩放
					}
				} else {
					newWidth = w;
					newHeight = h;
				}
				if(zoom == ZoomMode.ZOOM_TENSILE_MODE){
					//拉伸缩放
					if(newWidth<width) newWidth = width;
					if(newHeight<height) newHeight = height;
				}
				System.out.println("width:" + width + " - " + "height:" + height);
				previewGenerator.generate(source, target, newWidth, newHeight);
			}
		}
		return ;
	}
	
	private void checkDir(String target) {
		File dir = new File(target).getParentFile();
		if (! dir.exists()) {
			dir.mkdirs();
			log.warn(dir.getAbsolutePath() + " not exists! already auto made!");
		}
	}
	
	private void copy(String source, String target) throws IOException {
		FileUtils.copyFile(new File(source), new File(target));
	}
}
