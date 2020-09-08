package pw.cdmi.utils.image;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


public class ImageUtils {
	 
	/*
	 * 生成略缩图
	 * */
	public static  boolean scaleImage(String source,String target,int width,int height){
		
		return scaleImage(source,target,width,height,ZoomMode.ZOOM_SCALE_MODE);
		
	}
	
	public static  boolean scaleImage(String source,String target,int width,int height,int zoom){
		PreivewManager preivewmanager=new PreivewManager(new JmagickPreviewGenerator());
		@SuppressWarnings("unused")
		boolean flag=false;
		try {
			preivewmanager.generatePreview(source, target, width, height,zoom);
			return flag=true;
		} catch (IOException e) {
			e.printStackTrace();
			return flag=false;
		}	
	}
	
	/**
	 * 生成文件的缩略名称（最小的名称或中型的名称）
	 * @param dir			// 文件的物理路径
	 * @param type	 		// 0表示生成最小文件的名称 1表示中型文件的名称	
	 * @return
	 */
	public static String createImageURI(String dir, Integer type)
	{
		File file = new File(dir.toString());
		if(!file.exists()){
			//如果目录不存在则创建目录
			synchronized(ImageUtils.class) {
				if(!file.mkdirs())
					return null;
			}
		}
		//获取文件的后缀名
		int idx = dir.lastIndexOf('.') + 1;
		String extendName = (idx == 0 || idx >= dir.length()) ? "" : dir.substring(idx);

		String imageType = null;	// 文件名称的区别
		if (type == null) 
			return null;
		if (type == 1)
			imageType = "_m.";
		else if (type == 0)
			imageType = "_s.";
		StringBuffer createURI = new StringBuffer();
		if(StringUtils.isEmpty(dir))
			return null;
		
		createURI.append(dir.substring(0, idx));
		createURI.append(imageType);
		createURI.append(extendName);
		return createURI.toString();
	}
	
	/**
	 * 把临时文件复制到指定的目录下(上传的临时文件只有原图片)
	 * @param tempPath 		 	临时原图片路径(目录以及文件名)
	 * @param targetPath	  	指定的目录路径(目录以及文件名)
	 * @throws AppException
	 */
	public static boolean copyFileNameForPath(String tempPath, String targetPath)
	{
		if (StringUtils.isEmpty(tempPath))
			return false;
		File temp = new File(tempPath);
		if (StringUtils.isEmpty(targetPath))
			return false;
		String target = new String(targetPath.substring(0, targetPath.lastIndexOf("/")));
		File targetfn = new File(target);
		if(!targetfn.exists()){
			//如果目录不存在则创建目录
			synchronized(ImageUtils.class) {
				if(!targetfn.mkdirs())
					return false;
			}
		}
		File targetFile = new File(targetPath);
		try 
		{
			if (!targetFile.exists()) {
				if (!targetFile.createNewFile())
					return false;
			}
			FileUtils.copyFile(temp, targetFile);			// 复制原图片到指定的路径
			temp.delete();									// 删除临时原图片
		} catch (IOException e) 
		{
			e.printStackTrace();
			if(targetFile.exists())
				targetFile.delete();
			System.out.println("上传文件复制保存失败，原因：" + e.toString());
			return false;
		}
		finally{
			temp = null;
			targetFile = null;
			targetfn = null;
		}
		return true;
	}
	
	/**
	 * 返回生成指定的目录+名称
	 * @param servletPath	// 服务器路径
	 * @param target		// 酌定指定的目录
	 * @param temp			// 临时指定的目录+名称
	 * @return
	 */
	public static String createTargetPath(String servletPath, String target, String temp)
	{
		String file = null;			// 原图片名称
		StringBuffer targetPath = new StringBuffer();
		file = new String(temp.substring(temp.lastIndexOf("/")+ 1, temp.length()));
		targetPath.append(servletPath);
		targetPath.append(target);
		targetPath.append(file);
		return targetPath.toString();
	}
	
	/**
	 * 删除图片
	 * @param imgPath	// 图片的路径
	 */
	public static boolean deleteImgPath(String imgPath)
	{
		try {
			File imgFile = new File(imgPath);
			if (imgFile.exists() && imgFile.isFile())
				return imgFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
    public static void main(String[] args) {

    	String spath = "E:\\Data\\flo.jpg";
    	String tpath = "E:\\Data\\6.jpg";
    	System.out.println(ImageUtils.scaleImage(spath,tpath,100,120,0));

    }
    
    
	
}
