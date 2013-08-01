package com.xukea.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.xukea.common.util.cache.Config;

/**
 * 文件上传处理
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-02-16
 */
public class FileUploadUtil {
	private final static Logger log = Logger.getLogger(FileUploadUtil.class);
	
	public  static String DS          = File.separator; // 目录分隔符
	private static String basePath    = "";       // 根目录
	private static String uploadPath  = "upload"; // 上传路径
	private static boolean isWindows  = false;    // 是否windows系统
	private static boolean isRename   = false;    // 是否修改文件名
	private static boolean saveAsType = false;    // 是否按类型存储
	private static boolean saveAsDate = false;    // 是否按日期存储
	
	static {
		// 文件保存目录
		String fpath = Config.getInstance().getString("fileupload.path");
		if(fpath!=null && !"".equals(fpath) && !DS.equals(fpath)){
			fpath = fpath.endsWith(DS) ? fpath.substring(0, fpath.length()-1) : fpath;
			uploadPath = fpath;
		}
		
		// 系统运行class目录
		basePath = ClassUtils.getDefaultClassLoader().getResource("").toString();
//		if("weblogic".equalsIgnoreCase(Config.getInstance().getString("server.type"))){
//			basePath = Thread.currentThread().getContextClassLoader().getResource("/") + "";
//		}else{// tomcat
//			basePath = FileUploadUtil.class.getClassLoader().getResource("/") + "";
//		}
		// 系统运行根目录
		basePath = basePath.substring(0, basePath.indexOf("WEB-INF"));
		// 设置保存根目录
		if(basePath.startsWith("file:")){
			// windows系统，且uploadPath中含有盘符，则basePath为空
			isWindows = true;
			if(uploadPath.indexOf(":")>0){
				basePath = "";
			}else{
				basePath = basePath.substring( "file:".length() );
			}
		}else if(uploadPath.startsWith(DS)){
			// 其他操作系统
			basePath = "";
		}
		
		// 如果basePath不为空，则去除尾部的目录分隔符
		if(!"".equals(basePath)){
			basePath = basePath.endsWith(DS)  ? basePath.substring(0, basePath.length()-1) : basePath ;
		}
		
		isRename   = Config.getInstance().getBoolean("fileupload.rename");
		saveAsType = Config.getInstance().getBoolean("fileupload.saveAsDate");
		saveAsDate = Config.getInstance().getBoolean("fileupload.saveAsDate");
	}
	
	/**
	 * 保存文件
	 * @param file
	 * @return 文件名及相对路径
	 * @throws Exception 
	 */
	public static String upload(MultipartFile file) throws Exception{
		return upload(file, null);
	}
	
	/**
	 * 保存文件
	 * @param file
	 * @param newName
	 * @return 文件名及相对路径
	 * @throws Exception 
	 */
	public static String upload(MultipartFile file, String newName) throws Exception{
		String fpath = getFilePath(file.getOriginalFilename(), newName);
		String fname = getFileName(file.getOriginalFilename(), newName);
		String fullpath = fpath + DS + fname;
		
		mkdir(fpath);
		
		InputStream  surcStream = null;
        OutputStream destStream = null;

    	surcStream = file.getInputStream();
        destStream = new BufferedOutputStream(new FileOutputStream( fullpath ));

		FileCopyUtils.copy(surcStream, destStream);
//        int bytesRead = 0;
//        byte[] buffer = new byte[8192];
//        while ((bytesRead = surcStream.read(buffer, 0, 8192)) != -1) {
//            destStream.write(buffer, 0, bytesRead);
//        }

        try{
            if (surcStream != null){
            	surcStream.close();
            }
        }catch (Exception e){
        }

        try{
            if (destStream != null){
                destStream.flush();
                destStream.close();
            }
        }catch (Exception e){
        }
        
		return fullpath.substring(getRootPath().length());
	}

	/**
	 * 获取文件保存路径（绝对路径）
	 * @param fileName 文件名
	 * @return
	 */
	public static String getFilePath(String fileName){
		return getFilePath(fileName, null);
	}
	
	/**
	 * 获取文件保存路径（绝对路径）
	 * @param fileName 源文件名
	 * @param newName  新文件名
	 * @return
	 */
	public static String getFilePath(String fileName, String newName){
		String fext = getFileExt(fileName);
		String path = getRootPath();
		path = path.endsWith(DS) ? path.substring(0, path.length()-1) : path; // 去除尾部目录符
		fext = "".equals(fext) ? "unknown" : fext;

		// 是否按照类型保存
		if(saveAsType){
			path += DS + fext;
		}
		
		// 源文件的路径
		if(isWindows){
			fileName = fileName.replaceAll("/", "\\\\");
		}
		if(fileName.indexOf(DS)==0){
			path += fileName.substring(0, fileName.lastIndexOf(DS));
		}else if(fileName.indexOf(DS)>0){
			path += DS + fileName.substring(0, fileName.lastIndexOf(DS));
		}
		path = path.endsWith(DS) ? path.substring(0, path.length()-1) : path; // 去除尾部目录符
		
		// 新文件的路径
		if(newName!=null){
			if(isWindows){
				newName = newName.replaceAll("/", "\\\\");
			}
			if(newName.indexOf(DS)==0){
				path += newName.substring(0, newName.lastIndexOf(DS));
			}else if(newName.indexOf(DS)>0){
				path += DS + newName.substring(0, newName.lastIndexOf(DS));
			}
		}
		path = path.endsWith(DS) ? path.substring(0, path.length()-1) : path; // 去除尾部目录符
		
		// 是否按照日期保存
		if(saveAsDate){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String date = sdf.format(new Date());
			String[] dstr = date.split("-");
			path += DS + dstr[0];
			path += DS + dstr[1];
		}
		
		return path;
	}

	/**
	 * 获取保存后的文件名
	 * @param oldName  原文件名
	 * @return
	 */
	public static String getFileName(String oldName){
		return getFileName(oldName, null);
	}
	
	/**
	 * 获取保存后的文件名
	 * @param oldName  原文件名
	 * @param newName  新文件名
	 * @return
	 */
	public static String getFileName(String oldName, String newName){
		String fext = getFileExt(oldName);
		
		// 重命名
		if(isRename){
			// 日期时间（毫秒数）
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
			String temp = sdf.format(new Date());
			// 最后跟一位随机数，最大限度保证不重复
			Random random = new Random();
			temp += random.nextInt(10);
			// 文件后缀名
			temp += "."+ fext; 
			return temp;
		}
		
		// 如果有新文件名，则返回新文件名
		if(newName!=null && !"".equals(newName)){
			// 新文件名去除目录
			if(isWindows){
				newName = newName.replaceAll("/", "\\\\");
			}
			if(newName.indexOf(DS)>=0){
				newName = newName.substring(newName.lastIndexOf(DS)+1);
			}
		
			int idx = newName.lastIndexOf(".");
			if(idx>-1 && idx<(newName.length()-1)){
				newName = newName.substring(0, idx);
			}
			newName += "." +fext;
			return newName;
		}
		
		// 源文件名去除目录
		if(isWindows){
			oldName = oldName.replaceAll("/", "\\\\");
		}
		if(oldName.indexOf(DS)>=0){
			oldName = oldName.substring(oldName.lastIndexOf(DS)+1);
		}
		return oldName;
	}
	
	/**
	 * 获取文件扩展名
	 * @param fileName  文件名
	 * @return
	 */
	public static String getFileExt(String fileName){
		if(fileName!=null && !"".equals(fileName)){
			int idx = fileName.lastIndexOf(".");
			if(idx>-1 && idx<(fileName.length()-1)){
				return fileName.substring(idx + 1);
			}
		}
		return "";
	}

	/**
	 * 获取上传根路径
	 * @return
	 */
	public static String getRootPath(){
		return "".equals(basePath) ? uploadPath : (basePath + DS + uploadPath);
	}
	
	/**
	 * 创建目录
	 * @param path
	 * @throws Exception
	 */
	private static void mkdir(String path) throws Exception{
		try{
			File fpath = new File(path);
	        if (!fpath.exists()) {
	        	fpath.mkdirs();
	        }
		}catch(Exception e){
			String msg = "创建目录【"+ path +"】出错";
			log.error(msg + e);
			throw new Exception(msg);
		}
	}
	
	/**
	 * 根据文件扩展名获取文件Mime类型
	 * 
	 * @param ext
	 * @return
	 */
	public static String getFileMimeType(String ext){
		ext = ext.toLowerCase();
		if("doc".equals(ext) || "docx".equals(ext)){
			return "application/msword";
		}else if("xls".equals(ext) || "xlsx".equals(ext)){
			return "application/vnd.ms-excel";
		}else if("ppt".equals(ext) || "pptx".equals(ext)){
			return "application/vnd.ms-powerpoint";
		}else if("txt".equals(ext)){
			return "application/txt";
		}else if("pdf".equals(ext)){
			return "application/pdf";
		}else if("ico".equals(ext)){
			return "image/x-icon";
		}else if("gif".equals(ext)){
			return "image/gif";
		}else if("ipeg".equals(ext) || "jpg".equals(ext)){
			return "image/jpeg";
		}else if("rar".equals(ext)){
			return "application/rar";
		}else if("zip".equals(ext)){
			return "application/zip";
		}else if("gz".equals(ext)){
			return "application/x-gzip";
		}else if("tar".equals(ext)){
			return "application/x-tar";      
		}else if("iso".equals(ext)){
			return "application/octet-stream";
		}else if("chm".equals(ext)){
			return "application/mshelp";
		}else if("mht".equals(ext)){
			return "text/x-mht";
		}else if("htm".equals(ext) || "html".equals(ext)){
			return "text/html;";
		}else if("ape".equals(ext)){
			return "application/octet-stream";
		}else if("rmvb".equals(ext)){
			return "application/octet-stream";
		}else{
			return "application/x-msdownload";
		}
	}
}