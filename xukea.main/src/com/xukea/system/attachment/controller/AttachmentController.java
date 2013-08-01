package com.xukea.system.attachment.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.common.UserBasicInfo;
import com.xukea.common.exception.PageNotFoundException;
import com.xukea.common.util.FileUploadUtil;
import com.xukea.common.util.StringUtil;
import com.xukea.common.util.WebUtil;
import com.xukea.common.validator.FileUploadValidator;
import com.xukea.framework.base.BaseConstants;
import com.xukea.framework.base.BaseRestSpringController;
import com.xukea.system.attachment.model.Attachment;
import com.xukea.system.attachment.service.AttachmentService;

/**
 * 附件上传下载
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Controller
@RequestMapping("/system/attachment")
public class AttachmentController extends BaseRestSpringController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AttachmentService attachmentService;

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="/upload")
	public void fileupload(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		UserBasicInfo user = UserBasicInfo.getFromSession(request);
		
		List<MultipartFile> list = request.getFiles("files");
		
		
		String rtMsg  = ""; //返回的消息
		String fids   = ""; //成功上传后的ID
		String fnames = ""; //成功上传的文件
		Iterator<MultipartFile> it = list.iterator();
		while(it.hasNext()){
			MultipartFile file  = it.next();
			if(file.getSize()<=0 || "".equals(file.getOriginalFilename())){
				continue;
			}
			String fname  = file.getOriginalFilename();
			String vldMsg = FileUploadValidator.validate(file);

			if(vldMsg!=null){
				rtMsg +="\r\n"+ fname +":\t"+ vldMsg;
				continue;
			}
			//TODO 文件别名、备注等信息
			String alisName = fname; // 文件别名
			String remark = ""; //备注
			float  fsize  = file.getSize() / 1024;    //文件大小
			String fullpath = FileUploadUtil.upload(file, alisName); //上传并返回路径
			String fileExt  = FileUploadUtil.getFileExt(fname); //文件类型
			long attId = attachmentService.insert(alisName, fullpath, fsize, fileExt, remark, user.getId());
			fids   += attId +",";
			fnames += fname +",";
		}
		
		//输出
		JSONObject json = new JSONObject();
		if(!"".equals(rtMsg)){
			json.put("xukea_type"  , "error");
			json.put("xukea_status", BaseConstants.HTTP_SERVER_ERROR);
			json.put("xukea_msg"   , "以下文件上传失败"+ rtMsg);
		}else{
			json.put("xukea_type"  , "success");
			json.put("xukea_status", BaseConstants.HTTP_OK);
			json.put("xukea_msg"   , "所有文件上传成功");
		}
		json.put("data", "{ids:'"+ fids +"', fnames:'"+ fnames +"'}");
		String cntype = "text/html";
		this.output(request, response, cntype, json.toString());
	}

	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @throws PageNotFoundException 
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping(value="/download")
	public String downloadFile(HttpServletRequest request) throws PageNotFoundException, IOException{
		long fid = WebUtil.getValueLong(request, "fid", -1);
		return "redirect:/system/attachment/download/"+fid;
	}
	
	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @throws PageNotFoundException 
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping(value="/download/{fid}")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable long fid) throws PageNotFoundException, IOException{
		Attachment file = attachmentService.getFileById(fid);
		if(file==null){
			throw new PageNotFoundException();
		}
		
		String fileName = file.getFileName();//得到下载文件的名字 
//		int    fileSize = (int) file.getFileSize() * 1024;
        try {
        	String agent = WebUtil.getClientInfo(request);
        	if (null != agent && -1 != agent.indexOf("MSIE")){
        		fileName = URLEncoder.encode(fileName, "UTF-8");
        		fileName = StringUtil.replace(fileName, "+", "%20");
        	}else{
        		fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
//        		fileName = StringUtil.replace(fileName, " ", "%20");
        	}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setHeader("Content-Disposition", "attachment;filename=\""+ fileName +"\"");
		response.setContentType( FileUploadUtil.getFileMimeType(file.getFileType()) ); //设置response的编码方式 
//	    response.setContentLength( fileSize ); // 暂时去除文件大小的输出，防止浏览器接受数据不完整
        
		try {
			OutputStream myout  = response.getOutputStream();//从response对象中得到输出流,准备下载
	        FileInputStream fis = new FileInputStream(file.getFileUrl());//读出文件到i/o流  
	        BufferedInputStream buff = new BufferedInputStream(fis);
	        int    read = 0;
	        byte[] temp = new byte[1024];//相当于我们的缓存 
	        while ((read = buff.read(temp)) != -1){
	        	myout.write(temp, 0, read);
	        }
	        //将写入到客户端的内存的数据,刷新到磁盘 
	        myout.flush();
	        buff.close();
	        myout.close();
		} catch (IOException e) {
			log.error("download error, file id is "+fid, e);
			throw e;
		}
	}
	
}
