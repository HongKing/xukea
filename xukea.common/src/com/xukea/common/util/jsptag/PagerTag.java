package com.xukea.common.util.jsptag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.xukea.framework.util.PageList;

/**
 * 自定义标签：分页标签
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-02-16
 */
public class PagerTag<T> extends TagSupport {
	// 分页对象
	private PageList<T> pager;
	// 翻页的URL
	private String url;
	
    @Override
    public int doStartTag() throws JspException {
        if(pager == null)  return SKIP_BODY;
    	
        try {
        	JspWriter out = this.pageContext.getOut();
        	String pagerHtml = this.getPagerHTML();
        	out.print(pagerHtml);
        } catch(Exception e) {
            throw new JspException(e.getMessage());
        }
        
        return SKIP_BODY;
    }

    /**
     * 获取分页对象
     * @return
     */
    private String getPagerHTML(){
    	int pageLength = 5;//分页长度
		StringBuffer html = new StringBuffer();
		
		html.append("<ul>");
		
		if(pager.isFirstPage()){
			html.append("<li class='disabled'><a href='#'>首页</a></li>");
			html.append("<li class='disabled'><a href='#'>上一页</a></li>");
		}else{
			html.append("<li><a href='"+ getPageUrl(1) +"'>首页</a></li>");
			html.append("<li><a href='"+ getPageUrl(pager.getPrevPageNumber()) +"'>上一页</a></li>");
		}
		
//		int strNum = 1;
//		int endNum = pager.getLastPageNumber();
//		int thsNum = pager.getThisPageNumber();
//		if(endNum>pageLength){
//			int half = (int)Math.ceil(pageLength/2.0);
//			int temp = thsNum-half+1;
//			strNum   = (temp<1) ? 1 : (temp+pageLength>endNum ? endNum-pageLength+1 : temp);
//			endNum   = strNum + pageLength;
//		}else{
//			endNum = endNum + 1;
//		}
//		for(; strNum<endNum; strNum++){
//			if(thsNum==strNum){
//				html.append("<li><a href='"+ url +"/"+ strNum +"'>"+ strNum +"</a></li>");
//			}else{
//				html.append("<li class='active'><a href='"+ url +"/"+ strNum +"'>"+ strNum +"</a></li>");
//			}
//		}

		if(pager.isLastPage()){
			html.append("<li class='disabled'><a href='#'>下一页</a></li>");
			html.append("<li class='disabled'><a href='#'>末页</a></li>");
		}else{
			html.append("<li><a href='"+ getPageUrl(pager.getNextPageNumber()) +"'>下一页</a></li>");
			html.append("<li><a href='"+ getPageUrl(pager.getLastPageNumber()) +"'>末页</a></li>");
		}
		
		html.append("</ul>");
		
		return html.toString();
    }
    
	public PageList<T> getPager() {
		return pager;
	}

	public void setPager(PageList<T> pager) {
		this.pager = pager;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 格式化URL
	 * @param page
	 * @return
	 */
	private String getPageUrl(int page){
		if(url==null || "".equals(url)){
			return page+"";
		}else if(url.indexOf("?")>=0){
			return url + page;
		}else{
			return url +"/"+ page;
		}
	}
}