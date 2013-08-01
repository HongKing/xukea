package com.xukea.common.util.jsptag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.xukea.framework.util.PageList;

/**
 * 自定义标签：表格补行
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-02-16
 */
public class TableEquipTRTag<T> extends TagSupport{
	// 分页对象
	private PageList<T> pager;
	// 列数
	private int cols;
	
    @Override
    public int doStartTag() throws JspException {
        if(pager == null)  return SKIP_BODY;
        
        try {
        	JspWriter out = this.pageContext.getOut();
            // 页面记录数
            int rows = pager.getPageSize(); 
            // 当前行数
            int trs  = 0;
            if(pager.getResult()!=null){
            	trs = pager.getResult().size();
            }
            if(trs==0){
            	out.print("<tr><td colspan='"+ cols +"' class='center emptip'><i>未找到符合的数据</i></td></tr>");
            	trs=1;
            }
            // 补充空行
        	for(; trs<rows; trs++){
        		out.print("<tr>");
        		for(int i=0; i<cols; i++){
        			out.print("<td>&nbsp;</td>");
        		}
        		out.print("</tr>");
        	}

        } catch(Exception e) {
            throw new JspException(e.getMessage());
        }
        
        return SKIP_BODY;
    }
    
	public PageList<T> getPager() {
		return pager;
	}

	public void setPager(PageList<T> pager) {
		this.pager = pager;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}
}