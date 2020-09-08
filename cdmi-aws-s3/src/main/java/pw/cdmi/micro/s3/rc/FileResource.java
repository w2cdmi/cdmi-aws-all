package pw.cdmi.micro.s3.rc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("v1")
public class FileResource {
	private static final Logger log = LoggerFactory.getLogger(FileResource.class);

	@RequestMapping(value = "/get", method = GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/upload", method = POST)
	public String upload(@RequestParam("file") MultipartFile file) {

		if(file.isEmpty()){
			return "file is empty";
		}
		String fileName = file.getOriginalFilename();
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		log.info("上传的文件名为：" + fileName + " 后缀为" + suffixName) ;
		//设置文件存储路径
		String filePath = "D:\\";
		String path = filePath + fileName;
		File dest = new File(path);
		if(!dest.getParentFile().exists()){
			dest.getParentFile().mkdirs();//新建文件夹
		}
		try {
			file.transferTo(dest);//文件写入
			return "upload success";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "upload failure";
	}

	@RequestMapping(value = "/batch", method = POST)
	public String handFileUpload(HttpServletRequest request){
		List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
		MultipartFile file = null;
		BufferedOutputStream stream = null;
		for(int i =0;i<files.size();i++){
			file = files.get(i);
			String filePath = "D:\\uploads";
			if(!file.isEmpty()){
				try{
					byte[] bytes = file.getBytes();
					stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + File.separator +file.getOriginalFilename())));
					stream.write(bytes);
					stream.close();
				}catch (Exception e){
					stream = null;
					return "the " + i + " file upload failure";
				}
			}else{
				return "the " + i + "file is empty";
			}
		}
		return "update multifile success";
	}

	public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
		String fileName = ""; //文件名
		if(fileName != null){
			File file = new File("G:\\upload\\dd.txt");
			if(file.exists()){
				response.setContentType("application/force-download");
				response.addHeader("Content-Disposition","attachment;fileName=" + fileName);
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try{
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while(i != -1){
						os.write(buffer,0,i);
						i = bis.read(buffer);
					}
					return "download success";
				}catch (FileNotFoundException ex){
					ex.printStackTrace();
				}catch (IOException e){
					e.printStackTrace();
				}finally {
					try {
						bis.close();
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return "download failure";
	}
}
