package com.xukea.common.validator;

import java.io.File;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.xukea.common.util.Config;
import com.xukea.common.util.FileUploadUtil;
import com.xukea.framework.base.BaseValidator;


/**
 * 文件上传验证类
 * @author FishBoy
 * @version
 */
public class FileUploadValidator extends BaseValidator{
	private static int     maxSize   = 1024; // 文件大小（KB）
	private static String  extension = "";   // 允许类型
	private static boolean rewrite   = true; // 允许覆盖
	
	static{
		extension = Config.getInstance().getString("fileupload.extens");
		if(extension==null || "".equals(extension) || "*".equals(extension)){
			extension = "";
		}
		extension = ("|"+extension+"|").toLowerCase();
		
		maxSize = Config.getInstance().getInt("fileupload.maxsize");
		rewrite = Config.getInstance().getBoolean("fileupload.rewrite");
	}

	@Override
	public void validate(Object obj, Errors errors) {
	}
	
	public static String validate(Object obj) {
		MultipartFile file = (MultipartFile) obj;

		// 文件类型检测
		String fext  = FileUploadUtil.getFileExt( file.getOriginalFilename());
		fext = "|"+ fext.toLowerCase() +"|";
		if(!"||".equals(extension)){
			if(extension.indexOf(fext)==-1){
				return "非法的文件类型";
			}
		}
		
		// 文件大小检测
		long fsize = file.getSize() / 1024;
		if(maxSize>0 && fsize>maxSize){
			return "文件超过指定大小";
		}

		// 存放目录权限检测
		String rpath = FileUploadUtil.getRootPath();
		File temp = new File(rpath);
        if (!temp.canWrite()) {
        	return "上传目录无写入权限";
        }

		// 文件存在检测
		if(rewrite) return null;
		String fpath = FileUploadUtil.getFilePath(file.getOriginalFilename());
		String fname = FileUploadUtil.getFileName(file.getOriginalFilename());
		String fullpath = fpath + FileUploadUtil.DS + fname;
		temp = new File(fullpath);
        if (temp.exists()) {
        	return "上传的文件已经存在";
        }
        
        return null;
	}
}