package pw.cdmi.utils.image;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.IOException;

import magick.CompressionType;
import magick.ImageInfo;
import magick.MagickApiException;
import magick.MagickException;
import magick.MagickImage;

/**
 * 
 * Unix下最著名的ImageMagicK库的Java调用
 * JMagicK并不处理什么，只是ImageMagicK到Java的一个jni接口，其方法大部分是native的
 * 说实在的，JMagicK的封装真不怎么行，所有处理函数全在一个类里
 * 
 * 注：已放在正式环境下run了
 * 
 *
 */

public class JmagickPreviewGenerator implements PreviewGenerator {
	
	public JmagickPreviewGenerator() {
		System.setProperty("jmagick.systemclassloader", "no");
	}

	public Dimension getSize(String source) throws IOException {
		try {
			ImageInfo info = new ImageInfo(source);
			MagickImage image = new MagickImage(info);
			System.out.println("图片的大小："  + image.sizeBlob());
			return image.getDimension();
		} catch (MagickApiException ex) {
			throw new IOException(ex.getMessage());
		} catch (MagickException ex) {
			throw new IOException(ex.getMessage());
		}
	}

	public void generate(String source, String target, int width, int height) throws IOException {
		try {
			System.out.println("JMagick scale -> new ImageInfo");
			ImageInfo info = new ImageInfo(source);
			System.out.println("JMagick scale -> new MagickImage");
			MagickImage image = new MagickImage(info);
			System.out.println("JMagick scale -> scaleImage");
			MagickImage canvasImage = image.scaleImage(width, height);
			System.out.println("JMagick scale -> save");
			save(canvasImage, target);
		} catch (MagickApiException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		} catch (MagickException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}
	
	public void cropImage(String source, String target, Rectangle rect) throws IOException {
		try {
			System.out.println("JMagick scale -> new ImageInfo");
			ImageInfo info = new ImageInfo(source);
			System.out.println("JMagick scale -> new MagickImage");
			MagickImage image = new MagickImage(info);
			System.out.println("JMagick scale -> cropImage");
			MagickImage canvasImage = image.cropImage(rect);
			System.out.println("JMagick scale -> save");
			save(canvasImage, target);
		} catch (MagickApiException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		} catch (MagickException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}
	
	private void save(MagickImage image, String target) throws IOException {
		try {
			image.setCompression(CompressionType.JPEGCompression);
			image.setFileName(target);
			image.writeImage(new ImageInfo());
		} catch (MagickApiException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		} catch (MagickException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}

}
