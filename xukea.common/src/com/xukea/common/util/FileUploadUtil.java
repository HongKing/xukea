package com.xukea.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.xukea.common.util.cache.Config;


/**
 * 文件上传处理
 * @author FishBoy
 * @version
 */
public class FileUploadUtil {
	private final static Logger log = Logger.getLogger(FileUploadUtil.class);
	
	public  static String DS          = File.separator; // 目录分隔符
	private static String basePath    = "";       // 根目录
	private static String uploadPath  = "upload"; // 上传路径
	private static boolean isRename   = false;    // 是否修改文件名
	private static boolean saveAsType = false;    // 是否按类型存储
	private static boolean saveAsDate = false;    // 是否按日期存储
	
	static {
		basePath = ClassUtils.getDefaultClassLoader().getResource("/").toString();
//		if("weblogic".equalsIgnoreCase(Config.getInstance().getString("server.type"))){
//			basePath = Thread.currentThread().getContextClassLoader().getResource("/") + "";
//		}else{// tomcat
//			basePath = FileUploadUtil.class.getClassLoader().getResource("/") + "";
//		}
		if(basePath.startsWith("file:")){
			basePath = basePath.substring( "file:".length() );
		}
		basePath = basePath.substring(0, basePath.indexOf("WEB-INF"));
		basePath = basePath.endsWith(DS) ? basePath.substring(0, basePath.length()-1) : basePath;

		String fpath = Config.getInstance().getString("fileupload.path");
		if(fpath!=null && !"".equals(fpath) && !DS.equals(fpath)){
			fpath = fpath.endsWith(DS) ? fpath.substring(0, fpath.length()-1) : fpath;
			uploadPath = fpath;
		}
		
		if(uploadPath.startsWith(DS)){
			basePath = "";
		}
		
		isRename   = Config.getInstance().getBoolean("fileupload.rename");
		saveAsType = Config.getInstance().getBoolean("fileupload.saveAsDate");
		saveAsDate = Config.getInstance().getBoolean("fileupload.saveAsDate");
	}
	
	/**
	 * 保存文件
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	public static String upload(MultipartFile file) throws Exception{
		return upload(file, null);
	}
	
	/**
	 * 保存文件
	 * @param file
	 * @param newName
	 * @return
	 * @throws Exception 
	 */
	public static String upload(MultipartFile file, String newName) throws Exception{
		String fpath = getFilePath(file.getOriginalFilename());
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
        
		return fullpath;
	}

	/**
	 * 获取文件保存路径（绝对路径）
	 * @param fileName 文件名
	 * @return
	 * @throws Exception 
	 */
	public static String getFilePath(String fileName){
		String fext = getFileExt(fileName);
		String path = getRootPath();
		fext = "".equals(fext) ? "unknown" : fext;
		
		if(saveAsType){
			path += DS + fext;
		}
		
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

		if(isRename){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
			String temp = sdf.format(new Date());
			temp += "."+fext;
			return temp;
		}
		
		if(newName!=null && !"".equals(newName)){
			int idx = newName.lastIndexOf(".");
			if(idx>-1 && idx<(newName.length()-1)){
				newName = newName.substring(0, idx);
			}
			newName += "." +fext;
			return newName;
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